package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class Crawler {
    private static final Logger logger = LoggerFactory.getLogger(Crawler.class);
    private static final Pattern filters = Pattern.compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    private final WebDriverBase driver;

    public Crawler(WebDriverBase driver) {
        this.driver = driver;
    }

    public void start(CrawlingOptions options, Consumer<Document> consumer) {
        Set<String> visitedSet = new HashSet<>();
        Queue<String> urlQueue = new LinkedList<>();
        urlQueue.add(options.getUrl());

        String crawlDomain = getCrawlDomain(options.getUrl());
        int currentDepth = 0;

        while (!urlQueue.isEmpty()) {
            if (currentDepth > options.getMaxDepth()) {
                break;
            }

            int queueSize = urlQueue.size();
            for (int i = 0; i < queueSize; i++) {
                String url = urlQueue.poll();
                if (!valid(crawlDomain, url) || visited(visitedSet, url)) {
                    continue;
                }

                crawl(url, crawlDomain, consumer);
            }

            currentDepth++;
        }
    }

    private String getCrawlDomain(String url) {
        int endIndex = url.indexOf("/", url.indexOf("://") + 3);
        endIndex = endIndex < 0 ? url.length() : endIndex;
        return url.substring(0, endIndex);
    }

    private boolean valid(String crawlDomain, String url) {
        return url != null && url.startsWith(crawlDomain) && !filters.matcher(url).matches();
    }

    private boolean visited(Set<String> visitedSet, String url) {
        if (visitedSet.contains(url)) {
            return true;
        }

        visitedSet.add(url);
        return false;
    }

    private void crawl(String validUrl, String crawlDomain, Consumer<Document> consumer) {
        try {
            WebDriver webDriver = driver.getDriver();
            webDriver.get(validUrl);
            consumer.accept(Jsoup.parse(webDriver.getPageSource(), crawlDomain));
        } catch (Exception e) {
            logger.warn("크롤링에 실패하였습니다. url : {}", validUrl, e);
        }
    }
}
