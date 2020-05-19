package crawler;

public abstract class ImageCrawler extends MediaCrawler {
    public ImageCrawler(WebDriverBase driver, MediaCrawlingOptions options) {
        super(driver, options);
    }

    @Override
    public boolean allowContentType(String contentType) {
        return contentType.startsWith("image");
    }
}
