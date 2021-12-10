package bases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseClass {

    public static WebDriver driver;
    //Declare the WebDriver
    //constructor of base class

    public BaseClass()
    {
        if(driver ==null)
        {
            //get parameter values
            String browser=getPropertiesData("browser");
            String path=getPropertiesData("path");
            String url=getPropertiesData("url");

            if(browser.equalsIgnoreCase("chrome"))
            {
                System.setProperty("webdriver.chrome.driver",path+ "chromedriver.exe");
                driver=new ChromeDriver();
                driver.get(url);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            }
        }
    }
    //create a method to read the config
    public String getPropertiesData(String propertyName)
    {
        Properties prop =new Properties();
        InputStream f=null;

        try{
            f=new FileInputStream("C:\\Users\\PMonaheng\\IdeaProjects\\Exam\\config.properties");
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            prop.load(f);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return prop.getProperty(propertyName);
    }
    //create a method to wait for element
    public void waitForElement(int elementWait, By locator)
    {
        WebDriverWait wait =new WebDriverWait(driver,elementWait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    //create a method to wait for the URL
    public void waitForUrl(int elementWait,String locator)
    {
        WebDriverWait wait =new WebDriverWait(driver,elementWait);
        wait.until(ExpectedConditions.urlContains(locator));
    }
    //create the method to wait for click
    public void waitForClick(int elementWait, By locator)
    {
        WebDriverWait wait =new WebDriverWait(driver,elementWait);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    //create a method to get element text
    public String getText(By locator)
    {
        waitForElement(10,locator);
        String errorMessage=getElement(locator).getText();
        return errorMessage;
    }
    //create a method to click element
    public void clickElement(By locator)
    {
        waitForClick(10,locator);
        getElement(locator).click();
    }
    //create a method to get element
    public WebElement getElement(By locator)
    {
        waitForClick(10,locator);
        return driver.findElement(locator);
    }
    //clean up(close the browser)
    public void cleanUp()
    {
        driver.close();
    }
    //create a method to Enter Text
    public void enterText(By locator, String text)
    {
        waitForClick(10,locator);
        driver.findElement(locator).sendKeys(text);
    }
    //a method to clear a text field
    public void clearTextbox(By locator)
    {
        driver.findElement(locator).clear();
    }

    //create a method to select the dropdown
}
