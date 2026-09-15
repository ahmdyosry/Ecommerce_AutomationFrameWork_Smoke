package retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    int retryCount = 0;
    int maxTry = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount < maxTry) {
            retryCount++;
            System.out.println("retried");
            iTestResult.setAttribute("retrying", true);
            iTestResult.setAttribute("retryCount", retryCount);
            return true;
        }
        iTestResult.setAttribute("retrying", false);
        return false;
    }
}
