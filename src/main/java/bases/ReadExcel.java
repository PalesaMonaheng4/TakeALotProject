package bases;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ReadExcel {


        @DataProvider(name="brands")
        public static Object[][] brandNames() {
            Object[][] arrayObject = getExcelData("C:\\Users\\PMonaheng\\IdeaProjects\\Examination\\BrandNames.xlsx","Sheet1");
            return arrayObject;
        }

        public static String[][] getExcelData(String fileName, String sheetName) {
            String[][] arrayExcelData = null;
            try {
                FileInputStream fs = new FileInputStream(fileName);
                XSSFWorkbook wb = new XSSFWorkbook(fs);
                XSSFSheet sh = wb.getSheet(sheetName);
                DataFormatter formatter=new DataFormatter();

                XSSFRow row = sh.getRow(0);
                int noOfRows = sh.getPhysicalNumberOfRows();
                int noOfCols = row.getLastCellNum();

                arrayExcelData = new String[noOfRows-1][noOfCols];
                for (int i = 1; i < noOfRows; i++) {

                    for (int j = 0; j < noOfCols; j++) {
                        arrayExcelData[i-1][j] = formatter.formatCellValue( sh.getRow(i).getCell(j));
                    }

                }
                fs.close();
                        }
            catch (Exception e) {
                e.printStackTrace();
            }
            return arrayExcelData;
        }
        public  void writeToExcel(String brand,String overrideValue) throws Exception {
            FileInputStream fs=new FileInputStream("C:\\Users\\PMonaheng\\IdeaProjects\\Examination\\BrandNames.xlsx");
            XSSFWorkbook workbook=new XSSFWorkbook(fs);
            XSSFSheet sheet=workbook.getSheet("Sheet1");
            Cell cell;
            if(brand.equals("Energizer Recharge")) {
                cell = sheet.getRow(1).getCell(1);
                cell.setCellValue(overrideValue);
            }
            if(brand.equals("Diesel Only Brave")) {
                cell = sheet.getRow(2).getCell(1);
                cell.setCellValue(overrideValue);
            }
            if(brand.equals("Guess")) {
                cell = sheet.getRow(3).getCell(1);
                cell.setCellValue(overrideValue);
            }
            if(brand.equals("RCT")) {
                cell = sheet.getRow(4).getCell(1);
                cell.setCellValue(overrideValue);
            }
            if(brand.equals("Iphone")) {
                cell = sheet.getRow(5).getCell(1);
                cell.setCellValue(overrideValue);
            }
            if(brand.equals("Barbie Doll")) {
                cell = sheet.getRow(6).getCell(1);
                cell.setCellValue(overrideValue);
            }
            if(brand.equals("Kettle")) {
                cell = sheet.getRow(7).getCell(1);
                cell.setCellValue(overrideValue);
            }
            if(brand.equals("Casals")) {
                cell = sheet.getRow(8).getCell(1);
                cell.setCellValue(overrideValue);
            }
            if(brand.equals("Hisense")) {
                cell = sheet.getRow(9).getCell(1);
                cell.setCellValue(overrideValue);
            }
            fs.close();
            FileOutputStream fileOutput=new FileOutputStream("C:\\Users\\PMonaheng\\IdeaProjects\\Examination\\BrandNames.xlsx");
            workbook.write(fileOutput);
            fileOutput.close();
            Reporter.log("End of writing data to excel");
        }
}


