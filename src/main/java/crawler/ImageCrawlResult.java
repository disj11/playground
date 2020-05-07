package crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ImageCrawlResult extends CrawlResult {
    private final Element imageElement;

    public ImageCrawlResult(Document document, int depth, Element imageElement) {
        super(document, depth);
        this.imageElement = imageElement;
    }

    public Element getImageElement() {
        return imageElement;
    }

    public String getImageSrc() {
        return imageElement.absUrl("src");
    }
}
