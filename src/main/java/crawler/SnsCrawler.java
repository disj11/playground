package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Queue;

/**
 * 인스타 / 페이스북 크롤링
 */
public abstract class SnsCrawler extends AnchorImageCrawler {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SnsCrawler.class);

    public SnsCrawler(WebDriverBase driver, SnsCrawlingOptions options) {
        super(driver, options);
    }

    @Override
    protected void crawl(String validUrl, String crawlDomain, int depth, Queue<String> urlQueue) {
        try {
            WebDriver webDriver = driver.getDriver();
            webDriver.get(validUrl);
            hook();

            int maxNumberOfPaging = getMaxNumberOfPaging();
            for (int i = 0; i < maxNumberOfPaging; i++) {
                Document document = Jsoup.parse(webDriver.getPageSource(), crawlDomain);
                accept(new CrawlingResult(document, depth));

                Elements detectedPages = document.select("a");
                for (Element page : detectedPages) {
                    String pageUrl = page.absUrl("href");
                    if (valid(crawlDomain, pageUrl)) {
                        urlQueue.add(pageUrl);
                    }
                }

                if (i != maxNumberOfPaging - 1) {
                    if (!paging()) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("크롤링에 실패하였습니다. url : {}", validUrl, e);
        }
    }

    protected boolean paging() {
        WebDriver webDriver = driver.getDriver();
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        String lastHeight = executor.executeScript("return document.body.scrollHeight").toString();

        WebElement body = webDriver.findElement(By.tagName("body"));
        executor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        executor.executeScript("arguments[0].setAttribute('style', 'overflow:auto')", body);
        sleep(1500);

        String newHeight = executor.executeScript("return document.body.scrollHeight").toString();
        return !lastHeight.equals(newHeight);
    }

    private int getMaxNumberOfPaging() {
        return ((SnsCrawlingOptions) options).getMaxNumberOfPaging();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
