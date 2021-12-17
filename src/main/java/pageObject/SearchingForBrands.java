package pageObject;

import bases.BaseClass;

import bases.ReadExcel;
import com.google.common.base.CharMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;

import java.util.*;

public class SearchingForBrands extends BaseClass {
    ReadExcel read = new ReadExcel();
    private double sum = 0;

    //search for the brand
    public void searchForBrand(String brandNames) {
        Reporter.log("The brand name is " + brandNames);
        enterText(By.name("search"), brandNames);

    }

    //printing quantity from excel
    public void printQuantity(String quantity) {
        Reporter.log("brand quantity from excel is " + quantity);
    }

    //clicking the search button
    public void clickSearchButton() {
        clickElement(By.xpath("//button[@class='button search-btn search-icon']"));

    }

    //Clearing the search field
    public void clearField() {
        clearTextbox(By.name("search"));
    }

    //getting the total number of deals
    public void numberOfDeals() {
        try{
            WebElement element = driver.findElement(By.xpath("//div[@class='grid-x toolbar-module_container_1Uk1k']/div[2]/div"));
            String numDeals = getText(By.xpath("//div[@class='grid-x toolbar-module_container_1Uk1k']/div[2]/div"));
            String totalChar = CharMatcher.inRange('0', '9').retainFrom(numDeals);
            int items = Integer.parseInt(totalChar);
            if (items >0 && element.isDisplayed()) {
                Reporter.log("The total number items for the search is " + items);
                Assert.assertTrue(true, "Items are more than 0");
            } else {
                Reporter.log("Item not available");
                throw new SkipException("Skip Test");
            }
        } catch (Exception e) {
            clearField();
            throw new SkipException("No such element is displayed");
        }

    }

    //Selecting the first item
    public void itemSelect() {

        List<WebElement> allElements = driver.findElements(By.xpath("//div/div/a[@class='product-anchor product-card-module_product-anchor_TUCBV']"));
        waitForClick(10, By.xpath("//div/div/a[@class='product-anchor product-card-module_product-anchor_TUCBV']"));
        allElements.get(0).click();

    }

    //switching the tabs
    public void switchTabs() {
        ArrayList<String> newTb = new ArrayList<>(driver.getWindowHandles());
        //switch to new tab
        driver.switchTo().window(newTb.get(1));
        Reporter.log("Page title of new tab: " + driver.getTitle());

    }


    //initial price before clicking Add To Cart button
    public void initialPrice() {
        waitForElement(10, By.xpath("//div[@id='shopfront-app']/div[4]/div[1]/div[2]/aside/div[1]/div[1]/div[1]/span"));
        String initialPrice = getText(By.xpath("//div[@id='shopfront-app']/div[4]/div[1]/div[2]/aside/div[1]/div[1]/div[1]/span"));
        Reporter.log("Initial price is R" + initialPrice);
    }
    public  void addToCart()
    {
        clickElement(By.xpath("//a[@class='button  expanded add-to-cart-button add-to-cart-button-module_add-to-cart-button_1a9gT']"));
        driver.switchTo().activeElement();
    }
    //assert that the Added To cart Text is displayed
    public void addedToCartPopUp() {

        waitForElement(20, By.xpath("//body[@id='body']/div[8]/div[1]/div/div/div/div/div[1]/div[2]/span/span/span[1]"));

        String expected = "Added to cart";
        String actual = getText(By.xpath("//body[@id='body']/div[8]/div[1]/div/div/div/div/div[1]/div[2]/span/span/span[1]"));
        Assert.assertEquals(actual, expected);
        Reporter.log( "The \"" + actual + "\" text is displayed at the top of the popup");

    }
    public void clickGoToCart()
    {
        clickElement(By.xpath("//body[@id='body']/div[8]/div[1]/div/div/div/div/div[2]/div/div[1]/div/div[1]/div/div/div[2]/div[3]/button"));
    }

