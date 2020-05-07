package crawler;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.function.Consumer;

public class AnchorImageCrawler {
    private final ImageCrawler crawler;

    public AnchorImageCrawler(WebDriverBase driver) {
        this.crawler = new ImageCrawler(driver);
    }

    public void start(ImageCrawlingOptions options, Consumer<AnchorImageCrawlResult> consumer) {
        crawler.start(options, imageElement -> consumer.accept(new AnchorImageCrawlResult(imageElement, parent(imageElement))));
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
