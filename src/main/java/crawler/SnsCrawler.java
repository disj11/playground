package crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class SnsCrawler extends AnchorImageCrawler {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SnsCrawler.class);

    public SnsCrawler(WebDriverBase driver, SnsCrawlingOptions options) {
        super(driver, options);
    }

    @Override
    public void hook() {
        paging();
    }

    protected void paging() {
        WebDriver webDriver = driver.getDriver();
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;

        int maxNumberOfPaging = ((SnsCrawlingOptions) options).getMaxNumberOfPaging();
        String lastHeight = executor.executeScript("return document.body.scrollHeight").toString();
        for (int i = 0; i < maxNumberOfPaging; i++) {
            WebElement body = webDriver.findElement(By.tagName("body"));
            executor.executeScript("arguments[0].setAttribute('style', 'overflow:auto')", body);

            executor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            sleep(1500);

            String newHeight = executor.executeScript("return document.body.scrollHeight").toString();
            if (lastHeight.equals(newHeight)) {
                break;
            }

            lastHeight = newHeight;
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
