package main.OCS.Selenium.helper.browserConfiguration.config;

import main.OCS.Selenium.helper.browserConfiguration.BrowserType;
import main.OCS.Selenium.helper.resource.ResourceHelper;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader implements ConfigReader {
    private static FileInputStream file;
    public static Properties OR;

    public PropertyReader() {
        try {
            String pathname = ResourceHelper.getResourcePath("src/main/resources/config/config.properties");
            file = new FileInputStream(new File(pathname));
            OR = new Properties();
            OR.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getImplicitWait() {
        return Integer.parseInt(OR.getProperty("implicitWait"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(OR.getProperty("explicitWait"));
    }

    public int getPageLoadTime() {
        return Integer.parseInt(OR.getProperty("pageLoadTime"));
    }

    public BrowserType getBrowserType() {
        return BrowserType.valueOf(OR.getProperty("browserType"));
    }

    public String getUrl() {
        if (System.getProperty("url") != null) {
            return System.getProperty("url");
        }
        return OR.getProperty("applicationUrl");
    }

    public String getUserName() {
        if (System.getProperty("userName") != null) {
            return System.getProperty("userName");
        }
        return OR.getProperty("userName");
    }

    public String getPassword() {
        if (System.getProperty("password") != null) {
            return System.getProperty("password");
        }
        return OR.getProperty("password");
    }
}
