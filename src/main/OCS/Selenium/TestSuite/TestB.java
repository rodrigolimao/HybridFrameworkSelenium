package main.OCS.Selenium.TestSuite;

import main.OCS.Selenium.helper.assertion.AssertionHelper;
import main.OCS.Selenium.testBase.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestB extends TestBase{
        @Test
        public void testLogin(){
            new AssertionHelper().markPass();
        }
        @Test
        public void testLogin1(){
            new AssertionHelper().markFail();
        }
        @Test
        public void testLogin2(){
            new AssertionHelper().markPass();
        }
        @Test
        public void testLogin3(){
            new AssertionHelper().markFail();
        }
}
