package main.OCS.Selenium.testBase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import main.OCS.Selenium.helper.browserConfiguration.BrowserType;
import main.OCS.Selenium.helper.browserConfiguration.ChromeBrowser;
import main.OCS.Selenium.helper.browserConfiguration.FirefoxBrowser;
import main.OCS.Selenium.helper.browserConfiguration.IExplorerBrowser;
import main.OCS.Selenium.helper.browserConfiguration.config.ObjectReader;
import main.OCS.Selenium.helper.browserConfiguration.config.PropertyReader;
import main.OCS.Selenium.helper.javaScript.JavaScriptHelper;
import main.OCS.Selenium.helper.logger.LoggerHelper;
import main.OCS.Selenium.helper.resource.ResourceHelper;
import main.OCS.Selenium.helper.wait.WaitHelper;
import main.OCS.Selenium.utils.ExtentManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class TestBase {
    public static ExtentReports extent;
    public static ExtentTest test;
    public WebDriver driver;
    private Logger log = LoggerHelper.getLogger(TestBase.class);
    public static File reportDirectory;

    @BeforeSuite
    public void beforeSuite() throws Exception {
        extent = ExtentManager.getInstance();
    }

    @BeforeTest
    public void beforeTest() throws Exception {
        ObjectReader.reader = new PropertyReader();
        reportDirectory = new File(ResourceHelper.getResourcePath("src/main/resources/screenShots"));
        setUpDriver(ObjectReader.reader.getBrowserType());
        test = extent.createTest(getClass().getSimpleName());
    }


    @BeforeMethod
    public void beforeMethod(Method method) {
        test.log(Status.INFO, method.getName() + "**************test started***************");
        log.info("**************" + method.getName() + "Started***************");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, result.getThrowable());
            String imagePath = captureScreen(result.getName(), driver);
            test.addScreenCaptureFromPath(imagePath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, result.getName() + " passed");
            //String imagePath = captureScreen(result.getName(),driver);
            //test.addScreenCaptureFromPath(imagePath);
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, result.getThrowable());
        }
        log.info("**************" + result.getName() + "Finished***************");
        extent.flush();
    }

    @AfterTest
    public void afterTest() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getBrowserObject(BrowserType btype) throws Exception {
        try {
            switch (btype) {
                case Chrome:
                    // get object of ChromeBrowser class
                    ChromeBrowser chrome = ChromeBrowser.class.getDeclaredConstructor().newInstance();
                    return chrome.getChromeDriver(chrome.getChromeOptions());
                case Firefox:
                    FirefoxBrowser firefox = FirefoxBrowser.class.getDeclaredConstructor().newInstance();
                    return firefox.getFirefoxDriver(firefox.getFirefoxOptions());
                case IExplorer:
                    IExplorerBrowser ie = IExplorerBrowser.class.getDeclaredConstructor().newInstance();
                    return ie.getIExplorerDriver(ie.getIExplorerCapabilities());
                default:
                    throw new Exception("Driver not Found: " + btype.name());
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    public void setUpDriver(BrowserType btype) throws Exception {
        driver = getBrowserObject(btype);
        log.info("Initialize Web driver: " + driver.hashCode());
        WaitHelper wait = new WaitHelper(driver);
        wait.setImplicitWait(ObjectReader.reader.getImplicitWait(), TimeUnit.SECONDS);
        wait.pageLoadTime(ObjectReader.reader.getPageLoadTime(), TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }


    public String captureScreen(String fileName, WebDriver driver) {
        if (this.driver == null) {
            log.info("driver is null..");
            return null;
        }
        if (fileName.equals("")) {
            fileName = "blank";
        }
        Reporter.log("CaptureScreen method called");
        File destFile = null;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        File screenShotFile = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
        try {
            destFile = new File(reportDirectory + "/" + fileName + "_" + formatter.format(calendar.getTime()) + ".png");
            Files.copy(screenShotFile.toPath(), destFile.toPath());
            Reporter.log("<a href='" + destFile.getAbsolutePath() + "'><img src='" + destFile.getAbsolutePath() + "'height='100' width='100'/></a>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destFile.toString();
    }

    public void getNavigationScreen(WebDriver driver) {
        log.info("Capturing UI navigation screen...");
        new JavaScriptHelper(driver).zoomInBy60Percentage();
        String screen = captureScreen("", driver);
        new JavaScriptHelper(driver).zoomInBy100Percentage();
        try {
            test.addScreenCaptureFromPath(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logExtentReport(String s1) {
        test.log(Status.INFO, s1);
    }

    public void getApplicationUrl(String url) {
        driver.get(url);
        logExtentReport("Navigating to ..." + url);
    }

//
//    public Object[][] getExcelData(String excelName, String sheetName) {
//        String excelLocation = ResourceHelper.getResourcePath("src/main/resources/configfile/") + excelName;
//        log.info("excel location " + excelLocation);
//        ExcelHelper excelHelper = new ExcelHelper();
//        Object[][] data = excelHelper.getExcelData(excelLocation, sheetName);
//        return data;
//    }
}

