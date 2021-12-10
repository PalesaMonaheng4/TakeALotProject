package test_cases;

import bases.BaseClass;
import bases.ReadExcel;
import io.restassured.internal.http.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObject.LandingPage;
import pageObject.SearchingForBrands;

public class MakingOrder extends BaseClass {
    SearchingForBrands total=new SearchingForBrands();
    LandingPage page=new LandingPage();


    @Test(priority = 1)
    public void openWebApplication()
    {
        page.landingPage();

    }
    @Test(priority = 2,dataProvider="brands",dataProviderClass = ReadExcel.class)
    public void search_Brand(String names, String quantities) throws InterruptedException {
        System.out.println("The brand name is "+names +" and the quantity is "+quantities);
        enterText(By.name("search"), names);
        clickElement(By.xpath("//button[@class='button search-btn search-icon']"));
        page.feedBackPopUp();
        total.numberOfDeals();
       total.itemSelect(names,quantities);
        clearTextbox(By.name("search"));
    }
 /*   @Test(dataProvider="setQuantity",dataProviderClass = ReadExcel.class)
    public void search_Quantity(String names, String quantities) throws InterruptedException {
        total.itemSelect(names,quantities);
    }*/

  /*  @AfterClass
    public void closeApplication()
    {
        cleanUp();
    }*/
}
