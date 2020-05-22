package crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MediaCrawlingResult extends CrawlingResult {
    private final Element element;
    private final String detectedUrl;

    public MediaCrawlingResult(Document document, int depth, Element element, String detectedUrl) {
        super(document, depth);
        this.element = element;
        this.detectedUrl = detectedUrl;
    }

    public Element getElement() {
        return element;
    }

    public String getDetectedUrl() {
        return detectedUrl;
    }
}
