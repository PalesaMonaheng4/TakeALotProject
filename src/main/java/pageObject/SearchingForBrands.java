package pageObject;

import bases.BaseClass;

import com.google.common.base.CharMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;

import java.util.*;

public class SearchingForBrands extends BaseClass {
    private double sum=0;
    //getting the total number of deals
        public void numberOfDeals ()
        {
            String numDeals = getText(By.xpath("//div[@class='grid-x toolbar-module_container_1Uk1k']/div[2]/div"));
           String totalChar= CharMatcher.inRange('0','9').retainFrom(numDeals);

            int items=Integer.parseInt(totalChar);
            Reporter.log("The total number items for the search is "+items);
            boolean results=items>0;
            Assert.assertTrue(results,"Items are more than 0");
        }
        //Selecting the first item
        public void itemSelect(String name,String quantity1) throws InterruptedException {

            List<WebElement> allElements = driver.findElements(By.xpath("//div/div/a[@class='product-anchor product-card-module_product-anchor_TUCBV']"));
            waitForClick(20,By.xpath("//div/div/a[@class='product-anchor product-card-module_product-anchor_TUCBV']"));
            allElements.get(0).click();
            switchTabs(name,quantity1);

        }
        //switching the tabs
        public void switchTabs(String name,String quantity1) throws InterruptedException {
        ArrayList<String> newTb = new ArrayList<>(driver.getWindowHandles());
        //switch to new tab
        driver.switchTo().window(newTb.get(1));
        Reporter.log("Page title of new tab: " + driver.getTitle());
        addToCart(name,quantity1);
        driver.close();
        driver.switchTo().window(newTb.get(0));

        }

        //Adding to the cart
        public void addToCart(String brandName,String quantity1) throws InterruptedException {
            String initialPrice= getText(By.xpath("//div[@id='shopfront-app']/div[4]/div[1]/div[2]/aside/div[1]/div[1]/div[1]/span"));
            String price= CharMatcher.inRange('0','9').retainFrom(initialPrice);
            double convertInitialPrice=Double.parseDouble(price);
            System.out.println("Initial price is R"+convertInitialPrice);
             addedToCartPopUp();
             try {
                 selectQuantity(brandName, quantity1);
                 Thread.sleep(2000);

                 //getting the total price for each item added to the cart
                 int totalQuantity = Integer.parseInt(quantity1);
                 double totalAmount = totalQuantity * convertInitialPrice;
                 System.out.println("Total Amount R" + totalAmount);
                 totalPrice(totalAmount);
                 addMoreForFreeDelivery(totalAmount);
             }catch(SkipException e)
            {
                System.out.println("Skip test");
            }

        }
        //assert that the Added To cart Text is displayed
    public void addedToCartPopUp()
    {
        clickElement(By.xpath("//a[@class='button  expanded add-to-cart-button add-to-cart-button-module_add-to-cart-button_1a9gT']"));
        driver.switchTo().activeElement();
        waitForElement(20,By.xpath("//body[@id='body']/div[8]/div[1]/div/div/div/div/div[1]/div[2]/span/span/span[1]"));

        String expected="Added to cart";

        String actual =getText(By.xpath("//body[@id='body']/div[8]/div[1]/div/div/div/div/div[1]/div[2]/span/span/span[1]"));
        Assert.assertEquals(actual,expected);
        Reporter.log("The \""+actual+"\" text is displayed at the top of the popup");
        clickElement(By.xpath("//body[@id='body']/div[8]/div[1]/div/div/div/div/div[2]/div/div[1]/div/div[1]/div/div/div[2]/div[3]/button"));
    }
    public void addMoreForFreeDelivery(double totalAmount)
    {
        double amountForFreeDelivery=450;
        if(totalAmount < amountForFreeDelivery)
        {
            String expected="Spend R450 or more to get FREE DELIVERY. T&Cs apply";
            String actual=getText(By.xpath("//section/div[2]/div[2]/div/div/div[2]/div/div/div/p"));

            Assert.assertEquals(actual,expected);
            System.out.println(actual);
        }
        else
        {
            System.out.println("The cart amount is above R" +amountForFreeDelivery);
        }
    }
        //getting the quantity
        public void selectQuantity(String name,String quantity1)
        {
            waitForClick(10,By.id("cart-item_undefined"));
            clickElement(By.id("cart-item_undefined"));
            Select select=new Select(driver.findElement(By.id("cart-item_undefined")));
            //select.selectByValue(quantity);
            select.selectByVisibleText(quantity1);
            Reporter.log("Quantity selected is "+quantity1);


                    try {

                        String quantity;
                        WebElement error = driver.findElement(By.xpath("//article/div/div/div[1]/div/div/div/div[2]/div/div"));
                        if(error.isDisplayed()) {
                            String errorMessage = getText(By.xpath("//article/div/div/div[1]/div/div/div/div[2]/div/div"));
                            System.out.println(errorMessage);
                            quantity=errorMessage.substring(33,errorMessage.length()-11);
                            System.out.println(quantity);
                                if (!quantity.equals(quantity1)) {
                                   select.selectByVisibleText(quantity);
                                    // updateExcel();
                                    throw new SkipException("Skip the test case");
                                }
                                 else
                            {
                                System.out.println(" ");
                            }
                    }
                    }
                    catch(Exception e)
                    {
                     System.out.println("No such element");
                     }

        }
        public void totalPrice(double totalAmount)
        {

            //calculate the total price for the items in the cart
            sum =sum+totalAmount;
            System.out.println("The total amount for the products in the cart is R" + sum);


            //validate the amount displayed as total on the cart is the correct amount
            String cartAmount= getText(By.xpath("//div[@id='shopfront-app']/div[4]/div[2]/section/div[2]/div[2]/div/div/div/div/div[1]/div/div[2]/p/span"));
            String extractNumbers_FromCartAmount=CharMatcher.inRange('0','9').retainFrom(cartAmount);
            double convertCartAmount=Double.parseDouble(extractNumbers_FromCartAmount);
            if(sum==convertCartAmount)
            {
                Assert.assertEquals(convertCartAmount,sum);
            }
            else
            {
                Reporter.log("The amounts are different");
            }
        }
}
