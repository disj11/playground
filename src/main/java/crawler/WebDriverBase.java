package crawler;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverBase implements AutoCloseable {
    private final WebDriver driver;

    public WebDriverBase() {
        this.setDriver();

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--incognito");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
        this.driver = new ChromeDriver(options);
    }

    private void setDriver() {
        // set chrome driver
        ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
    }

    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public void close() {
        this.driver.quit();
    }
}
