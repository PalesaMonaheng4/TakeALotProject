package bases;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
                        //System.out.println(arrayExcelData[i-1][j]);
                      /*  if(arrayExcelData[2][0].equalsIgnoreCase("adidas")) {
                            Reporter.log(arrayExcelData[2][0]);
                        }*/
                    }

                }
                fs.close();
                        }
            catch (Exception e) {
                e.printStackTrace();
            }
            return arrayExcelData;
        }
}


