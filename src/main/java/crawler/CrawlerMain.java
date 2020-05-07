package crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlerMain {
    private static final Logger logger = LoggerFactory.getLogger(CrawlerMain.class);
    public static void main(String[] args) {
        try (WebDriverBase driverBase = new WebDriverBase()) {
            AnchorImageCrawler crawler = new AnchorImageCrawler(driverBase);
            ImageCrawlingOptions options = new ImageCrawlingOptions("https://www.naver.com");
            crawler.start(options, result -> System.out.println("src : " + result.getImgSrc() + ", href : " + result.getAnchorHref()));
        }
    }
}
