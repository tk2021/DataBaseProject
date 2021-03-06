import java.util.Scanner;

import java.lang.String;
import java.io.File;
import java.io.FileInputStream;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.*;
import java.text.*;

import java.sql.*;

public class insertFromBattingTable {
	
	public static HashMap<Integer, ArrayList<Object>> fileContentss = new HashMap<Integer, ArrayList<Object>>();
	
	public static void ConnectToDB(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String databaseURL = "jdbc:oracle:thin:hr/hr@oracle1.cise.ufl.edu:1521:orcl";
			
			Connection databaseConnection = DriverManager.getConnection(databaseURL, "tk1", "canada55");
		
			String insert = "insert into BattingStatistics(PId, Year, playerStint, TeamId, Games, AtBats, Runs, Hits, Doubles, Triples, Homeruns, RBI, StolenBases, CaughtStealing, Walks, strikeOuts, intentionalWalks, hitByPitch, sacrificedHits, sacrificeFlies, groundedIntoDoublePlay) " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";  
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
			
			
			databaseConnection.close();
		}
		catch(Exception e){
			System.out.println("Exception Found!!!!");
			System.out.println(e.getMessage());
		}
	}
	
	public static void insertIntoBattingTable(){
		try{
			FileInputStream excelFile = new FileInputStream(new File("C:\\Users\\ddmac\\Documents\\CIS4301_Databases\\DatabaseProject\\Batting.xlsx"));
			
		//	SimpleDateFormat dateEntries = new SimpleDateFormat("MM/dd/yyyy");
			
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
			XSSFSheet currentSheetOnFile = workbook.getSheetAt(0);
			
			
			int totalRowCountBattingTable = currentSheetOnFile.getLastRowNum();
			//System.out.println("Total Row Count is " + totalRowCountBattingTable);
			final int columnCountBattingTable = 22;
			Integer playingYear = 0, playingDuration = 0, atBats = 0, runsScored = 0;
			Integer runsBattedIn = 0, homeRuns = 0, hits = 0, doubles = 0, triples = 0;
			Integer groundIntoDoublePlay = 0, sacrificeFlies = 0, sacrificeHits = 0, walks = 0, intentionalWalks = 0;
			Integer caughtStealing = 0, stolenBases = 0, strikeOuts = 0, hitByPitch = 0;
			
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
						rowContents.add(3, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						continue;
					}*/
					if(j==5){
						rowContents.add(4, (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue());
						//System.out.println((int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue());
						continue;
					}
					if(j==6){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							atBats = 0;
						}
						else
							atBats = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(5, atBats);
						//System.out.println(atBats);
						continue;
					}
					if(j==7){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							runsScored = 0;
						}
						else
							runsScored = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(6, runsScored);
						//System.out.println(runsScored);
						continue;
					}
					if(j==8){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							hits = 0;
						}
						else
							hits = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(7, hits);
						//System.out.println(hits);
						continue;
					}
					if(j==9){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							doubles = 0;
						}
						else
							doubles = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(8, doubles);
						//System.out.println(doubles);
						continue;
					}
					if(j==10){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							triples = 0;
						}
						else
							triples = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(9, triples);
						//System.out.println(triples);
						continue;
					}
					if(j==11){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							homeRuns = 0;
						}
						else
							homeRuns = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(10, homeRuns);
						//System.out.println(homeRuns);
						continue;
					}
					if(j==12){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							runsBattedIn = 0;
						}
						else
							runsBattedIn = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(11, runsBattedIn);
						//System.out.println(runsBattedIn);
						continue;
					}
					if(j==13){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							stolenBases = 0;
						}
						else
							stolenBases = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(12, stolenBases);
						//System.out.println(stolenBases);
						continue;
					}
					if(j==14){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							caughtStealing = 0;
						}
						else
							caughtStealing = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(13, caughtStealing);
						//System.out.println(caughtStealing);
						continue;
					}
					if(j==15){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							walks = 0;
						}
						else
							walks = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(14, walks);
						//System.out.println(walks);
						continue;
					}
					if(j==16){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							strikeOuts = 0;
						}
						else
							strikeOuts = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(15, strikeOuts);
						//System.out.println(strikeOuts);
						continue;
					}
					if(j==17){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							intentionalWalks = 0;
						}
						else
							intentionalWalks = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(16, intentionalWalks);
						//System.out.println(intentionalWalks);
						continue;
					}
					if(j==18){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							hitByPitch = 0;
						}
						else
							hitByPitch = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(17, hitByPitch);
						//System.out.println(hitByPitch);
						continue;
					}
					if(j==19){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							sacrificeHits = 0;
						}
						else
							sacrificeHits = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(18, sacrificeHits);
						//System.out.println(sacrificeHits);
						continue;
					}
					if(j==20){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							sacrificeFlies = 0;
						}
						else
							sacrificeFlies = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(19, sacrificeFlies);
						//System.out.println(sacrificeFlies);
						continue;
					}
					if(j==21){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							groundIntoDoublePlay = 0;
						}
						else
							groundIntoDoublePlay = (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
						rowContents.add(20, groundIntoDoublePlay);
						//System.out.println(groundIntoDoublePlay);
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
		
		insertIntoBattingTable();
	}

}
