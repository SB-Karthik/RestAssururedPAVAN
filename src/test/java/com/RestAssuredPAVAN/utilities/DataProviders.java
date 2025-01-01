package com.RestAssuredPAVAN.utilities;


import org.testng.annotations.DataProvider;
import java.io.IOException;

public class DataProviders {

    // DataProvider to get all data from the Excel sheet
    @DataProvider(name = "Data")
    public Object[][] getAllData() throws IOException {
        String path = System.getProperty("user.dir") + "/testData/Userdata.xlsx";
        XLUtility xl = new XLUtility(path);

        int rowNum = xl.getRowCount("Sheet1");
        int colNum = xl.getColumnCount("Sheet1", 1);

        String[][] apiData = new String[rowNum][colNum];

        for (int i = 1; i <= rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                apiData[i - 1][j] = xl.getCellData("Sheet1", i, j); // Adjusted for zero-based indexing
            }
        }

        return apiData;
    }

    // DataProvider to get only usernames from the Excel sheet
    @DataProvider(name = "UserNames")
    public Object[] getUserNames() throws IOException {
        String path = System.getProperty("user.dir") + "/testData/Userdata.xlsx";
        XLUtility xl = new XLUtility(path);

        int rowNum = xl.getRowCount("Sheet1");

        String[] userNames = new String[rowNum];

        for (int i = 1; i <= rowNum; i++) {
            userNames[i - 1] = xl.getCellData("Sheet1", i, 1); // Assuming the first column contains usernames
        }

        return userNames;
    }
}
