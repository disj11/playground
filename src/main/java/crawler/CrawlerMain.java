package crawler;

import java.util.Collections;

public class CrawlerMain {
    public static void main(String[] args) {
        try (WebDriverBase driverBase = new WebDriverBase()) {
            MediaCrawlingOptions options = new MediaCrawlingOptions("https://www.naver.com");
            options.setExtensions(Collections.singletonList("png"));

            AnchorImageCrawler crawler = new AnchorImageCrawler(driverBase, options) {
                @Override
                public void accept(AnchorImageCrawlingResult result) {
                    System.out.println(result.getAbsSrc() + " / " + result.getAnchorHref());
                }
            };
            crawler.start();
        }
    }
}
