package framework.core;


import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public final class DriverFactory {

    private DriverFactory() {

    }


    public static WebDriver create() throws IOException {

        /*The below statements are only to fetch the data from properties file*/
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("application.properties");
        properties.load(fileInputStream);
        String url = properties.getProperty("url");
        String browser = properties.getProperty("browser");
        System.out.println("URL:::::: " + url);
        System.out.println("BROWSER:::::: " + browser);

        /*Below steps initialize and declare a driver return driver session */
        WebDriver driver;
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + browser);
        }
        driver.get(url);
        return driver;
    }
}




