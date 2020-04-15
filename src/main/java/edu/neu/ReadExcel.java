package edu.neu;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class ReadExcel {

    private  static double[] rhsArray;
    private  static double[][] lhsArray;
    private  static String[] teamNames;
    public static Map<String,String> map = new HashMap<>();

    public void readExcel(FileInputStream file , int num){
        try{

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
     
             //Get first/desired sheet from the workbook
             XSSFSheet sheet = workbook.getSheetAt(num-1);
             
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

                     String homeVsAway = home.trim()+","+away.trim();
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
                             }else if (cell.getStringCellValue().equalsIgnoreCase("na")) {
                                 lhsArray[i][j] = 0;
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
        else if(s1.equalsIgnoreCase("na")){
            team1Score += .5;
            team2Score += .5;
        }

        if(s2.equalsIgnoreCase("W")){
            team2Score += 1;
        }
        else if(s2.equalsIgnoreCase("L")){
            team1Score += 1;
        }else if(s2.equalsIgnoreCase("na")){
            team1Score += .5;
            team2Score += .5;
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


    public static void main(String args[])  {
        boolean repeat = false;
        boolean firstRepeat = false;
        int number1 = 0;
        int number2 = 0;
        HashMap<Integer,String> teams = new HashMap<>();
        int num=0;

        try{

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            do {
                System.out.println("We have two datasets - the 2018-2019 dataID-19\n Which seasons ranking would you like to view?\n Enter 1 for 2018-2019 and 2 for COVID-19 dataset");
                firstRepeat = false;
                String in = reader.readLine();
                if (in == null || in.isEmpty() || in.matches("\\s+") || in.equalsIgnoreCase("0")) {
                    firstRepeat = true;
                }else if (in.equalsIgnoreCase("1") || in.equalsIgnoreCase("2"))
                    num = Integer.parseInt(in);
                else
                    firstRepeat = true;
            }while(firstRepeat==true);


            do{
                if(num ==1){
                    System.out.println("EPL fan? Check which team wins and their Rankings");
                    teams.put(1,"Arsenal");
                    teams.put(2,"Bournemouth");
                    teams.put(3,"Brighton & Hove Albion");
                    teams.put(4,"Burnley");
                    teams.put(5,"Cardiff City");
                    teams.put(6,"Chelsea");
                    teams.put(7,"Crystal Palace");
                    teams.put(8,"Everton");
                    teams.put(9,"Fulham");
                    teams.put(10,"Huddersfield Town");
                    teams.put(11,"Leicester City");
                    teams.put(12,"Liverpool");
                    teams.put(13,"Manchester City");
                    teams.put(14,"Manchester United");
                    teams.put(15,"Newcastle United");
                    teams.put(16,"Southampton");
                    teams.put(17,"Tottenham Hotspur");
                    teams.put(18,"Watford");
                    teams.put(19,"West Ham United");
                    teams.put(20,"Wolverhampton Wanderers");

                }else if(num==2){
                    System.out.println("EPL fan? Check which team wins and their Rankings");
                    teams.put(1,"Arsenal");
                    teams.put(2,"Aston Villa");
                    teams.put(3,"Bournemouth");
                    teams.put(4,"Brighton & Hove Albion");
                    teams.put(5,"Burnley");
                    teams.put(6,"Chelsea");
                    teams.put(7,"Crystal Palace");
                    teams.put(8,"Everton");
                    teams.put(9,"Leicester City");
                    teams.put(10,"Liverpool");
                    teams.put(11,"Manchester City");
                    teams.put(12,"Manchester United");
                    teams.put(13,"Newcastle United");
                    teams.put(14,"Norwich City");
                    teams.put(15,"Sheffield United");
                    teams.put(16,"Southampton");
                    teams.put(17,"Tottenham Hotspur");
                    teams.put(18,"Watford");
                    teams.put(19,"West Ham United");
                    teams.put(20,"Wolverhampton Wanderers");
                }else{
                    System.out.println("Enter a valid number- 1 or 2");
                }

                for(Map.Entry mappings: teams.entrySet()){
                    System.out.println("Enter --"+ mappings.getKey() + " for -- " +mappings.getValue());
                }

                repeat = false;

                String input = reader.readLine();
                if(input.isEmpty() || input.matches("\\s+"))
                    repeat = true;

                String[] str = input.trim().split("\\s+");
                if(str[0].matches("\\s+") || str[1].matches("\\s") || str[0].isEmpty() || str[1].isEmpty())
                    repeat = true;


                number1 = Integer.parseInt(str[0].trim());
                number2 = Integer.parseInt(str[1].trim());


                if(number1 == 0 || number2 ==0 || !teams.containsKey(number1) || !teams.containsKey(number2)){
                    System.out.println("Kindly enter valid number separated by space");
                    repeat = true;
                }

            }while(repeat);

            ReadExcel excel = new ReadExcel();
            FileInputStream file = new FileInputStream(new File("Dataset_new1.xlsx"));
            excel.readExcel(file, num);

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

        }catch (Exception e){
            System.out.println("Something went wrong");
        }

    }
}