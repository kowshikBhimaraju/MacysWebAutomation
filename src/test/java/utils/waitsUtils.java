package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class waitsUtils {


    WebDriver driver;
    public waitsUtils(WebDriver driver) {
        this.driver=driver;
    }

    //
    public WebElement elementClickable(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void fluentWait() {
        new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(250)).ignoring(StaleElementReferenceException.class).until(driver -> {

            return null;
        });
    }


}
