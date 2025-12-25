package steps;

import framework.core.DriverFactory;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Screenshots;
import utils.waitsUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class AddItemToBag {

    WebDriverWait webDriverWait;
    Actions actions;
    Select select;

    waitsUtils waitsUtils;

    public AddItemToBag() {
        waitsUtils = new waitsUtils(driver);
    }

    @Given("User navigates to Macy's landing page")
    public void user_navigates_to_macy_s_landing_page() throws IOException {
        driverInitialization();
    }

    @When("User enters product name {string}")
    public void user_enters_product_name(String brand) {
        WebElement searchInput = driver.findElement(By.xpath("//input[@aria-label='search input']"));
        searchInput.click();
        searchInput.sendKeys(brand);

        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(10));

        //Whenever we are having mulitple list of elements in webpage we need to handle them using waits
        List<WebElement> listOfSearch = webDriverWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='search-suggestions']/ul[@class='grid-y']/li"))));
        System.out.println("Size of search list" + listOfSearch.size());
//        waitsUtils.elementClickable(By.xpath("//div[@class='search-suggestions']/ul[@class='grid-y']/li")).click();

        //The elements
        for (WebElement brandList : listOfSearch) {
            String nameOfBrand = brandList.getText().trim();
            System.out.println("brand name " + nameOfBrand);
            if (nameOfBrand.equalsIgnoreCase("nike")) {
                brandList.click();
                break;
            }
        }

    }

    @When("User chooses category {string}")
    public void user_chooses_category(String category) {
        driver.findElement(By.xpath("//img[@alt='" + category + "']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
       /* WebElement popupMessage = driver.findElement(By.xpath("(//div/button[text()='No, Thank You!'])[1]"));
        boolean popUp = popupMessage.isDisplayed();
        if (popUp) {
            popupMessage.click();
        } else {
            System.out.println("PopUp Banner not displayed continue to select product");
        }*/
    }


    @And("User selects the product and adds it to the bag")
    public void user_selects_the_product_and_adds_it_to_the_bag() throws IOException {
        actions = new Actions(driver);
        WebElement product = driver.findElement(By.xpath("(//div/div/a[@aria-labelledby=\"Women's V5 RNR Casual Sneakers from Finish Line\"]/div)[2]"));
        actions.moveToElement(product).click().perform();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            if (windowHandle != mainWindowHandle) {
                driver.switchTo().window(windowHandle);
            }
        }

        actions = new Actions(driver);
        WebElement productSizes = driver.findElement(By.xpath("//label/span[text()='5']"));
        WebElement addToBag = driver.findElement(By.xpath("//button[@aria-label='Add To Bag']"));
        actions.moveToElement(productSizes).click().perform();
        actions.moveToElement(addToBag).click().perform();

//        Screenshots screenshots = new Screenshots(driver);
//        screenshots.takeScreenshot();
    }

    @Then("User checks the added items in bag")
    public void user_checks_the_added_items_in_bag() throws InterruptedException, IOException {
        WebElement viewBag = driver.findElement(By.xpath("//a[text()='View Bag']"));
        Screenshots screenshots = new Screenshots(driver);
        screenshots.takeScreenshotOfSpecificElement(viewBag);
        viewBag.click();

        int count = 1;
        for (int increaseQuantity = 1; increaseQuantity < 4; increaseQuantity++) {
            webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement increaseCount = webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[@aria-label='Increase quantity']"))));
            increaseCount.click();
            count++;
        }

        System.out.println("Count of items added" + count);
        Thread.sleep(10000);
        WebElement shoppingBagElement = driver.findElement(By.xpath("//a[@title='Shopping bag']/div/span"));
        String shoppingBagCount = shoppingBagElement.getText();
        Assert.assertEquals(shoppingBagCount, String.valueOf(count));

    }


    @When("User checks out to payment page")
    public void user_checks_out_to_payment_page() {
        actions = new Actions(driver);
        WebElement checkout = driver.findElement(By.xpath("//button/span[text()=' Proceed To Checkout ']"));
        actions.moveToElement(checkout).click().perform();

        WebElement checkoutGuest = driver.findElement(By.xpath("//button/span[normalize-space()='Checkout as Guest']"));
        actions.moveToElement(checkoutGuest).click().perform();
    }

    @When("User enters shipping and payment details")
    public void user_enters_shipping_and_payment_details() throws IOException {
        WebElement tableOne = driver.findElement(By.xpath("//div[@id=\"rc-shipping-method-options-list-form\"]"));
        Screenshots screenshots = new Screenshots(driver);
        screenshots.takeScreenshotOfSpecificElement(tableOne);

        driver.findElement(By.cssSelector("input[name='firstName']")).sendKeys("Kowshik");
        driver.findElement(By.cssSelector("input[name='lastName']")).sendKeys("Bhimaraju");
        driver.findElement(By.xpath("//input[@name='address.addressLine1']")).sendKeys("New York");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='input-container']/div[@id='rc-shipping-address1autocomplete-list']/div")));
        List<WebElement> addressLineDetails = driver.findElements(By.xpath("//div[@class='input-container']/div[@id='rc-shipping-address1autocomplete-list']/div"));
        for (WebElement addresses : addressLineDetails) {
            if (addresses.getText().trim().contains(" Marriott Marquis, Broadway, ")) {
                System.out.println("Addresses List: " + addresses.getText());
                addresses.click();
                break;
            }
        }


        webDriverWait = new WebDriverWait(driver, Duration.ofMinutes(10));
        driver.findElement(By.cssSelector("input[name='address.phoneNumber'")).sendKeys("9229220982");

        driver.findElement(By.xpath("//input[@name='address.addressLine2']")).sendKeys("Abcd");

        actions = new Actions(driver);
        WebElement saveAndContinue = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save & Continue']")));
        actions.moveToElement(saveAndContinue).click().perform();


        WebElement addressConfirmation = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Use the entered address']")));
        addressConfirmation.click();

        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        actions = new Actions(driver);

        actions.moveToElement(driver.findElement(By.xpath("//h3[text()='Payment Method']"))).perform();
        WebElement cardNumber = driver.findElement(By.xpath("//input[@name='cardNumber' and  @class='cc-number']"));
        cardNumber.click();
        cardNumber.sendKeys("4445222299990007");

        WebElement cardMonth = driver.findElement(By.xpath("//select[@aria-label='Expiration Date Month']"));
        select = new Select(cardMonth);
        select.selectByVisibleText("12");

        WebElement cardYear = driver.findElement(By.xpath("//select[@aria-label='Expiration Date Year']"));
        select = new Select(cardMonth);
        select.selectByVisibleText("2025");

        driver.findElement(By.xpath("//input[@class='cvv-number']")).sendKeys("222");
        driver.findElement(By.xpath("//input[@name='contact.email']")).sendKeys("kowshik.bhimaraju@gmail.com");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save & Continue']"))).click();
    }


}
