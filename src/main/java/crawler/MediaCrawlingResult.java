package crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MediaCrawlingResult extends CrawlingResult {
    private final Element element;

    public MediaCrawlingResult(Document document, int depth, Element element) {
        super(document, depth);
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    public String getAbsSrc() {
        return element.absUrl("src");
    }
}
