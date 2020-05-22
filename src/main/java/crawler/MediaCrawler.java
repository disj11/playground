package crawler;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 이미지 / 동영상 크롤링
 * allowContentType 메서드를 구현하여 어떤 contentType을 크롤링 할 지 지정
 */
public abstract class MediaCrawler extends Crawler<MediaCrawlingOptions> {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MediaCrawler.class);
    private static final Pattern pattern = Pattern.compile("url\\((?!['\"]?(?:data|http):)['\"]?([^'\"\\)]*)['\"]?\\)");
    private int detectedCount;

    public MediaCrawler(WebDriverBase driver, MediaCrawlingOptions options) {
        super(driver, options);
    }

    public abstract void accept(MediaCrawlingResult result);
    public abstract boolean allowContentType(String contentType);

    @Override
    public void prestart() {
        detectedCount = 0;
    }

    @Override
    public boolean stop() {
        return options.getLimit() != 0 && options.getLimit() <= detectedCount;
    }

    @Override
    public void accept(CrawlingResult result) {
        crawlSrcElements(result);
        crawlBackgroundElements(result);
    }

    private void crawlSrcElements(CrawlingResult result) {
        Document document = result.getDocument();
        Elements elements = document.getElementsByAttribute("src");

        for (Element element : elements) {
            if (stop()) {
                break;
            }

            String url = element.absUrl("src");
            if (!valid(url)) {
                continue;
            }

            detectedCount++;
            accept(new MediaCrawlingResult(result.getDocument(), result.getDepth(), element, url));
        }
    }

    private void crawlBackgroundElements(CrawlingResult result) {
        Document document = result.getDocument();
        Elements elements = document.select("*[style*='background']");
        for (Element element : elements) {
            if (stop()) {
                break;
            }

            Matcher matcher = pattern.matcher(element.attr("style"));
            if (!matcher.find()) {
                continue;
            }

            String url = StringUtil.resolve(element.baseUri(), matcher.group(1));
            if (!valid(url)) {
                continue;
            }

            detectedCount++;
            accept(new MediaCrawlingResult(result.getDocument(), result.getDepth(), element, url));
        }
    }

    private boolean valid(String url) {
        try {
            return allowContentType(CrawlerUtils.getContentType(url)) &&
                    CrawlerUtils.validExt(url, options.getExtensions()) &&
                    CrawlerUtils.validCapacity(url, options.getMinBytes(), options.getMaxBytes());
        } catch (Exception e) {
            logger.warn("Content-Length 확인에 실패하였습니다. url : {}", url);
            return false;
        }
    }
}
