package crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MediaCrawler extends Crawler<MediaCrawlingOptions> {
    private static final Logger logger = LoggerFactory.getLogger(MediaCrawler.class);
    private static final Pattern pattern = Pattern.compile("url\\((?!['\"]?(?:data|http):)['\"]?([^'\"\\)]*)['\"]?\\)");

    private Set<String> detectedUrlSet;
    private int detectedCount;

    public MediaCrawler(WebDriverBase driver, MediaCrawlingOptions options) {
        super(driver, options);
    }

    public abstract void accept(MediaCrawlingResult result);
    public abstract boolean allowContentType(String contentType);

    @Override
    public void prestart() {
        detectedCount = 0;
        detectedUrlSet = new HashSet<>();
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

            accept(result, element, url);
        }
    }

    private void crawlBackgroundElements(CrawlingResult result) {
        Document document = result.getDocument();
        Elements elements = document.select("*[style*='background']");
        for (Element element : elements) {
            if (stop()) {
                break;
            }

            String url = getAbsUrl(element);
            if (!valid(url)) {
                continue;
            }

            accept(result, element, url);
        }
    }

    private void accept(CrawlingResult result, Element element, String url) {
        detectedUrlSet.add(url);
        detectedCount++;
        accept(new MediaCrawlingResult(result.getDocument(), result.getDepth(), element, url));
    }

    private boolean valid(String url) {
        try {
            if (url == null || "".equals(url.trim()) || detectedUrlSet.contains(url)) {
                return false;
            }

            return allowContentType(CrawlerUtils.getContentType(url)) &&
                    CrawlerUtils.validExt(url, options.getExtensions()) &&
                    CrawlerUtils.validCapacity(url, options.getMinBytes(), options.getMaxBytes());
        } catch (Exception e) {
            logger.warn("Content-Length 확인에 실패하였습니다. url : {}", url);
            return false;
        }
    }

    private String getAbsUrl(Element element) {
        try {
            Matcher matcher = pattern.matcher(element.attr("style"));
            if (!matcher.find()) {
                return "";
            }

            return CrawlerUtils.resolve(element.baseUri(), matcher.group(1));
        } catch (Exception e) {
            logger.warn("absolute url 을 구할 수 없음", e);
            return "";
        }
    }
}