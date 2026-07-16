package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private static final DataFormatter FORMATTER = new DataFormatter();

    private ExcelUtils() {
    }

    public static String getTestDataPath(String fileName) {
        return System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\excel\\" + fileName;
    }

    public static <T> List<T> read(String fileName, String sheetName, Class<T> clazz) {
        String filePath = getTestDataPath(fileName);
        List<T> records = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in " + fileName);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return records;
            }

            Map<Integer, String> columnIndexToField = new HashMap<>();
            for (Cell cell : headerRow) {
                columnIndexToField.put(cell.getColumnIndex(), toFieldName(getCellValue(cell)));
            }

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null || isRowEmpty(row)) {
                    continue;
                }

                T instance = clazz.getDeclaredConstructor().newInstance();
                for (Map.Entry<Integer, String> entry : columnIndexToField.entrySet()) {
                    String value = getCellValue(row.getCell(entry.getKey()));
                    setFieldValue(instance, entry.getValue(), value);
                }
                records.add(instance);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }

        return records;
    }

    public static Object[][] toDataProviderArray(List<?> records) {
        Object[][] data = new Object[records.size()][1];
        for (int i = 0; i < records.size(); i++) {
            data[i][0] = records.get(i);
        }
        return data;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return FORMATTER.formatCellValue(cell).trim();
    }

    private static boolean isRowEmpty(Row row) {
        for (Cell cell : row) {
            if (!getCellValue(cell).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static String toFieldName(String header) {
        String normalized = header.replaceAll("[^a-zA-Z0-9]", "");
        if (normalized.isEmpty()) {
            return normalized;
        }
        return Character.toLowerCase(normalized.charAt(0)) + normalized.substring(1);
    }

    private static void setFieldValue(Object instance, String fieldName, String value) throws Exception {
        String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method setter = null;
        for (Method method : instance.getClass().getMethods()) {
            if (method.getName().equalsIgnoreCase(setterName) && method.getParameterCount() == 1) {
                setter = method;
                break;
            }
        }
        if (setter != null) {
            setter.invoke(instance, value);
        }
    }
}
