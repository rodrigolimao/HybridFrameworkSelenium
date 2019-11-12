package main.OCS.Selenium.TestSuite;

import main.OCS.Selenium.testBase.TestBase;
import org.testng.annotations.Test;

public class testScreenShot extends TestBase {

    @Test
    public void testScreen(){
    driver.get("https://www.ocs.ca");
    captureScreen("firstScreen", driver);

    }
}
