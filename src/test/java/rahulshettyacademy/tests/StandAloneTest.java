package rahulshettyacademy.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;



import rahulshettyacademy.pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {

	    WebDriverManager.chromedriver().setup();
	    
	    //new code added
//added another new code
// added another third code

	    WebDriver driver = new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    driver.manage().window().maximize();
	    driver.get("https://rahulshettyacademy.com/client");

	    LandingPage landingPage = new LandingPage(driver);
	    driver.findElement(By.id("userEmail")).sendKeys("anshika@gmail.com");
	    driver.findElement(By.id("userPassword")).sendKeys("Iamking@000");
	    driver.findElement(By.id("login")).click();

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
	    List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

	    WebElement prod = products.stream().filter(product ->
	        product.findElement(By.cssSelector("b")).getText().equals("IPHONE 13 PRO")).findFirst().orElse(null);
	    prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
	    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
	    driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

	    List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
	    Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase("IPHONE 13 PRO"));
	    Assert.assertTrue(match);
	    driver.findElement(By.cssSelector(".totalRow button")).click();

	    Actions a = new Actions(driver);
	    a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();

	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	    driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
	    driver.findElement(By.cssSelector(".action__submit")).click();

	    String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
	    Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	    driver.close();
	    
	    // some changes 09-02-2025
	    //Updated Changes on 15-02-2025 0651
	    //Updated Changes on 15-02-2025 0700
	  //Updated Changes on 15-02-2025 0710
	}
}