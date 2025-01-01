package com.RestAssuredPAVAN.utilities;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLUtility
{
        public FileInputStream fi;
        public FileOutputStream fo;
        public XSSFWorkbook workbook;
        public XSSFSheet sheet;
        public XSSFRow row;
        public XSSFCell cell;
        public CellStyle style;
        String path;

        // Constructor to initialize the file path
        public XLUtility(String path) {
            this.path = path;
        }

        // Method to get row count
        public int getRowCount(String sheetName) throws IOException
        {
            fi = new FileInputStream(path);
            workbook = new XSSFWorkbook(fi);
            sheet = workbook.getSheet(sheetName);
            int rowcount = sheet.getLastRowNum(); // Get the last row number
            workbook.close();
            fi.close();
            return rowcount;
        }

        // Method to get column count
        public int getColumnCount(String sheetName, int rowNum) throws IOException
        {
            fi = new FileInputStream(path);
            workbook = new XSSFWorkbook(fi);
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowNum);
            int colCount = row.getLastCellNum(); // Get the last cell number
            workbook.close();
            fi.close();
            return colCount;
        }

        // Method to get cell data
        public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
            fi = new FileInputStream(path);
            workbook = new XSSFWorkbook(fi);
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowNum);
            cell = row.getCell(colNum);

            String data;
            switch (cell.getCellType())
            {
                case STRING:
                    data = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    data = String.valueOf(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    data = String.valueOf(cell.getBooleanCellValue());
                    break;
                default:
                    data = "";
            }

            workbook.close();
            fi.close();
            return data;
        }

        // Method to set cell data
        public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException
        {
            File file = new File(path);
            fi = new FileInputStream(file);
            workbook = new XSSFWorkbook(fi);
            sheet = workbook.getSheet(sheetName);

            if (sheet.getRow(rowNum) == null) {
                row = sheet.createRow(rowNum);
            } else {
                row = sheet.getRow(rowNum);
            }

            cell = row.createCell(colNum);
            cell.setCellValue(data);

            fo = new FileOutputStream(file);
            workbook.write(fo);

            workbook.close();
            fi.close();
            fo.close();
        }

}

