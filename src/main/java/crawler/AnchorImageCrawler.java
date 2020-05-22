package crawler;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

/**
 * 이미지와 이미지에 걸려있는 링크를 크롤링
 */
public abstract class AnchorImageCrawler extends ImageCrawler {
    public AnchorImageCrawler(WebDriverBase driver, MediaCrawlingOptions options) {
        super(driver, options);
    }

    public abstract void accept(AnchorImageCrawlingResult result);

    @Override
    public void accept(MediaCrawlingResult result) {
        accept(new AnchorImageCrawlingResult(
                result.getDocument(),
                result.getDepth(),
                result.getElement(),
                result.getDetectedUrl(),
                parent(result.getElement())
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
