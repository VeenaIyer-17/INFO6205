package edu.neu;

import Jama.Matrix;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class ReadExcel {

    private  static double[] rhsArray;
    private  static double[][] lhsArray;
    private  static String[] teamNames;

    public void readExcel(FileInputStream file ){
        try{

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
     
             //Get first/desired sheet from the workbook
             XSSFSheet sheet = workbook.getSheetAt(0);
             
             int numRows = sheet.getLastRowNum()+1;
             int numCols = sheet.getRow(0).getLastCellNum();

             lhsArray = new double[numRows-1][numCols-1];
             rhsArray = new double[numRows-1];
             this.getTeamNames(sheet.getRow(0),numCols);

             for(int i=0;i<numRows-1;i++) {
                 double draw=0, wins=0, loss=0;
                 int self_row=0,self_col=0;

                 Row row = sheet.getRow(i+1);

                 for (int j = 0; j < numCols-1; j++) {
                     Cell cell = row.getCell(j+1);

                     switch (cell.getCellType()) {
                         case Cell.CELL_TYPE_BLANK:
                             self_row = i;
                             self_col = j;
                             break;

                         case Cell.CELL_TYPE_STRING:
                             if (cell.getStringCellValue().equalsIgnoreCase("W")) {
                                 lhsArray[i][j] = Integer.parseInt("-1");
                                 wins++;
                             }else if (cell.getStringCellValue().equalsIgnoreCase("L")) {
                                 lhsArray[i][j] = Integer.parseInt("-1");
                                 loss++;
                             }else if (cell.getStringCellValue().equalsIgnoreCase("D")) {
                                 lhsArray[i][j] = -1;
                                 draw++;
                             }else{
                                 continue;
                             }
                             break;
                     }
                 }

                 lhsArray[self_row][self_col] = wins+loss+draw+2;
                 wins+=draw/2;
                 loss+=draw/2;
                 double result = wins+(wins-loss)/2;
                 rhsArray[i] = result;
             }
             file.close();

         }catch(Exception e){
             e.printStackTrace();
         }
    }

    public static void getTeamNames(Row row0,int cols){
        teamNames = new String[cols];

        for(int i=1; i<row0.getLastCellNum(); i++){
            teamNames[i]= String.valueOf(row0.getCell(i));
        }
    }

    public static void main(String args[]) throws FileNotFoundException {
        ReadExcel excel = new ReadExcel();
        FileInputStream file = new FileInputStream(new File("Dataset.xlsx"));
        excel.readExcel(file);
        new RankingSystem(lhsArray, rhsArray,teamNames);
    }
}