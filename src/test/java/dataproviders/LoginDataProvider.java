package dataproviders;

import java.util.List;

import org.testng.annotations.DataProvider;

import models.ForgotPasswordData;
import models.LoginData;
import utilities.ExcelUtils;

public class LoginDataProvider {

    @DataProvider(name = "validLogin")
    public Object[][] validLogin() {
        List<LoginData> data = ExcelUtils.read("LoginData.xlsx", "ValidLogin", LoginData.class);
        return ExcelUtils.toDataProviderArray(data);
    }

    @DataProvider(name = "invalidLogin")
    public Object[][] invalidLogin() {
        List<LoginData> data = ExcelUtils.read("LoginData.xlsx", "InvalidLogin", LoginData.class);
        return ExcelUtils.toDataProviderArray(data);
    }

    @DataProvider(name = "forgotPassword")
    public Object[][] forgotPassword() {
        List<ForgotPasswordData> data = ExcelUtils.read("LoginData.xlsx", "ForgotPassword", ForgotPasswordData.class);
        return ExcelUtils.toDataProviderArray(data);
    }
}
