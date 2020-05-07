package crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.EnumSet;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class ImageCrawler {
    private final Crawler crawler;

    public ImageCrawler(WebDriverBase driver) {
        this.crawler = new Crawler(driver);
    }

    public void start(ImageCrawlingOptions options, Consumer<Element> consumer) {
        crawler.start(options, document -> {
            Elements imgElements = document.select("img");
            for (Element imgElement : imgElements) {
                String imageUrl = imgElement.absUrl("src");
                if (!validExt(imageUrl, options.getExtensions()) || !validCapacity(imageUrl, options.getMinBytes(), options.getMaxBytes())) {
                    continue;
                }

                consumer.accept(imgElement);
            }
        });
    }

    private boolean validExt(String imageUrl, EnumSet<ImageExtension> imageExtensions) {
        if (imageExtensions == null) {
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
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
