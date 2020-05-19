package crawler;

import java.util.List;

public class MediaCrawlingOptions extends CrawlingOptions {
    private java.util.List<String> extensions;
    private long minBytes;
    private long maxBytes;
    private int limit;

    public MediaCrawlingOptions(String url) {
        super(url);
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<String> extensions) {
        this.extensions = extensions;
    }

    public long getMinBytes() {
        return minBytes;
    }

    public void setMinBytes(long minBytes) {
        this.minBytes = minBytes;
    }

    public long getMaxBytes() {
        return maxBytes;
    }

    public void setMaxBytes(long maxBytes) {
        this.maxBytes = maxBytes;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
