package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestDataSeeder {

    public static void main(String[] args) throws IOException {
        seedLoginData();
        seedAdminData();
        System.out.println("Test data Excel files created successfully.");
    }

    public static void seedLoginData() throws IOException {
        String filePath = ExcelUtils.getTestDataPath("LoginData.xlsx");
        createParentDirs(filePath);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet validLogin = workbook.createSheet("ValidLogin");
            createHeader(validLogin, "TestCaseID", "Username", "Password", "ExpectedResult");
            createRow(validLogin, 1, "OHRM001", "Admin", "admin123", "success");

            Sheet invalidLogin = workbook.createSheet("InvalidLogin");
            createHeader(invalidLogin, "TestCaseID", "Username", "Password", "ExpectedResult");
            createRow(invalidLogin, 1, "OHRM002", "Admin", "james789", "error");

            Sheet forgotPassword = workbook.createSheet("ForgotPassword");
            createHeader(forgotPassword, "TestCaseID", "Username");
            createRow(forgotPassword, 1, "OHRM003", "Admin");

            writeWorkbook(workbook, filePath);
        }
    }

    public static void seedAdminData() throws IOException {
        String filePath = ExcelUtils.getTestDataPath("AdminData.xlsx");
        createParentDirs(filePath);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet addUser = workbook.createSheet("AddUser");
            createHeader(addUser, "TestCaseID", "EmployeeName", "Role", "Status", "Password");
            createRow(addUser, 1, "OHRM004", "James Butler", "Admin", "Enabled", "admin123");

            writeWorkbook(workbook, filePath);
        }
    }

    private static void createParentDirs(String filePath) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    private static void createHeader(Sheet sheet, String... headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
    }

    private static void createRow(Sheet sheet, int rowIndex, String... values) {
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < values.length; i++) {
            row.createCell(i).setCellValue(values[i]);
        }
    }

    private static void writeWorkbook(Workbook workbook, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
    }
}
