package crawler;

import java.util.EnumSet;

public class CrawlerMain {
    public static void main(String[] args) {
        try (WebDriverBase driverBase = new WebDriverBase()) {
            ImageCrawlingOptions options = new ImageCrawlingOptions("https://www.naver.com");
            options.setExtensions(EnumSet.of(ImageExtension.PNG));

            AnchorImageCrawler crawler = new AnchorImageCrawler(driverBase, options) {
                @Override
                public void accept(AnchorImageCrawlResult result) {
                    System.out.println("src : " + result.getImageSrc() + ", href : " + result.getAnchorHref());
                }
            };
            crawler.start();
        }
    }
}
