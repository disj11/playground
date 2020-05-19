package crawler;

public abstract class VideoCrawler extends MediaCrawler {
    public VideoCrawler(WebDriverBase driver, MediaCrawlingOptions options) {
        super(driver, options);
    }

    @Override
    public boolean allowContentType(String contentType) {
        return contentType.startsWith("video");
    }
}
