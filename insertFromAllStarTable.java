import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class insertFromAllStarTable {
	
	public static HashMap<Integer, ArrayList<Object>> fileContentss = new HashMap<Integer, ArrayList<Object>>();
	
	public static void ConnectToDB(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String databaseURL = "jdbc:oracle:thin:hr/hr@oracle1.cise.ufl.edu:1521:orcl";
			
			Connection databaseConnection = DriverManager.getConnection(databaseURL, "tk1", "canada55");
		
			String insert = "insert into AllStar(PId, Year, gameNumber, gameId, Team, LeagueId, GamesPlayed, StartingPosition) " + "values(?,?,?,?,?,?,?,?)"; 
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
	
	public static void insertDataIntoAllStarTable(){
		try{
			FileInputStream excelFile = new FileInputStream(new File("C:\\Users\\ddmac\\Documents\\CIS4301_Databases\\DatabaseProject\\AllStarFull.xlsx"));
			
			//SimpleDateFormat dateEntries = new SimpleDateFormat("MM/dd/yyyy");
			
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
			XSSFSheet currentSheetOnFile = workbook.getSheetAt(0);
			
			int totalRowCountAllStarTable = currentSheetOnFile.getLastRowNum();
			//System.out.println("Total Row Count is " + totalRowCountBattingTable);
			final int columnCountAllStarTable = 8;
			
			Integer playingYear = 0, gameNum = 0, gamesPlayed = 0, startingPosition = 0;
			String teamID = "", gameID = "", leagueID = "";
			
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
							playingYear = 0;
							//System.out.println(playingYear);
						}
						else
							playingYear =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(playingYear);
						rowContents.add(1, playingYear);
						continue;
					}
					if(j == 2){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							gameNum = 0;
						}
						else
							gameNum =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(playingDuration);
						rowContents.add(2, gameNum);
						continue;
					}
					if(j == 3){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							gameID = "";
						}
						else
							gameID = currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
						
						rowContents.add(3, gameID);
					//	System.out.println(gameID);
						continue;
					}
					if(j == 4){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							teamID = "";
						}
						else
							teamID = currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
						
						rowContents.add(4, teamID);
						//System.out.println(teamID);
						continue;
					}
					if(j == 5){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							leagueID = "";
						}
						else
							leagueID = currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
						
						//System.out.println(leagueID);
						rowContents.add(5, leagueID);
						continue;
					}
					if(j == 6){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							gamesPlayed = 0;
						}
						else
							gamesPlayed =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						
						//System.out.println(gamesPlayed);
						rowContents.add(6, gamesPlayed);
						continue;
					}
					if(j == 7){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							startingPosition = 0;
						}
						else
							startingPosition =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
					
						//System.out.println(startingPosition);
						rowContents.add(7, startingPosition);
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
		
		insertDataIntoAllStarTable();

	}

}
