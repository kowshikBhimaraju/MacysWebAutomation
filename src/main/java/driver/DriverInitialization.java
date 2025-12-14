package driver;


import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DriverInitialization {
    private String url = null;
    private String browser = null;
    ChromeOptions chromeOptions;
    EdgeOptions edgeOptions;
    public WebDriver driver;


    public void driverInitialization() throws IOException {
        getUrlAnBrowser();
        initializeDriver();
    }

    public void getUrlAnBrowser() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("application.properties");
        properties.load(fileInputStream);
        url = properties.getProperty("url");
        browser = properties.getProperty("browser");
        System.out.println("URL:::::: " + url);
        System.out.println("BROWSER:::::: " + browser);
    }

    public void initializeDriver() {
        switch (browser) {
            case "chrome":
                chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
                driver.get(url);
                break;
            case "edge":
                edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                driver = new EdgeDriver(edgeOptions);
                driver.get(url);
                break;
        }
    }


}
