package crawler;

import org.jsoup.nodes.Document;

public class CrawlingResult {
    private final Document document;
    private final int depth;

    public CrawlingResult(Document document, int depth) {
        this.document = document;
        this.depth = depth;
    }

    public Document getDocument() {
        return document;
    }

    public int getDepth() {
        return depth;
    }
}
