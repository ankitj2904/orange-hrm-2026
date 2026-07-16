package resources;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ConfigReader;

public class Base {

	WebDriver driver;

	public WebDriver initializeDriver() throws IOException {

		String browserName = ConfigReader.get("browser");

		if(browserName.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		if(browserName.equalsIgnoreCase("firefox")){

            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        if(browserName.equalsIgnoreCase("edge")){

            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));


		return driver;
	}

	public String takeScreenshot(String testName, WebDriver driver) throws IOException {
		waitForPageLoad(driver);

		// Get the path to the reports directory where extentReport is saved
		String reportDirPath = System.getProperty("user.dir") + "\\reports";

		// Create the screenshots directory inside the reports folder if it doesn't exist
		String screenshotsDirPath = reportDirPath + "\\screenshots";
		File screenshotsDir = new File(screenshotsDirPath);
		if (!screenshotsDir.exists()) {
			screenshotsDir.mkdirs();  // Create the directory if it doesn't exist
		}

		// Define the screenshot file path (inside the reports/screenshots folder)
		String screenshotFileName = testName + ".png";
		String destinationFilePath = screenshotsDirPath + "\\" + screenshotFileName;

		// Capture screenshot and save it to the directory
		File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sourceFile, new File(destinationFilePath));

		// Return the relative path to the screenshot, within the reports directory
		return "screenshots/" + screenshotFileName;
	}
	public void waitForPageLoad(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		while (true) {
			String readyState = jsExecutor.executeScript("return document.readyState").toString();
			if (readyState.equals("complete")) {
				break;
			}
		}
	}
}
