package com.identitye2e.service.glue;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;

@Slf4j
@Component
public class WebDriverSession {

    @Value("${vehicle.selenium.driver.path}")
    private String driverPath;
    @Value("${vehicle.selenium.browser}")
    private String browser = "CHROME_HEADLESS";

    private WebDriver webDriver;


    public WebDriver getWebDriverForBrowser(String browserType) {

        System.setProperty("webdriver.chrome.driver", driverPath);
        System.setProperty("webdriver.gecko.driver", driverPath);

        WebDriver driver;

        String driverTypeUpper = browserType.toUpperCase();
        log.info("Using the driver: " + driverTypeUpper);
        switch (driverTypeUpper) {
            case "FIREFOX":
                driver = new FirefoxDriver();
                break;
            case "CHROME_HEADLESS":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                driver = new ChromeDriver(options);
                break;
            case "CHROME":
                driver = new ChromeDriver();
                break;
            case "PHANTOM":
                System.setProperty("phantomjs.binary.path", "src/main/resources/drivers/phantomjs");
                DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
                desiredCapabilities.setCapability("phantomjs.cli.args", asList("--disk-cache=true", "--load-images=false"));
                driver = new PhantomJSDriver(desiredCapabilities);
                break;
            default:
                throw new RuntimeException("Unknown driver type " + driverTypeUpper);
        }

        return driver;
    }

    public WebDriver getWebDriver(String browserType) {
        if (webDriver == null) {
            setWebDriver(browserType);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                quitWebDriver();
            }));
        }
        return webDriver;
    }

    public WebDriver getWebDriver() {
        String browserToUse = System.getProperty("acceptanceTest.browser", browser);
        return getWebDriver(browserToUse);
    }

    public void setWebDriver(String browserType) {

        webDriver = getWebDriverForBrowser(browserType);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void quitWebDriver() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }
}