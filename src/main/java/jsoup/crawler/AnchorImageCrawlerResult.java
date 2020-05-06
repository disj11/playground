package jsoup.crawler;

public class AnchorImageCrawlerResult {
    private String linkUrl;
    private String imageUrl;

    public AnchorImageCrawlerResult(String linkUrl, String imageUrl) {
        this.linkUrl = linkUrl;
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
