package dataproviders;

import java.util.List;

import org.testng.annotations.DataProvider;

import models.AdminUserData;
import utilities.ExcelUtils;

public class AdminDataProvider {

    @DataProvider(name = "addUser")
    public Object[][] addUser() {
        List<AdminUserData> data = ExcelUtils.read("AdminData.xlsx", "AddUser", AdminUserData.class);
        return ExcelUtils.toDataProviderArray(data);
    }
}
