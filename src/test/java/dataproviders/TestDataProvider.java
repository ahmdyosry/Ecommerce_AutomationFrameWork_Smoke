package dataproviders;

import org.testng.annotations.DataProvider;

import java.util.HashMap;
import java.io.IOException;
import java.util.List;

import utils.JsonDataReader;

public class TestDataProvider {

    @DataProvider(name = "invalidLogins", parallel = true)
    public static Object[][] invalidLogins() throws IOException {

        List<HashMap<String, Object>> data =
                JsonDataReader.getJsonData(
                        "src/test/resources/testdata/invalidLogins.json"
                );

        return data.stream()
                .map(item -> new Object[]{item})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "registerFormData", parallel = true)
    public static Object[][] registerFormData() throws IOException {

        List<HashMap<String, Object>> data =
                JsonDataReader.getJsonData(
                        "src/test/resources/testdata/validRegistration.json"
                );

        return data.stream()
                .map(item -> new Object[]{item})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "productNames", parallel = true)
    public static Object[][] productNames() throws IOException {

        List<HashMap<String, Object>> data =
                JsonDataReader.getJsonData(
                        "src/test/resources/testdata/products.json"
                );

        return data.stream()
                .map(item -> new Object[]{item})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "checkoutData", parallel = true)
    public static Object[][] checkoutData() throws IOException {

        List<HashMap<String, Object>> data =
                JsonDataReader.getJsonData(
                        "src/test/resources/testdata/checkout.json"
                );

        return data.stream()
                .map(item -> new Object[]{item})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "paymentData", parallel = true)
    public static Object[][] paymentData() throws IOException {

        List<HashMap<String, Object>> data =
                JsonDataReader.getJsonData(
                        "src/test/resources/testdata/paymentDetails.json"
                );

        return data.stream()
                .map(item -> new Object[]{item})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "searchData", parallel = true)
    public static Object[][] searchData() throws IOException {

        List<HashMap<String, Object>> data =
                JsonDataReader.getJsonData(
                        "src/test/resources/testdata/searchData.json"
                );

        return data.stream()
                .map(item -> new Object[]{item})
                .toArray(Object[][]::new);
    }
}
