package rahulshettyacademy.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest{
	String productName = "ADIDAS ORIGINAL";

	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{

		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		

	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest()
	{
		//"ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("sherlockholmes@gmail.com", "Sherl@ckHo1mes");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
		
}
	

	
	//Extent Reports - 
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{

		
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//rahulshettyacademy//data//PurchaseOrder.json");
		return new Object[][]  {{data.get(0)}, {data.get(1) } };
		
	}
	
	
	
	
//	 @DataProvider
//	  public Object[][] getData()
//	  {
//	    return new Object[][]  {{"anshika@gmail.com","Iamking@000","ZARA COAT 3"}, {"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL" } };
//	    
//	  }
//	HashMap<String,String> map = new HashMap<String,String>();
//	map.put("email", "anshika@gmail.com");
//	map.put("password", "Iamking@000");
//	map.put("product", "ZARA COAT 3");
//	
//	HashMap<String,String> map1 = new HashMap<String,String>();
//	map1.put("email", "shetty@gmail.com");
//	map1.put("password", "Iamking@000");
//	map1.put("product", "ADIDAS ORIGINAL");
	
	//Updated Changes on 15-02-2025 0707
	//Updated Changes on 15-02-2025 0720
	//Updated Changes on 15-02-2025 0728
	//Updated Changes on 16	-02-2025 1655
	//Updated Changes on 16	-02-2025 1734
	//Updated Changes on 16	-02-2025 1740
	//Updated Changes on 16	-02-2025 1753
	//Updated Changes on 16	-02-2025 1759
	//Updated Changes on 16	-02-2025 1803
	//Updated Changes on 16	-02-2025 1809
	//Updated Changes on 16	-02-2025 1813
	//Updated Changes on 16	-02-2025 1820
	//Updated Changes on 16	-02-2025 1848
	//Updated Changes on 16	-02-2025 2007
	//Updated Changes on 16	-02-2025 2010
	//Updated Changes on 16	-02-2025 2030
	//Updated Changes on 17-02-2025 1150
	//Updated Changes on 17-02-2025 1150
	//Updated Changes on 17-02-2025 1157
	//Updated Changes on 17-02-2025 1358	
	//Updated Changes on 17-02-2025 1405
	//Updated Changes on 17-02-2025 1409
	//Updated Changes on 17-02-2025 1410
	//Updated Changes on 17-02-2025 1413
	//Updated Changes on 17-02-2025 1440
	//Updated Changes on 17-02-2025 1445
	//Updated Changes on 17-02-2025 1456
	//Updated Changes on 17-02-2025 1515
	//Updated Changes on 17-02-2025 1520
	//Updated Changes on 17-02-2025 1530
	//Updated Changes on 17-02-2025 1540
	//Updated Changes on 17-02-2025 1541
	//Updated Changes on 17-02-2025 1600
	//Updated Changes on 17-02-2025 1600
	//Updated Changes on 17-02-2025 1604
	//Updated Changes on 17-02-2025 1615
	//Updated Changes on 17-02-2025 1614
	//Updated Changes on 17-02-2025 1627
	//Updated Changes on 17-02-2025 1629
	//Updated Changes on 17-02-2025 1745
	//Updated Changes on 17-02-2025 1817
	//1
	//2
	//3
	//4
	//5
	//6
	//7
	//8
	//9
	//10
	//11
	//12
	//13
	//14
	//15
	
	
	
}
