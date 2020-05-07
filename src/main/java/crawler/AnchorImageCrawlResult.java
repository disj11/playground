package crawler;

import org.jsoup.nodes.Element;

public class AnchorImageCrawlResult {
    private final Element imgElement;
    private final Element anchorElement;

    public AnchorImageCrawlResult(Element imgElement, Element anchorElement) {
        this.imgElement = imgElement;
        this.anchorElement = anchorElement;
    }

    public Element getImgElement() {
        return imgElement;
    }

    public Element getAnchorElement() {
        return anchorElement;
    }

    public String getImgSrc() {
        return imgElement.absUrl("src");
    }

    public String getAnchorHref() {
        return anchorElement.absUrl("href");
    }
}
