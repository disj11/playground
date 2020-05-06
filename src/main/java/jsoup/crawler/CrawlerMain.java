package jsoup.crawler;

import java.io.IOException;

public class CrawlerMain {
    public static void main(String[] args) throws IOException {
        CrawlerConfig config = new CrawlerConfig();
        config.setMaxDepth(0);
        config.setTimeoutMills(5000);

        Crawler crawler = new AnchorImageCrawler(config, 1024) {
            @Override
            public void accept(AnchorImageCrawlerResult anchorImageCrawlerResult) {
                System.out.println("link : " + anchorImageCrawlerResult.getLinkUrl() + ", image : " + anchorImageCrawlerResult.getImageUrl());
            }
        };
        crawler.start("https://www.naver.com");
    }
}
