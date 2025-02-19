package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import rahulshettyacademy.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject(); // Initialize Extent Reports
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>(); // Ensures thread safety for parallel execution

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName()); // Create a new test entry
        extentTest.set(test); // Store it in ThreadLocal to ensure correct logging in parallel execution
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed"); // Log test as passed
        extent.flush(); // ✅ Force writing data immediately
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable()); // Log failure message

        WebDriver driver = null;
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String screenshotPath = null;
        try {
            screenshotPath = getScreenshot(result.getMethod().getMethodName(), driver);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (screenshotPath != null) {
            extentTest.get().addScreenCaptureFromPath("./" + screenshotPath, result.getMethod().getMethodName());
        }
        extent.flush(); // ✅ Ensure the report updates immediately
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush(); // Ensures index.html is updated
            System.out.println("✅ Extent Report Flushed Successfully.");
        }
    }

    // ✅ Save screenshots inside the timestamped report folder
    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        String reportFolder = ExtentReporterNG.getReportFolder();

        String screenshotPath = reportFolder + "/" + testCaseName + ".png";
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File(screenshotPath);
        FileUtils.copyFile(source, destination);

        return new File(screenshotPath).getName(); // Only return the filename

    }

    @Override
    public void onTestSkipped(ITestResult result) {}

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    @Override
    public void onStart(ITestContext context) {}
}
