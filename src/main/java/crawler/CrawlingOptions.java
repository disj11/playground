package crawler;

public class CrawlingOptions {
    private final String url;
    private int maxDepth;

    public CrawlingOptions(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
}
