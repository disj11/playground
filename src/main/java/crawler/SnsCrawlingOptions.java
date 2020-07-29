package crawler;

public class SnsCrawlingOptions extends MediaCrawlingOptions {
    public SnsCrawlingOptions(String url) {
        super(url);
    }

    private int maxNumberOfPaging;

    public int getMaxNumberOfPaging() {
        return Math.max(maxNumberOfPaging, 1);
    }

    public void setMaxNumberOfPaging(int maxNumberOfPaging) {
        this.maxNumberOfPaging = maxNumberOfPaging;
    }
}
