package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 페이지 내에 존재하는 페이지 링크를 검색하고, 검색된 페이지 모두 방문
 * @param <T> 크롤링 옵션
 */
public abstract class Crawler<T extends CrawlingOptions> implements java.util.function.Consumer<CrawlingResult> {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Crawler.class);
    private static final Pattern filters = Pattern.compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    protected final WebDriverBase driver;
    protected final T options;

    public Crawler(WebDriverBase driver, T options) {
        this.driver = driver;
        this.options = options;
    }

    public void prestart() {
        //
    }

    public void hook() {
        //
    }

    public boolean stop() {
        return false;
    }

    public void start() {
        prestart();

        Set<String> visitedSet = new HashSet<>();
        Queue<String> urlQueue = new LinkedList<>();
        urlQueue.add(options.getUrl());

        String crawlDomain = getCrawlDomain(options.getUrl());
        int currentDepth = 0;

        while (!urlQueue.isEmpty() && !stop()) {
            if (currentDepth > options.getMaxDepth()) {
                break;
            }

            int queueSize = urlQueue.size();
            for (int i = 0; i < queueSize; i++) {
                if (stop()) {
                    break;
                }

                String url = urlQueue.poll();
                if (!valid(crawlDomain, url) || visited(visitedSet, url)) {
                    continue;
                }

                logger.info("visit url : {}, depth : {}", url, currentDepth);
                crawl(url, crawlDomain, currentDepth, urlQueue);
            }

            currentDepth++;
        }
    }

    private String getCrawlDomain(String url) {
        int endIndex = url.indexOf("/", url.indexOf("://") + 3);
        endIndex = endIndex < 0 ? url.length() : endIndex;
        return url.substring(0, endIndex);
    }

    protected boolean valid(String crawlDomain, String url) {
        return url != null && url.startsWith(crawlDomain) && !filters.matcher(url).matches();
    }

    private boolean visited(Set<String> visitedSet, String url) {
        if (visitedSet.contains(url)) {
            return true;
        }

        visitedSet.add(url);
        return false;
    }

    protected void crawl(String validUrl, String crawlDomain, int depth, Queue<String> urlQueue) {
        try {
            WebDriver webDriver = driver.getDriver();
            webDriver.get(validUrl);
            hook();

            Document document = Jsoup.parse(webDriver.getPageSource(), crawlDomain);
            accept(new CrawlingResult(document, depth));

            Elements detectedPages = document.select("a");
            for (Element page : detectedPages) {
                String pageUrl = page.absUrl("href");
                if (valid(crawlDomain, pageUrl)) {
                    urlQueue.add(pageUrl);
                }
            }
        } catch (Exception e) {
            logger.warn("크롤링에 실패하였습니다. url : {}", validUrl, e);
        }
    }
}
