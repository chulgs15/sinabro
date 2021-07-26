package org.example.anno;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.IntStream;

public class ExcelCreateTest {
    public static void main(String[] args) {
        System.out.println("hello world");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("first sheet");

        Object[][] dataTypes = {
                {"DataType", "Type", "Size(in bytes)"},
                {"int", "primitirve", 2},
                {"date", "test", LocalDateTime.now()}
        };

        int rowNum = 0;
        System.out.println("Creating Excel");

        for (Object[] dataType : dataTypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : dataType) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                } else if (field instanceof LocalDateTime) {
                    cell.setCellValue((LocalDateTime) field);
                    XSSFCellStyle style = workbook.createCellStyle();
                    XSSFDataFormat format = workbook.createDataFormat();
                    style.setDataFormat(format.getFormat("yyyy-mm-dd"));
                    cell.setCellStyle(style);
                }
            }
        }

        // 길이 조정.
        int length = dataTypes[0].length;
        IntStream.rangeClosed(0, length)
                .forEach(value -> sheet.autoSizeColumn(value));

        try {
            FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.home")
                    + "/Desktop/test.xlsx"
            );
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
