package test_cases;

import bases.BaseClass;
import bases.ReadExcel;
import org.testng.annotations.*;
import pageObject.LandingPage;
import pageObject.SearchingForBrands;

public class MakingOrder extends BaseClass{
    SearchingForBrands brands=new SearchingForBrands();
    LandingPage page=new LandingPage();
    @BeforeClass
    public void openWebApplication()
    {
        page.landingPage();
        page.feedBackPopUp();
    }
    @Test(dataProvider="brands",dataProviderClass = ReadExcel.class)
    public void TC1_GIVEN_EnterItemOnTheSearchField_WHEN_ClickSearchButton_THEN_ValidateThatTheResultsAreAboveZero(String names, String quantities) {
        //Given-EnterItemOnTheSearchField
        brands.searchForBrand(names);

        //WHEN-ClickSearchButton
        brands.clickSearchButton();
        brands.printQuantity(quantities);
        //THEN-ValidateThatTheResultsAreAboveZero
        brands.numberOfDeals();
        brands.clearField();
    }

    @Test(dataProvider="brands",dataProviderClass = ReadExcel.class)
    public void TC2_GIVEN_OnTheSearchFieldEnterItem_AND_ClickSearchButton_WHEN_ValidateThatTheResultsAreAboveZero_AND_SelectFirstSearchedItem_THEN_SwitchToNextTab_AND_GetInitialPriceOfItem(String names, String quantities){
        //GIVEN-OnTheSearchFieldEnterItem
        brands.searchForBrand(names);

        //AND-ClickSearchButton
        brands.clickSearchButton();
        brands.printQuantity(quantities);
        //WHEN-ValidateThatTheResultsAreAboveZero
        brands.numberOfDeals();

        //AND-SelectFirstSearchedItem
        brands.itemSelect();

        //THEN-SwitchToNextTab
        brands.switchTabs();

        //AND-GetInitialPriceOfItem
        brands.initialPrice();
        brands.switchToParentWindow();
        brands.clearField();
    }
    @Test(dataProvider="brands",dataProviderClass = ReadExcel.class)
    public void TC3_GIVEN_EnterItem_AND_ClickSearchButton_AND_ValidateThatTheResultsAreAboveZero_AND_SelectFirstSearchedItem_WHEN_SwitchToNextTab_THEN_ValidateTheAddedToTheCartIsDisplayed(String names, String quantities) throws InterruptedException {
        //GIVEN-EnterItem
        brands.searchForBrand(names);

        //AND-ClickSearchButton
        brands.clickSearchButton();
        brands.printQuantity(quantities);
        //AND-ValidateThatTheResultsAreAboveZero
        brands.numberOfDeals();

        //AND-SelectFirstSearchedItem
        brands.itemSelect();

        //WHEN-SwitchToNextTab
        brands.switchTabs();
        brands.initialPrice();

        //THEN-ValidateTheAddedToTheCartIsDisplayed
        brands.addToCart();
        brands.addedToCartPopUp();
        brands.clickGoToCart();
        Thread.sleep(5000);
        brands.removeItemFromCart();
        Thread.sleep(5000);
        brands.switchToParentWindow();
        brands.clearField();
    }
    @Test(dataProvider="brands",dataProviderClass = ReadExcel.class)
    public void TC4_GIVEN_SearchItem_AND_ClickSearchButton_AND_ValidateThatTheResultsAreAboveZero_AND_SelectFirstSearchedItem_WHEN_SwitchToNextTab_AND_ValidateTheAddedToTheCartIsDisplayed_THEN_SelectQuantity_AND_ValidateCartSummary(String names, String quantities) throws InterruptedException {

        //GIVEN-SearchItem
        brands.searchForBrand(names);

        //AND-ClickSearchButton
        brands.clickSearchButton();
        brands.printQuantity(quantities);
        //AND-ValidateThatTheResultsAreAboveZero
        brands.numberOfDeals();

        //AND-SelectFirstSearchedItem
        brands.itemSelect();

        //WHEN-SwitchToNextTab
        brands.switchTabs();
        brands.initialPrice();

        //AND-ValidateTheAddedToTheCartIsDisplayed
        brands.addToCart();
        brands.addedToCartPopUp();
        brands.clickGoToCart();

        //THEN-SelectQuantity
        Thread.sleep(5000);
        brands.selectQuantity(names,quantities);

        //AND-ValidateCartSummary
        Thread.sleep(5000);
        brands.calculateTotalPrice(names,quantities);
        brands.switchToParentWindow();
        brands.clearField();
    }
    @AfterClass
    public void closeApplication()
    {
        cleanUp();
    }
}
