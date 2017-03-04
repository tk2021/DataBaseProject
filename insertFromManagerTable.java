import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class insertFromManagerTable {

	public static HashMap<Integer, ArrayList<Object>> fileContentss = new HashMap<Integer, ArrayList<Object>>();
	
	public static void ConnectToDB(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//NOT SURE IF THIS CORRECT URL
			String databaseURL = "jdbc:oracle:thin:hr/hr@oracle1.cise.ufl.edu:1521:orcl";
			//INCLUDE REAL USERNAME AND PASSWORD
			Connection databaseConnection = DriverManager.getConnection(databaseURL, "tk1", "canada55");
		
			String insert = "insert into Manager(ManagerId, Year, TeamId, LeagueId, InSeason, Games, Wins, Losses, Rank, PlayerManager) " + "values(?,?,?,?,?,?,?,?,?,?)"; 
	        PreparedStatement ps = databaseConnection.prepareStatement(insert); 
	      
	        int counter = 0;
			for (Map.Entry<Integer, ArrayList<Object>> entry : fileContentss.entrySet()) {
			    
			    ArrayList<Object> value = entry.getValue();
			    int index = 1;
			  
			    for(Object obj : value){
			    	ps.setObject(index, obj);
			    	 index++;
			    	 counter++;
			    }
			    ps.addBatch();
			    index = 1;
			   System.out.println(counter);
			}
			System.out.println("Hiiii");
			 ps.executeBatch();  
			 System.out.println("Finished Inserting!");
			
			
			databaseConnection.close();
		}
		catch(Exception e){
			System.out.println("Exception Found!!!!");
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void insertDataIntoManagerTable(){
		try{
			FileInputStream excelFile = new FileInputStream(new File("C:\\Users\\ddmac\\Documents\\CIS4301_Databases\\DatabaseProject\\Manager.xlsx"));
			
			//SimpleDateFormat dateEntries = new SimpleDateFormat("MM/dd/yyyy");
			
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
			XSSFSheet currentSheetOnFile = workbook.getSheetAt(0);
			
			int totalRowCountAllStarTable = currentSheetOnFile.getLastRowNum();
			//System.out.println("Total Row Count is " + totalRowCountBattingTable);
			final int columnCountAllStarTable = 10;
			
			Integer managingYear = 0, rank = 0, wins = 0, losses = 0, games = 0, season = 0;
			String teamID = "", leagueID = "", isPlayerManager = "";
			
			for(int i = 1; i <= totalRowCountAllStarTable; i++){
				ArrayList<Object> rowContents = new ArrayList<Object>();
				for(int j = 0; j < columnCountAllStarTable; j++){
					if(j == 0){
						rowContents.add(0, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						continue;
					}
					if(j == 1){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							managingYear = 0;
						}
						else
							managingYear =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(managingYear);
						rowContents.add(1, managingYear);
						continue;
					}
					if(j == 2){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							teamID = "";
						}
						else
							teamID = currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
						
						rowContents.add(2, teamID);
						//System.out.println(teamID);
						continue;
					}
					if(j == 3){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							leagueID = "";
						}
						else
							leagueID = currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
						
						//System.out.println(leagueID);
						rowContents.add(3, leagueID);
						continue;
					}
					if(j == 4){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							season = 0;
						}
						else
							season =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(season);
						rowContents.add(4, season);
						continue;
					}
					if(j == 5){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							games = 0;
						}
						else
							games =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(games);
						rowContents.add(5, games);
						continue;
					}
					if(j == 6){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							wins = 0;
						}
						else
							wins =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(wins);
						rowContents.add(6, wins);
						continue;
					}
					if(j == 7){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							losses = 0;
						}
						else
							losses =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(losses);
						rowContents.add(7, losses);
						continue;
					}
					if(j == 8){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rank = 0;
						}
						else
							rank =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(rank);
						rowContents.add(8, rank);
						continue;
					}
					if(j == 9){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							isPlayerManager = "";
						}
						else
							isPlayerManager = currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
						
						//System.out.println(isPlayerManager);
						rowContents.add(9, isPlayerManager);
						continue;
					}
				}
				fileContentss.put(i, rowContents);
			}
			ConnectToDB();
		}
		catch (Exception e){
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
		insertDataIntoManagerTable();

	}

}
