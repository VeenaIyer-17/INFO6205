package edu.neu;

import Jama.Matrix;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReadExcel {

    private  static double[] rhsArray;
    private  static double[][] lhsArray;
    private  static String[] teamNames;
    public static Map<String,String> map = new HashMap<>();

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

                 String home =  sheet.getRow(i+1).getCell(0).getStringCellValue();

                 for (int j = 0; j < numCols-1; j++) {
                     Cell cell = row.getCell(j+1);

                     String away = sheet.getRow(0).getCell(j+1).getStringCellValue();

                     String homeVsAway = home+","+away;
                     homeVsAway.trim();

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
                             map.put(homeVsAway,cell.getStringCellValue());
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

    public static void proability(String team1, String team2, Map<String, String> map){

        String s1 = map.get(team1+","+team2);
        String s2 = map.get(team2+","+team1);

        Double team1Score = 0.0;
        Double team2Score = 0.0;

        if(s1.equalsIgnoreCase("W")){
            team1Score += 1;
        }
        else if(s1.equalsIgnoreCase("L")){
            team2Score += 1;
        }

        if(s2.equalsIgnoreCase("W")){
            team2Score += 1;
        }
        else if(s2.equalsIgnoreCase("L")){
            team1Score += 1;
        }

        if(s1.equalsIgnoreCase("D") || s2.equalsIgnoreCase("D")){
            team1Score += .5;
            team2Score += .5;
        }

        double probabilityOfTeam1 = team1Score/2;
        double probabilityOfTeam2 = team2Score/2;

        System.out.println("");
        System.out.println("####################################");
        System.out.println("Probability of given teams");
        System.out.println("####################################");
        System.out.println("Probability of : "+team1+" "+String.valueOf(probabilityOfTeam1));
        System.out.println("Probability of : "+team2+" "+String.valueOf(probabilityOfTeam2));


    }


    public static void main(String args[]) throws IOException {
        boolean repeat = false;
        int number1 = 0;
        int number2 = 0;
        HashMap<Integer,String> teams = new HashMap<>();

        do{
            System.out.println("EPL fan? Check which team wins and their Rankings");

            teams.put(1,"Man United");
            teams.put(2,"Bournemouth");
            teams.put(3,"Fulham");
            teams.put(4,"Huddersfield");
            teams.put(5,"Newcastle");
            teams.put(6,"Watford");
            teams.put(7,"Wolves");
            teams.put(8,"Arsenal");
            teams.put(9,"Liverpool");
            teams.put(10,"Southampton");
            teams.put(11,"Cardiff");
            teams.put(12,"Chelsea");
            teams.put(13,"Everton");
            teams.put(14,"Leicester");
            teams.put(15,"Tottenham");
            teams.put(16,"West Ham");
            teams.put(17,"Brighton");
            teams.put(18,"Burnley");
            teams.put(19,"Man City");
            teams.put(20,"Crystal Palace");

            for(Map.Entry mappings: teams.entrySet()){
                System.out.println("Enter --"+ mappings.getKey() + " for -- " +mappings.getValue());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            repeat = false;

            String input = reader.readLine();
            String[] str = input.trim().split("\\s+");
            number1 = Integer.parseInt(str[0].trim());
            number2 = Integer.parseInt(str[1].trim());


            if(number1 == 0 || number2 ==0 || !teams.containsKey(number1) || !teams.containsKey(number2)){
                System.out.println("Kindly enter valid number separated by space");
                repeat = true;
            }
        }while(repeat);

            ReadExcel excel = new ReadExcel();
            FileInputStream file = new FileInputStream(new File("Dataset.xlsx"));
            excel.readExcel(file);

            System.out.println("##########################################");
            System.out.println("Home Team, Away Team : Result(home team)");
            System.out.println("###########################################");

            for(String key : map.keySet()){
                System.out.println(key+" : "+map.get(key));
            }

            new RankingSystem(lhsArray, rhsArray,teamNames);

            String team1 = teams.get(number1);
            String team2 = teams.get(number2);

            proability(team1, team2, map);
    }
}