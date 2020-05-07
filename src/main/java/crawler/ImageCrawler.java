package crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.regex.Pattern;

public abstract class ImageCrawler extends Crawler<ImageCrawlingOptions> {
    private static final Logger logger = LoggerFactory.getLogger(ImageCrawler.class);
    public ImageCrawler(WebDriverBase driver, ImageCrawlingOptions options) {
        super(driver, options);
    }

    public abstract void accept(ImageCrawlResult result);

    @Override
    public void accept(CrawlResult result) {
        Document document = result.getDocument();
        Elements imgElements = document.select("img");

        for (Element imgElement : imgElements) {
            String imageUrl = imgElement.absUrl("src");
            if (!validExt(imageUrl, options.getExtensions()) || !validCapacity(imageUrl, options.getMinBytes(), options.getMaxBytes())) {
                continue;
            }

            accept(new ImageCrawlResult(result.getDocument(), result.getDepth(), imgElement));
        }
    }

    private boolean validExt(String imageUrl, EnumSet<ImageExtension> imageExtensions) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return false;
        }

        if (imageExtensions == null || imageExtensions.isEmpty()) {
            return true;
        }

        String[] extensions = new String[imageExtensions.size()];
        int i = 0;

        for (Enum<ImageExtension> imageExtension : imageExtensions) {
            extensions[i++] = imageExtension.name().toLowerCase();
        }

        Pattern pattern = Pattern.compile(".*(\\.(" + String.join("|", extensions) +"))$");
        return pattern.matcher(imageUrl).matches();
    }

    private boolean validCapacity(String imageUrl, long minBytes, long maxBytes) {
        long contentLength = getContentLength(imageUrl);
        if (contentLength < minBytes) {
            return false;
        }

        return maxBytes == 0 || contentLength <= maxBytes;
    }

    private long getContentLength(String url) {
        try {
            Connection.Response resp = Jsoup.connect(url)
                    .method(Connection.Method.HEAD)
                    .ignoreContentType(true)
                    .execute();
            String length = resp.header("Content-Length");
            return Long.parseLong(length);
        } catch (Exception e) {
            logger.warn("Content-Length 확인해 실패하였습니다. url : {}", url);
            return 0;
        }
    }
}
