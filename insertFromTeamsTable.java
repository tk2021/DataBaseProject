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


public class insertFromTeamsTable {
	
	public static HashMap<Integer, ArrayList<Object>> fileContentss = new HashMap<Integer, ArrayList<Object>>();
	
	public static void ConnectToDB(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String databaseURL = "jdbc:oracle:thin:hr/hr@oracle1.cise.ufl.edu:1521:orcl";
			
			Connection databaseConnection = DriverManager.getConnection(databaseURL, "tk1", "canada55");
		
			String insert = "insert into Team(Year, LeagueID, TeamId, FranchiseId, Division, Rank, Name) " + "values(?,?,?,?,?,?,?)";  
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
			
			
			databaseConnection.close();
		}
		catch(Exception e){
			System.out.println("Exception Found!!!!");
			System.out.println(e.getMessage());
		}
	}

public static void insertDataIntoTeamsTable(){
	try{
		FileInputStream excelFile = new FileInputStream(new File("C:\\Users\\ddmac\\Documents\\CIS4301_Databases\\DatabaseProject\\Teams.xlsx"));
		
		//SimpleDateFormat dateEntries = new SimpleDateFormat("MM/dd/yyyy");
		
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
		XSSFSheet currentSheetOnFile = workbook.getSheetAt(0);
		
		int totalRowCountAllStarTable = currentSheetOnFile.getLastRowNum();
		//System.out.println("Total Row Count is " + totalRowCountBattingTable);
		final int columnCountAllStarTable = 41;
		
		String division = "", teamID = "", teamName = "", franchiseID = "", leagueID = "";
		Integer playingYear = 0, rank = 0;
		
		for(int i = 1; i <= totalRowCountAllStarTable; i++){
			ArrayList<Object> rowContents = new ArrayList<Object>();
			for(int j = 0; j < columnCountAllStarTable; j++){
				if(j == 0){
					if(currentSheetOnFile.getRow(i).getCell(j) == null){
						playingYear = 0;
						//System.out.println(playingYear);
					}
					else
						playingYear =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
					//System.out.println(playingYear);
					rowContents.add(0, playingYear);
					continue;
				}
				if(j == 1){
					if(currentSheetOnFile.getRow(i).getCell(j) == null){
						leagueID = "";
					}
					else
						leagueID = currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
					
					//System.out.println(leagueID);
					rowContents.add(1, leagueID);
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
						franchiseID = "";
					}
					else
						franchiseID = currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
					
					rowContents.add(3, franchiseID);
					//System.out.println(franchiseID);
					continue;
				}
				if(j == 4){
					if(currentSheetOnFile.getRow(i).getCell(j) == null){
						division = "";
					}
					else
						division = currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
					
					rowContents.add(4, division);
					//System.out.println(division);
					continue;
				}
				if(j == 5){
					if(currentSheetOnFile.getRow(i).getCell(j) == null){
						rank = 0;
					}
					else
						rank =  (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue();
					//System.out.println(rank);
					rowContents.add(5, rank);
					j = 39;
					continue;
				}
				if(j == 40){
					if(currentSheetOnFile.getRow(i).getCell(j) == null){
						teamName = "";
					}
					else
						teamName = currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
					
					rowContents.add(6, teamName);
				//	System.out.println(teamName);
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
		insertDataIntoTeamsTable();

	}

}
