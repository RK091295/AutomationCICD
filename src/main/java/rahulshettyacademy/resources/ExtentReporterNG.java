package rahulshettyacademy.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReporterNG {

    private static String reportFolder; // Store folder path for reports and screenshots
    private static ExtentReports extent; // Store ExtentReports instance

    public static ExtentReports getReportObject() {
        if (extent != null) {
            return extent; // Prevent duplicate report initialization
        }

        // ✅ Generate timestamp for unique folder name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        reportFolder = System.getProperty("user.dir") + "/reports/" + timestamp;
        String reportPath = reportFolder + "/index.html";

        // ✅ Create the main reports folder if it doesn't exist
        File mainReportsFolder = new File(System.getProperty("user.dir") + "/reports");
        if (!mainReportsFolder.exists()) {
            mainReportsFolder.mkdirs();
            System.out.println("📁 Created main reports folder.");
        }

        // ✅ Create the timestamped folder for this test run
        File folder = new File(reportFolder);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("📁 Created report folder: " + reportFolder);
        }

        System.out.println("📄 Creating new Extent Report at: " + reportPath);
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("Web Automation Results - " + timestamp);
        reporter.config().setDocumentTitle("Test Execution Report - " + timestamp);

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Rohit Kulkarni Shetty");

        System.out.println("✅ Extent Report Object Created Successfully.");
        return extent;
    }

    // ✅ Getter for the timestamped report folder
    public static String getReportFolder() {
        return reportFolder;
    }

    // ✅ Ensure reports are saved before JVM shutdown
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (extent != null) {
                extent.flush();
                System.out.println("✅ Extent Report Flushed Successfully at Shutdown.");
            }
        }));
    }
}
