package crawler;

import java.util.EnumSet;

public class ImageCrawlingOptions extends CrawlingOptions {
    private EnumSet<ImageExtension> extensions;
    private long minBytes;
    private long maxBytes;

    public ImageCrawlingOptions(String url) {
        super(url);
    }

    public EnumSet<ImageExtension> getExtensions() {
        return extensions;
    }

    public void setExtensions(EnumSet<ImageExtension> extensions) {
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
}
