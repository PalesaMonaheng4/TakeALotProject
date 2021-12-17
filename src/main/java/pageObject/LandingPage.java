package pageObject;

import bases.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.SkipException;

public class LandingPage extends BaseClass {
    public void landingPage() {
        Reporter.log(driver.getTitle());
        try {
            clickElement(By.xpath("//button[@class='button cookies-banner-module_dismiss-button_24Z98']"));
        }
        catch(Exception e)
        {
            Reporter.log("No such element is displayed");
        }
    }
    public void feedBackPopUp()
    {
        try {
            if (driver.findElement(By.xpath("//body[@id=\"body\"]/div[4]/div/div/div/div/form")).isDisplayed() && driver.findElement(By.xpath("//body[@id=\"body\"]/div[4]/div/div/div/div/form")).isEnabled()) {
                clickElement(By.xpath("//body[@id=\"body\"]/div[4]/div/div/div/div/form/div[1]/div[2]/div/label[2]/span[2]"));
                clickElement(By.xpath("//body[@id=\"body\"]/div[4]/div/div/div/div/form/div[2]/div/button"));
                clickElement(By.xpath("//body[@id=\"body\"]/div[4]/div/div/div/div/form/div[1]/div/button"));
            }
        }
        catch (Exception e) {
           Reporter.log("Element not displayed");
        }
    }

}
