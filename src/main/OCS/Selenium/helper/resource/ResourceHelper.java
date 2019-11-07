package main.OCS.Selenium.helper.resource;

import main.OCS.Selenium.helper.logger.LoggerHelper;
import org.apache.log4j.Logger;

public class ResourceHelper {
    public static String getResourcePath(String path) {
        System.out.println(path);
        String basePath = System.getProperty("user.dir");
        return basePath + "/" + path;
    }
}
