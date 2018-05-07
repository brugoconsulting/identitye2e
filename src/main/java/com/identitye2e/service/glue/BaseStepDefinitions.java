package com.identitye2e.service.glue;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BaseStepDefinitions {

    static final String BASE_URL = "https://www.gov.uk/get-vehicle-information-from-dvla";

    @Autowired
    private WebDriverSession webDriverSession;

    protected String getBaseUrl() {
        return BASE_URL;
    }

    protected void gotoPage(String page) {
        driver().navigate().to(getBaseUrl() + "/" + page);
    }

    protected WebDriver driver() {
        return webDriverSession.getWebDriver();
    }

    protected void quit() {
        webDriverSession.quitWebDriver();
    }
}
