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
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
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
