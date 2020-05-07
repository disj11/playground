package crawler;

import org.jsoup.nodes.Document;

public class CrawlResult {
    private final Document document;
    private final int depth;

    public CrawlResult(Document document, int depth) {
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
