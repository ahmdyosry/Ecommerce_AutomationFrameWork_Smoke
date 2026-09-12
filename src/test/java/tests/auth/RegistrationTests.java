package tests.auth;

import base.BaseTest;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegistrationPage;
import pages.SuccessRegisterPage;

import java.util.HashMap;
import java.util.Optional;

public class RegistrationTests extends BaseTest {

    @Test(groups = "smoke", dataProvider = "registerFormData", dataProviderClass = TestDataProvider.class)
    public void successfulRegistrationTest(HashMap<String, String> input) {
        SuccessRegisterPage success = new RegistrationPage(getDriver()).open()
                .initializeSignUp(input.get("name"), input.get("email"))
                .fillRegistrationForm(
                        input.get("name"),
                        input.get("password"),
                        input.get("firstName"),
                        input.get("lastName"),
                        Optional.ofNullable(input.get("company")),
                        input.get("address1"),
                        Optional.ofNullable(input.get("address2")),
                        input.get("state"),
                        input.get("city"),
                        input.get("zipcode"),
                        input.get("mobileNumber")
                );

        Assert.assertEquals(success.getSuccessMessage(), input.get("successMessage"));
    }
}
