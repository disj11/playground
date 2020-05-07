package crawler;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public abstract class AnchorImageCrawler extends ImageCrawler {
    public AnchorImageCrawler(WebDriverBase driver, ImageCrawlingOptions options) {
        super(driver, options);
    }

    public abstract void accept(AnchorImageCrawlResult result);

    @Override
    public void accept(ImageCrawlResult result) {
        accept(new AnchorImageCrawlResult(
                result.getDocument(),
                result.getDepth(),
                result.getImageElement(),
                parent(result.getImageElement())
        ));
    }

    private Element parent(Element elem) {
        Element anchor = elem.parent();
        String tagName = "a";

        while (anchor.hasParent() && !tagName.equals(anchor.tagName().toLowerCase())) {
            anchor = anchor.parent();
        }

        if (tagName.equals(anchor.tagName().toLowerCase())) {
            return anchor;
        }

        return new Element(Tag.valueOf("a"), elem.baseUri());
    }
}
