package com.identitye2e.service.glue;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PageSteps extends BaseStepDefinitions {

    static final String VEHICLE_EXISTS_TEXT = "Is this the vehicle you are looking for?";

    public void openVehicleHome() {
        gotoPage("");
    }

    public void clickStartNowButton() {
        log.debug("Title is: " + driver().getTitle());
        driver().findElement(By.xpath("//a[text()='Start now']")).click();
    }

    public boolean enterCarRegistrationAndSearch(String vehicleRegistrationNo) {
        log.info(driver().getTitle() + " for " + vehicleRegistrationNo);
        driver().getPageSource();
        WebElement element = driver().findElement(By.id("Vrm"));
        element.clear();
        element.sendKeys(vehicleRegistrationNo);
        driver().findElement(By.name("Continue")).click();
        log.debug("Title is: " + driver().getTitle());
        return driver().findElements(By.xpath("//*[contains(text(),'" + VEHICLE_EXISTS_TEXT + "')]")).stream().findFirst().isPresent();
    }

    public boolean validateVehicleMake(String makeInput) {
        log.debug("Title is: " + driver().getTitle());
        WebElement makeElement = driver().findElement(By.id("Make"));
        String make = makeElement.getAttribute("value");
        log.debug("make is: " + make);
        return makeInput.trim().equalsIgnoreCase(make);
    }

    public boolean validateVehicleColor(String colorInput) {
        log.debug("Title is: " + driver().getTitle());
        WebElement colorElement = driver().findElement(By.id("Colour"));
        String color = colorElement.getAttribute("value");
        log.debug("color is: " + color);
        return colorInput.trim().equalsIgnoreCase(color);
    }

    public void closeVehicleHome() {
        quit();
    }

}
