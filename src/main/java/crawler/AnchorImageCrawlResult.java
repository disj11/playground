package crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AnchorImageCrawlResult extends ImageCrawlResult {
    private final Element anchorElement;

    public AnchorImageCrawlResult(Document document, int depth, Element imageElement, Element anchorElement) {
        super(document, depth, imageElement);
        this.anchorElement = anchorElement;
    }

    public Element getAnchorElement() {
        return anchorElement;
    }

    public String getAnchorHref() {
        return anchorElement.absUrl("href");
    }
}