    public void addMoreForFreeDelivery(double totalAmount) {
        try {
            WebElement element = driver.findElement(By.xpath("//div[@class='show-free-delivery free-delivery-module_free-delivery-tab_3xNIm']"));
             if (element.isDisplayed()) {
                String freeDelivery = getText(By.xpath("//section/div[2]/div[2]/div/div[1]/div[2]/div/div/div/p/strong[1]"));
                String extractDigits = CharMatcher.inRange('0', '9').retainFrom(freeDelivery);
               int amountForFreeDelivery = Integer.parseInt(extractDigits);

                if (totalAmount < amountForFreeDelivery) {
                    double addAmount = amountForFreeDelivery - totalAmount;
                    Reporter.log("You need to add a product that is worth R" + addAmount + " to get free delivery.");
                    String expected = "Spend R" + amountForFreeDelivery + " or more to get FREE DELIVERY. T&Cs apply";
                    String actual = getText(By.xpath("//section/div[2]/div[2]/div/div/div[2]/div/div/div/p"));
                    Assert.assertEquals(actual, expected);
                    Reporter.log(actual);
                }
            }
            }catch(Exception e){
            Reporter.log(" The cart amount is R" + totalAmount);
            Reporter.log("Element not displayed");
            }

    }

    //Switching back to the parent Tab
    public void switchToParentWindow() {
        ArrayList<String> newTb = new ArrayList<>(driver.getWindowHandles());
        driver.close();
        //Switch back to parent tab
        driver.switchTo().window(newTb.get(0));

    }

    //calculating the total price of each item
    public void calculateTotalPrice(String brand,String quantity1) {
        String priceTotal = getText(By.xpath("//section/div[2]/div[1]/div[1]/div/div/article/div/div/div[3]/div/div[1]/div/div[2]/div[1]/div/span"));
        String extractDigitsFromPrice = CharMatcher.inRange('0', '9').retainFrom(priceTotal);
        double convertInitialPrice = Double.parseDouble(extractDigitsFromPrice);

        selectQuantity(brand,quantity1);
        int totalQuantity = Integer.parseInt(quantity1);
        double initialPriceForProduct = (convertInitialPrice / totalQuantity);

        //Initial Price
        Reporter.log("The initial Price is R" + initialPriceForProduct);
        //getting the total price for each item added to the cart


        double totalAmount = totalQuantity * initialPriceForProduct;
        Reporter.log("Total Amount R" + totalAmount);
        totalPrice(totalAmount);
        addMoreForFreeDelivery(totalAmount);
    }


    //getting the quantity
    public void selectQuantity(String brand,String quantity1) {

        try {
            waitForClick(10, By.id("cart-item_undefined"));
            clickElement(By.id("cart-item_undefined"));
            Select select = new Select(driver.findElement(By.id("cart-item_undefined")));
            select.selectByVisibleText(quantity1);
            Reporter.log("Quantity selected is " + quantity1);

            String quantity;
            WebElement error = driver.findElement(By.xpath("//article/div/div/div[1]/div/div/div/div[2]/div/div"));
                if (error.isDisplayed()) {
                    String errorMessage = getText(By.xpath("//article/div/div/div[1]/div/div/div/div[2]/div/div"));
                    Reporter.log(errorMessage);
                    if(errorMessage.length()==45) {
                        //extract the available quantity of the product from error message
                        quantity = errorMessage.substring(33, errorMessage.length() - 11);
                        Reporter.log("Available quantity for the product is " + quantity);
                        if (!quantity.equals(quantity1)) {
                            select.selectByVisibleText(quantity);
                            read.writeToExcel(brand,quantity);
                        }
                    }
                }
        } catch (Exception e) {
            Reporter.log("No error message displayed");
        }

    }

    public void totalPrice(double totalAmount) {

        //calculate the total price for the items in the cart
        sum = sum + totalAmount;
        System.out.println("The total amount for the products in the cart is R" + sum);


        //validate the amount displayed as total on the cart is the correct amount
        String cartAmount = getText(By.xpath("//div[@id='shopfront-app']/div[4]/div[2]/section/div[2]/div[2]/div/div/div/div/div[1]/div/div[2]/p/span"));
        String extractNumbers_FromCartAmount = CharMatcher.inRange('0', '9').retainFrom(cartAmount);
        double convertCartAmount = Double.parseDouble(extractNumbers_FromCartAmount);
        if (sum == convertCartAmount) {
            Assert.assertEquals(convertCartAmount, sum);
            Reporter.log("The summary is the correct amount");
        } else {
            Reporter.log("The amounts are different");
        }
    }
    public void removeItemFromCart() {
        waitForClick(10,By.xpath("//button[@class='button clear remove-item']"));
        clickElement(By.xpath("//button[@class='button clear remove-item']"));
    }
}
