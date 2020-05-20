package crawler;

/**
 * contentType 이 이미지인 것을 크롤링
 */
public abstract class ImageCrawler extends MediaCrawler {
    public ImageCrawler(WebDriverBase driver, MediaCrawlingOptions options) {
        super(driver, options);
    }

    @Override
    public boolean allowContentType(String contentType) {
        return contentType.startsWith("image");
    }
}
