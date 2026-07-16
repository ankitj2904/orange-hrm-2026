package Tests;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ObjectRepository.AdminPage;
import ObjectRepository.BuzzPage;
import ObjectRepository.DashboardPage;
import ObjectRepository.ForgotPasswordPage;
import ObjectRepository.LeavePage;
import ObjectRepository.LoginPage;
import ObjectRepository.MyInfoPage;
import ObjectRepository.PerformancePage;
import ObjectRepository.RecruitmentPage;
import dataproviders.AdminDataProvider;
import dataproviders.LoginDataProvider;
import models.AdminUserData;
import models.ForgotPasswordData;
import models.LoginData;
import resources.Base;
import utilities.ConfigReader;
import utilities.FakerUtils;

public class AllTests extends Base {
	
	WebDriver driver;
    WebDriverWait wait;
	
	@BeforeMethod
	public void setup(ITestResult result) throws IOException {
		
		driver = initializeDriver();
		result.setAttribute("driver", driver);
        driver.get(ConfigReader.get("url"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	}
	
	@Test(dataProvider = "validLogin", dataProviderClass = LoginDataProvider.class)
	public void OHRM001Test(LoginData data) {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(data.getUsername(), data.getPassword());
		
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.dashboard().isDisplayed();
		
	}
	
	@Test(dataProvider = "invalidLogin", dataProviderClass = LoginDataProvider.class)
	public void OHRM002Test(LoginData data) {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(data.getUsername(), data.getPassword());
		loginPage.errormessage().isDisplayed();		
		
	}
	
	@Test(dataProvider = "forgotPassword", dataProviderClass = LoginDataProvider.class)
	public void OHRM003Test(ForgotPasswordData data) {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.forgotPasswordLink().click();
		
		ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
		forgotPasswordPage.username().sendKeys(data.getUsername());
		forgotPasswordPage.resetPasswordButton().click();	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement validationMessage = forgotPasswordPage.validationMessage();
		    
		wait.until(ExpectedConditions.visibilityOf(validationMessage));		  		

	}
	
	@Test(dataProvider = "addUser", dataProviderClass = AdminDataProvider.class)
	public void OHRM004Test(AdminUserData data) {

		LoginPage loginPage = new LoginPage(driver);
	    loginPage.login();

	    AdminPage adminPage = new AdminPage(driver);
	    adminPage.adminMenu().click();
	    adminPage.addButton().click();

	    adminPage.selectCustomDropdown(adminPage.selectUserRole(), data.getRole());
	    adminPage.selectCustomDropdown(adminPage.selectStatus(), data.getStatus());

	    adminPage.employeeNameField().sendKeys(data.getEmployeeName());
	    adminPage.usernameField().sendKeys(FakerUtils.randomUsername());
	    adminPage.passwordField().sendKeys(data.getPassword());
	    adminPage.confirmPasswordField().sendKeys(data.getPassword());
	    adminPage.saveButton().click();
		
	}
	
	@Test
	public void OHRM005Test() {

		LoginPage loginPage = new LoginPage(driver);
	    loginPage.login();

	    LeavePage leavePage = new LeavePage(driver);
	    leavePage.leaveMenu().click();
	    leavePage.applyButton().click();
	    leavePage.selectLeaveType().click();
	    leavePage.familyType().sendKeys(Keys.DOWN);
	    leavePage.familyType().sendKeys(Keys.ENTER);
	    leavePage.formDate().sendKeys("2026-02-02");
	    leavePage.formDate().sendKeys(Keys.TAB);
	    leavePage.submitButton().click();
	   
	}
	
	@Test
	public void OHRM006Test() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login();
	
		MyInfoPage myInfoPage = new MyInfoPage(driver);
		myInfoPage.myInfoMenu().click();
		myInfoPage.contactDetails().click();
		myInfoPage.workEmail().sendKeys(FakerUtils.randomEmail());
		myInfoPage.workEmail().sendKeys(Keys.TAB);
		myInfoPage.workEmail().sendKeys(Keys.ENTER);
	}
	
	@Test
	public void OHRM007Test() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login();
		
		PerformancePage performancePage = new PerformancePage(driver);
		performancePage.performanceMenu().click();
		performancePage.performanceText().isDisplayed();
		
	}
	
	@Test
	public void OHRM008Test() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login();
		
		RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
		recruitmentPage.recruitmentMenu().click();
		recruitmentPage.vacancies().click();
		WebElement vacanciesTitle = recruitmentPage.vacanciesTitle();
		Assert.assertEquals(vacanciesTitle.getText(), "Vacancies");
	}
	
	@Test
	public void OHRM009Test() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login();
		
		BuzzPage buzzPage = new BuzzPage(driver);
		buzzPage.buzzMenu().click();
		buzzPage.postArea().sendKeys("Hello all, I am Ram new QA Automation Engineer");
		buzzPage.postButton().click();
		driver.navigate().refresh();
		WebElement buzzNewsfeedText = buzzPage.buzzNewsfeedText();
		Assert.assertEquals(buzzNewsfeedText.getText(),"Buzz Newsfeed");


	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void OHRM010Test() throws IOException {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login();
		
		BuzzPage buzzPage = new BuzzPage(driver);
		buzzPage.buzzMenu().click();
		wait.until(ExpectedConditions.visibilityOf(buzzPage.selectPhotoButton()));
		buzzPage.selectPhotoButton().click();
		buzzPage.addText().sendKeys("Hello all, I am sharing this image");
		buzzPage.addPhotos().click();
		buzzPage.executeAutoItScript();
		buzzPage.shareButton.click();
		driver.navigate().refresh();
		WebElement buzzNewsfeedText = buzzPage.buzzNewsfeedText();
		Assert.assertEquals(buzzNewsfeedText.getText(),"Buzz Newsfeed");
		
		
	}

	@Test
	public void OHRM011Test() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login();
		
		BuzzPage buzzPage = new BuzzPage(driver);
		buzzPage.buzzMenu().click();
		buzzPage.sharevideoButton().click();
		buzzPage.pastVideoUrl().sendKeys("https://youtu.be/kBESOqobduE?si=Yvycd0W9LWq2-DN2");
		buzzPage.shareVideoButton.click();
		
	}
	
	@Test
	public void OHRM012Test() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login();
		loginPage.userDropdown().click();
		loginPage.logout().click();
		loginPage.homePageLogo().isDisplayed();
	}
		

	 @AfterMethod
	    public void tearDown() {

		 if (driver != null) {
			 driver.quit();
		 }
	    }
		
}
