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

public class insertFromFieldingTable {
	
	public static HashMap<Integer, ArrayList<Object>> fileContentss = new HashMap<Integer, ArrayList<Object>>();
	
	public static void ConnectToDB(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String databaseURL = "jdbc:oracle:thin:hr/hr@oracle1.cise.ufl.edu:1521:orcl";
			
			Connection databaseConnection = DriverManager.getConnection(databaseURL, "tk1", "canada55");
		
			String insert = "insert into DefensiveStatistics(PId, Year, playerStint, TeamId, Position, Games, GamesStarted, TimePlayedInField, Putouts, Assists, Errors, DoublePlays, PassedBalls, WildPitches, StolenBasesAllowed, CaughtStealing) " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";  
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
			   // System.out.println(counter);
			}
			System.out.println("Hiiii");
			 ps.executeBatch();
			 System.out.println("Data inserted!");
			
			
			databaseConnection.close();
		}
		catch(Exception e){
			System.out.println("Exception Found!!!!");
			System.out.println(e.getMessage());
		}
	}
	
	public static void insertIntoFieldingTable(){
		try{
			FileInputStream excelFile = new FileInputStream(new File("C:\\Users\\ddmac\\Documents\\CIS4301_Databases\\DatabaseProject\\Fielding.xlsx"));
			
			//SimpleDateFormat dateEntries = new SimpleDateFormat("MM/dd/yyyy");
			
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
			XSSFSheet currentSheetOnFile = workbook.getSheetAt(0);
			
			int totalRowCountFieldingTable = currentSheetOnFile.getLastRowNum();
			//System.out.println("Total Row Count is " + totalRowCountBattingTable);
			final int columnCountFieldingTable = 17;
			
			Integer assists = 0, putOuts = 0, playingYear = 0, playingDuration = 0, outsPlayed = 0, gamesPlayed = 0;
			Integer gamesStarted = 0, stolenBases = 0, caughtStealing = 0, zoneRating = 0, error = 0, doublePlays = 0;
			Integer wildThrows = 0, passedBalls = 0;
			String position = "";
					
			for(int i = 1; i <= totalRowCountFieldingTable; i++){
				ArrayList<Object> rowContents = new ArrayList<Object>();
				for(int j = 0; j < columnCountFieldingTable; j++){
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
							playingDuration = 0;
						}
						else
							playingDuration =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(playingDuration);
						rowContents.add(2, playingDuration);
						continue;
					}
					if(j == 3){
						rowContents.add(3, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						continue;
					}
					/*if(j == 4){
						rowContents.add(4, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						continue;
					}*/
					if(j == 5){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							position = "";
						}
						else
							position = currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
						//System.out.println(position);
						rowContents.add(4, position);
						continue;
					}
					if(j == 6){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							gamesPlayed = 0;
						}
						else
							gamesPlayed = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(gamesPlayed);
						rowContents.add(5, gamesPlayed);
						continue;
					}
					if(j == 7){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							gamesStarted = 0;
						}
						else
							gamesStarted = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(gamesStarted);
						rowContents.add(6, gamesStarted);
						continue;
					}
					if(j == 8){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							outsPlayed = 0;
						}
						else
							outsPlayed = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(outsPlayed);
						rowContents.add(7, outsPlayed);
						continue;
					}
					if(j == 9){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							putOuts = 0;
						}
						else
							putOuts = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(putOuts);
						rowContents.add(8, putOuts);
						continue;
					}
					if(j == 10){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							assists = 0;
						}
						else
							assists = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(assists);
						rowContents.add(9, assists);
						continue;
					}
					if(j == 11){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							error = 0;
						}
						else
							error = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(error);
						rowContents.add(10, error);
						continue;
					}
					if(j == 12){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							doublePlays = 0;
						}
						else
							doublePlays = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(doublePlays);
						rowContents.add(11, doublePlays);
						continue;
					}
					if(j == 13){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							passedBalls = 0;
						}
						else
							passedBalls = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(passedBalls);
						rowContents.add(12, passedBalls);
						continue;
					}
					if(j == 14){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							wildThrows = 0;
						}
						else
							wildThrows = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(wildThrows);
						rowContents.add(13, wildThrows);
						continue;
					}
					if(j == 15){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							stolenBases = 0;
						}
						else
							stolenBases = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(stolenBases);
						rowContents.add(14, stolenBases);
						continue;
					}
					if(j == 16){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							caughtStealing = 0;
						}
						else
							caughtStealing = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(caughtStealing);
						rowContents.add(15, caughtStealing);
						continue;
					}
				/*	if(j == 17){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							zoneRating = 0;
						}
						else
							zoneRating = (int) currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(zoneRating);
						rowContents.add(15, zoneRating);
						continue;
					}*/
					
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
		insertIntoFieldingTable();

	}

}
