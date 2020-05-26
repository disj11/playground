package crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class CrawlerUtils {
    public static boolean validExt(String imageUrl, java.util.List<String> imageExtensions) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return false;
        }

        if (imageExtensions == null || imageExtensions.isEmpty()) {
            return true;
        }

        Pattern pattern = Pattern.compile(".*(\\.(" + String.join("|", imageExtensions) +"))$");
        return pattern.matcher(imageUrl).matches();
    }

    public static boolean validCapacity(String imageUrl, long minBytes, long maxBytes) throws IOException {
        long contentLength = getContentLength(imageUrl);
        if (contentLength < minBytes) {
            return false;
        }

        return maxBytes == 0 || contentLength <= maxBytes;
    }

    public static String getContentType(String url) throws IOException {
        return getHeader(url, "Content-Type");
    }

    public static String resolve(String baseUrl, String relUrl) {
        try {
            URL base;
            try {
                base = new URL(baseUrl);
            } catch (MalformedURLException var5) {
                URL abs = new URL(relUrl);
                return abs.toExternalForm();
            }

            return resolve(base, relUrl).toExternalForm();
        } catch (MalformedURLException var6) {
            return "";
        }
    }

    public static URL resolve(URL base, String relUrl) throws MalformedURLException {
        if (relUrl.startsWith("?")) {
            relUrl = base.getPath() + relUrl;
        }

        if (relUrl.indexOf(46) == 0 && base.getFile().indexOf(47) != 0) {
            base = new URL(base.getProtocol(), base.getHost(), base.getPort(), "/" + base.getFile());
        }

        return new URL(base, relUrl);
    }

    private static long getContentLength(String url) throws IOException {
        String length = getHeader(url, "Content-Length");
        return Long.parseLong(length);
    }

    private static Connection.Response getResp(String url) throws IOException {
        return Jsoup.connect(url)
                .method(Connection.Method.HEAD)
                .ignoreContentType(true)
                .execute();
    }

    private static String getHeader(String url, String s) throws IOException {
        return getResp(url).header(s);
    }
}
