package crawler;

public class CrawlerMain {
    public static void main(String[] args) {
        try (WebDriverBase driverBase = new WebDriverBase()) {
            AnchorImageCrawler crawler = new AnchorImageCrawler(driverBase);
            ImageCrawlingOptions options = new ImageCrawlingOptions("https://www.naver.com");
            crawler.start(options, result -> System.out.println("src : " + result.getImgSrc() + ", href : " + result.getAnchorHref()));
        }
    }
}
