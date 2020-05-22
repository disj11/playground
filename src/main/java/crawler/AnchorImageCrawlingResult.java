package crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AnchorImageCrawlingResult extends MediaCrawlingResult {
    private final Element anchorElement;

    public AnchorImageCrawlingResult(Document document, int depth, Element element, String detectedUrl, Element anchorElement) {
        super(document, depth, element, detectedUrl);
        this.anchorElement = anchorElement;
    }

    public Element getAnchorElement() {
        return anchorElement;
    }

    public String getAnchorHref() {
        return anchorElement.absUrl("href");
    }
}
