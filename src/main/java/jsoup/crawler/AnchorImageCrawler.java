package jsoup.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * 이미지 크롤러
 * 이미지와 이미지에 걸린 링크를 찾아준다.
 */
public abstract class AnchorImageCrawler extends Crawler implements Consumer<AnchorImageCrawlerResult> {
    private final int minImageBytes;

    public AnchorImageCrawler(CrawlerConfig config, int minImageBytes) {
        super(config);
        this.minImageBytes = minImageBytes;
    }

    @Override
    void visit(Document doc) {
        Elements images = doc.getElementsByTag("img");
        for (Element image : images) {
            String src = image.absUrl("src");
            if (src.trim().isEmpty()) {
                continue;
            } else if (getContentLength(src) < minImageBytes) {
                continue;
            }

            Element anchor = parent(image, "a");
            if (anchor != null) {
                String href = anchor.absUrl("href");
                accept(new AnchorImageCrawlerResult(href, src));
            } else {
                accept(new AnchorImageCrawlerResult("", src));
            }
        }
    }

    private Element parent(Element elem, String parentTagName) {
        Element anchor = elem.parent();
        String tagName = parentTagName.toLowerCase();

        while (anchor.hasParent() && !tagName.equals(anchor.tagName().toLowerCase())) {
            anchor = anchor.parent();
        }

        if (tagName.equals(anchor.tagName().toLowerCase())) {
            return anchor;
        }

        return null;
    }

    private int getContentLength(String url) {
        try {
            Connection.Response resp = Jsoup.connect(url)
                    .method(Connection.Method.HEAD)
                    .ignoreContentType(true)
                    .execute();
            String length = resp.header("Content-Length");
            return Integer.parseInt(length);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
