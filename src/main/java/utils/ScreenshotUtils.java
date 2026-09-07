package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ScreenshotUtils {
    public static String getScreenshot(WebDriver driver, String testCaseName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = Paths.get(System.getProperty("user.dir"), "reports", "screenshots" , testCaseName + ".png").toString();

        FileUtils.copyFile(source, new File(destination));

        System.out.println(
                "SETUP | Thread: "
                        + Thread.currentThread().getId()
                        + " | Driver: "
                        + System.identityHashCode(driver)
        );

        return Paths.get(
                "screenshots",
                testCaseName + ".png"
        ).toString();

    }
}
