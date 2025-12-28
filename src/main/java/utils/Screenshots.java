package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Screenshots {

    WebDriver driver;

    public Screenshots(WebDriver driver) {
        this.driver = driver;
    }

    public void takeScreenshot() throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File destination = new File("C:/Users/BH23038/IdeaProjects/seleniumCucumberAutomation/src/test/resources/page_" + timeStamp + ".png");
        destination.getParentFile().mkdirs();
        FileHandler.copy(src, destination);
    }

    public void takeScreenshotOfSpecificElement(WebElement webElement) throws IOException {

        File src = webElement.getScreenshotAs(OutputType.FILE);
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File destination = new File("C:/Users/BH23038/IdeaProjects/seleniumCucumberAutomation/src/test/resources/page_" + timeStamp + ".png");
        destination.getParentFile().mkdirs();
        FileHandler.copy(src, destination);
    }


}
