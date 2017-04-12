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

public class insertFromPitchingTable {
	
	public static HashMap<Integer, ArrayList<Object>> fileContentss = new HashMap<Integer, ArrayList<Object>>();
	
	public static void ConnectToDB(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String databaseURL = "jdbc:oracle:thin:hr/hr@oracle1.cise.ufl.edu:1521:orcl";
			
			Connection databaseConnection = DriverManager.getConnection(databaseURL, "tk1", "canada55");
		
			String insert = "insert into Pitcher(Playerid, Year, TeamId, Wins, Losses, Games, GamesStarted, CompleteGames, Shutouts, Saves, OutsPitched, Hits, EarnedRuns, HomeRunsAgainst, Walks, Strikeouts, opponentBattingAverage, ERA, IntentionalWalks, WildPitches, BattersHitByPitch, balls, BattersFacedByPitcher, GamesFinished, RunsAllowed, SacrificesByOpposingBatters, GroundedIntoDoublePlay) " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";  
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
			//System.out.println("Hiiii");
			 ps.executeBatch();
			 System.out.println("Data Inserted!");
			
			databaseConnection.close();
		}
		catch(Exception e){
			System.out.println("Exception Found!!!!");
			System.out.println(e.getMessage());
		}
	}
	
	public static void insertFromPitchingTable(){
		try{
			FileInputStream excelFile = new FileInputStream(new File("C:\\Users\\Daniel002\\Documents\\DatabaseProject\\Pitching.xlsx"));
			
			//SimpleDateFormat dateEntries = new SimpleDateFormat("MM/dd/yyyy");
			
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
			XSSFSheet currentSheetOnFile = workbook.getSheetAt(0);
			
			//System.out.println("Hi");
			int totalRowCountBattingTable = currentSheetOnFile.getLastRowNum();
			//System.out.println("Total Row Count is " + totalRowCountBattingTable);
			final int columnCountBattingTable = 30;
			Integer playingYear = 0, playingDuration = 0, gamesPlayed = 0, gamesStarted = 0, losses = 0, shutOuts = 0;
			Integer completeGames = 0, numberOfOuts = 0, hitsAllowed = 0, homeRunsAllowed = 0, saves = 0, earnedRuns = 0;
			Integer wildPitches = 0, walks = 0, strikeOuts = 0, intentionalWalks = 0, battersFacingPitcher = 0, hitByPitch = 0, gamesFinished = 0;
			Integer runsAllowed = 0, balks = 0, sacrificeHitsAgainst = 0, sacrificeFliesAgainst = 0, groundIntoDoublePlaysCaused = 0;
			Double earnedRunAverage = 0.0, opponentBattingAverage = 0.0;
			
			for(int i = 1; i <= totalRowCountBattingTable; i++){
				ArrayList<Object> rowContents = new ArrayList<Object>();
				for(int j = 0; j < columnCountBattingTable; j++){
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
					/*if(j == 2){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							playingDuration = 0;
						}
						else
							playingDuration =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						//System.out.println(playingDuration);
						rowContents.add(2, playingDuration);
						continue;
					}*/
					if(j == 3){
						rowContents.add(2, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						continue;
					}
					/*if(j == 4){
						rowContents.add(4, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						continue;
					}*/
					if(j==5){
						rowContents.add(3, (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue());
						//System.out.println((int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue());
						continue;
					}
					if(j==6){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							losses = 0;
						}
						else
							losses = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(4, losses);
						//System.out.println(losses);
						continue;
					}
					if(j==7){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							gamesPlayed = 0;
						}
						else
							gamesPlayed = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(5, gamesPlayed);
						//System.out.println(gamesPlayed);
						continue;
					}
					if(j==8){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							gamesStarted = 0;
						}
						else
							gamesStarted = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(6, gamesStarted);
						//System.out.println(gamesStarted);
						continue;
					}
					if(j==9){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							completeGames = 0;
						}
						else
							completeGames = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(7, completeGames);
						//System.out.println(completeGames);
						continue;
					}
					if(j==10){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							shutOuts = 0;
						}
						else
							shutOuts = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(8, shutOuts);
						//System.out.println(shutOuts);
						continue;
					}
					if(j==11){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							saves = 0;
						}
						else
							saves = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(9, saves);
						//System.out.println(saves);
						continue;
					}
					if(j==12){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							numberOfOuts = 0;
						}
						else
							numberOfOuts = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(10, numberOfOuts);
						//System.out.println(numberOfOuts);
						continue;
					}
					if(j==13){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							hitsAllowed = 0;
						}
						else
							hitsAllowed = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(11, hitsAllowed);
						//System.out.println(hitsAllowed);
						continue;
					}
					if(j==14){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							earnedRuns = 0;
						}
						else
							earnedRuns = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(12, earnedRuns);
						//System.out.println(earnedRuns);
						continue;
					}
					if(j==15){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							homeRunsAllowed = 0;
						}
						else
							homeRunsAllowed = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(13, homeRunsAllowed);
						//System.out.println(homeRunsAllowed);
						continue;
					}
					if(j==16){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							walks = 0;
						}
						else
							walks = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(14, walks);
						//System.out.println(walks);
						continue;
					}
					if(j==17){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							strikeOuts = 0;
						}
						else
							strikeOuts = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(15, strikeOuts);
						//System.out.println(strikeOuts);
						continue;
					}
					if(j==18){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							opponentBattingAverage = 0.0;
						}
						else
							opponentBattingAverage = currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(16, opponentBattingAverage);
						//System.out.println(opponentBattingAverage);
						continue;
					}
					if(j==19){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							earnedRunAverage = 0.0;
						}
						else
							earnedRunAverage = currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(17, earnedRunAverage);
						//System.out.println(earnedRunAverage);
						continue;
					}
					if(j==20){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							intentionalWalks = 0;
						}
						else
							intentionalWalks = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(18, intentionalWalks);
						//System.out.println(intentionalWalks);
						continue;
					}
					if(j==21){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							wildPitches = 0;
						}
						else
							wildPitches = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(19, wildPitches);
						//System.out.println(wildPitches);
						continue;
					}
					if(j==22){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							hitByPitch = 0;
						}
						else
							hitByPitch = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(20, hitByPitch);
						//System.out.println(hitByPitch);
						continue;
					}
					if(j==23){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							balks = 0;
						}
						else
							balks = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(21, balks);
						//System.out.println(balks);
						continue;
					}
					if(j==24){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							battersFacingPitcher = 0;
						}
						else
							battersFacingPitcher = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(22, battersFacingPitcher);
						//System.out.println(battersFacingPitcher);
						continue;
					}
					if(j==25){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							gamesFinished = 0;
						}
						else
							gamesFinished = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(23, gamesFinished);
						//System.out.println(gamesFinished);
						continue;
					}
					if(j==26){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							runsAllowed = 0;
						}
						else
							runsAllowed = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(24, runsAllowed);
						//System.out.println(runsAllowed);
						continue;
					}
					if(j==27){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							sacrificeHitsAgainst = 0;
						}
						else
							sacrificeHitsAgainst = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(25, sacrificeHitsAgainst);
						//System.out.println(sacrificeHitsAgainst);
						continue;
					}
					/*if(j==28){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							sacrificeFliesAgainst = 0;
						}
						else
							sacrificeFliesAgainst = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(28, sacrificeFliesAgainst);
						//System.out.println(sacrificeFliesAgainst);
						continue;
					}*/
					if(j==29){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							groundIntoDoublePlaysCaused = 0;
						}
						else
							groundIntoDoublePlaysCaused = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(26, groundIntoDoublePlaysCaused);
						//System.out.println(groundIntoDoublePlaysCaused);
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
		insertFromPitchingTable();

	}

}
