package rahulshettyacademy.TestComponents;

import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.resources.ExtentReporterNG;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException

	{
		// properties class

		 Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "//src//main//java//rahulshettyacademy//resources//GlobalData.properties");
		prop.load(fis);
		
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");
		//prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless")){
			options.addArguments("headless");
			}		
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440,900));//full screen

		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().setSize(new Dimension(1440,900));
			// Firefox
		} else if (browserName.equalsIgnoreCase("edge")) {
			// Edge
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().setSize(new Dimension(1440,900));
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;

	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//read json to string
	String jsonContent = 	FileUtils.readFileToString(new File(filePath), 
			StandardCharsets.UTF_8);
	
	//String to HashMap- Jackson Databind
	
	ObjectMapper mapper = new ObjectMapper();
	  List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
      });
	  return data;
	
	//{map, map}

	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
	    String reportFolder = ExtentReporterNG.getReportFolder(); // Get the same report folder
	    String screenshotPath = Paths.get(reportFolder, testCaseName + ".png").toString(); // Store screenshots in the same folder

	    File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    File destination = new File(screenshotPath);
	    FileUtils.copyFile(source, destination);

	    // Ensure relative path is correct for HTML reports
	    String relativePath = new File(System.getProperty("user.dir")).toURI()
	                            .relativize(new File(screenshotPath).toURI()).getPath();

	    System.out.println("Screenshot saved at: " + screenshotPath);
	    System.out.println("Relative path for report: " + relativePath);

	    return relativePath;
	}

	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		
		 driver = initializeDriver();
		  landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	
		
	}
	
	@AfterMethod(alwaysRun=true)
	
	public void tearDown()
	{
		driver.close();
	}
}
