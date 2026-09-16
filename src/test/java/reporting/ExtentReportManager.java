package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ExtentReportManager {

    private ExtentReportManager() {
    }

    private static class Holder {
        private static final ExtentReports INSTANCE = createReport();
    }

    public static ExtentReports getReportObject() {
        return Holder.INSTANCE;
    }

    private static ExtentReports createReport() {

        try {
            Path reportDirectory = Paths.get("reports");

            Files.createDirectories(reportDirectory);

            Path reportPath =
                    reportDirectory.resolve("index.html");

            ExtentSparkReporter reporter =
                    new ExtentSparkReporter(reportPath.toString());

            reporter.config()
                    .setReportName("Web Automation Results");

            reporter.config()
                    .setDocumentTitle("Test Results");

            ExtentReports extent = new ExtentReports();

            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Ahmed");

            return extent;

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to initialize Extent Report",
                    e
            );
        }
    }
}