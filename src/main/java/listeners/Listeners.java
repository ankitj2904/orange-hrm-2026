package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import resources.Base;
import utilities.ExtentReportManager;

public class Listeners extends Base implements ITestListener {

    ExtentReports extentReport = ExtentReportManager.getExtentReport();
    ExtentTest extentTest;
    ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<ExtentTest>();

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getName();
        extentTest = extentReport.createTest(testName);
        extentTestThread.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getName();
        extentTestThread.get().log(Status.PASS, testName + " got passed");

        try {
            WebDriver driver = (WebDriver) result.getAttribute("driver");
            if (driver != null) {
                // Capture screenshot and get the relative path (inside reports/screenshots)
                String screenshotFilePath = takeScreenshot(testName, driver);

                // Add screenshot to Extent Report (relative path works because it's inside reports)
                extentTestThread.get().addScreenCaptureFromPath(screenshotFilePath, testName + " - passed");
            } else {
                extentTestThread.get().log(Status.WARNING, "WebDriver instance is null. Screenshot could not be captured.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        extentTestThread.get().log(Status.FAIL, testName + " got Failed");

        try {
            WebDriver driver = (WebDriver) result.getAttribute("driver");
            if (driver != null) {
                // Capture screenshot and get the relative path (inside reports/screenshots)
                String screenshotFilePath = takeScreenshot(testName, driver);

                // Add screenshot to Extent Report (relative path works because it's inside reports)
                extentTestThread.get().addScreenCaptureFromPath(screenshotFilePath, testName + " - failed");
            } else {
                extentTestThread.get().log(Status.WARNING, "WebDriver instance is null. Screenshot could not be captured.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // You can also add screenshot for skipped tests if needed
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // You can handle this if needed
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        // Handle this case if needed
    }

    @Override
    public void onStart(ITestContext context) {
        // You can initialize some resources before the tests start if needed
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush(); // This will ensure the report is written to disk after all tests have run
        extentTestThread.remove();
    }
}
