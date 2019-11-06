package main.OCS.Selenium.helper.window;

import main.OCS.Selenium.helper.logger.LoggerHelper;

import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class WindowHelper {

    private WebDriver driver;
    private Logger log = LoggerHelper.getLogger(WindowHelper.class);

    public WindowHelper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * This method will switch to parent window
     */
    public void switchToParentWindow() {
        log.info("switching to parent window...");
        driver.switchTo().defaultContent();
    }

    /**
     * This method will switch to child window based on index
     *
     * @param index
     */
    public void switchToWindow(int index) {
        Set<String> windows = driver.getWindowHandles();
        int i = 1;
        for (String window : windows) {
            if (i == index) {
                log.info("Switched to : " + index + " window");
                driver.switchTo().window(window);
            } else {
                i++;
            }
        }
    }

    /**
     * This method will close all tabbed window and
     * switched to main window
     */
    public void closeAllTabsAndSwitchToMainWindow() {
        Set<String> windows = driver.getWindowHandles();
        String mainWindow = driver.getWindowHandle();

        for (String window : windows) {
            if (!window.equalsIgnoreCase(mainWindow)) {
                driver.close();
            }
        }
        log.info("Switched to main window");
        driver.switchTo().window(mainWindow);
    }

    /**
     * This method will do browser back navigation
     */
    public void navigateBack() {
        log.info("Navigating back");
        driver.navigate().back();
    }

    /**
     * This method will do browser forward navigation
     */
    public void navigateForward() {
        log.info("Navigating forward");
        driver.navigate().forward();
    }

}
