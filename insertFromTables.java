import java.util.Scanner;
import java.lang.String;
import java.io.File;
import java.io.FileInputStream;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Map;

import org.apache.poi.xssf.usermodel.*;
//import java.text.*;

import java.sql.*;
import java.text.SimpleDateFormat;

public class insertFromTables {
	
	public static Map<Integer, ArrayList<Object>> fileContentss = new HashMap<Integer, ArrayList<Object>>();
	
	public static void ConnectToDB(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String databaseURL = "jdbc:oracle:thin:hr/hr@oracle1.cise.ufl.edu:1521:orcl";
			
			Connection databaseConnection = DriverManager.getConnection(databaseURL, "tk1", "canada55");
			
			
			String insert = "insert into teamMember(playerId, birthDate, BirthCountry, birthState, birthCity, deathDate, deathCountry, deathState, deathCity, Name, Weight, Height, Bats, Throws) " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";  
	        PreparedStatement ps = databaseConnection.prepareStatement(insert); 
	      
	        int counter = 0;
			for (Map.Entry<Integer, ArrayList<Object>> entry : fileContentss.entrySet()) {
			    
			    ArrayList<Object> value = entry.getValue();
			    int index = 1;
			  
			    for(Object obj : value){
			  
			    	if(index == 1){
			    		ps.setString(index, obj.toString());
			    	}
			    	if(index == 2){
			    		ps.setDate(index, (Date) obj);
			    	}
			    	if(index == 3){
			    		ps.setString(index, obj.toString());
			    	}
			    	if(index == 4){
			    		ps.setString(index, obj.toString());
			    	}
			    	if(index == 5){
			    		ps.setString(index, obj.toString());
			    	}
			    	if(index == 6){
			    		ps.setDate(index, (Date) obj);
			    	}
			    	if(index == 7){
			    		ps.setString(index, obj.toString());
			    	}
			    	if(index == 8){
			    		ps.setString(index, obj.toString());
			    	}
			    	if(index == 9){
			    		ps.setString(index, obj.toString());
			    	}
			    	if(index == 10){
			    		ps.setString(index, obj.toString());
			    	}
			    	if(index == 11){
			    		ps.setInt(index, (int) obj);
			    	}
			    	if(index == 12){
			    		ps.setInt(index, (int) obj);
			    	}
			    	if(index == 13){
			    		ps.setString(index, obj.toString());
			    	}
			    	if(index == 14){
			    		ps.setString(index, obj.toString());
			    	}
			    	
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
	
	public static void insertIntoPlayerTable(){
		
		try{
			FileInputStream excelFile = new FileInputStream(new File("C:\\Users\\ddmac\\Documents\\CIS4301_Databases\\DatabaseProject\\Master.xlsx"));
			
			SimpleDateFormat dateEntries = new SimpleDateFormat("MM/dd/yyyy");
			
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
			XSSFSheet currentSheetOnFile = workbook.getSheetAt(0);
			
			int totalRowCountMasterTable = currentSheetOnFile.getLastRowNum();
			//System.out.println("Total Row Count is " + totalRowCountMasterTable);
			final int columnCountMasterTable = 20;
			String birthYear = "", birthMonth = "", birthDay = "", combinedBirthValues = "";
			String deathYear = "", deathMonth = "", deathDay = "", combinedDeathValues = "";
			String firstName = "", lastName = "", fullName = "";
			java.util.Date birthDates = null, deathDates = null;
			java.sql.Date sqlDeathDate = null, sqlBirthDate = null;
		
			for(int i = 1; i <= totalRowCountMasterTable; i++){
				ArrayList<Object> rowContents = new ArrayList<Object>();
				for(int j = 0; j < columnCountMasterTable; j++){
					if(j == 0){
						rowContents.add(0, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						continue;
					}
					if(j == 1){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							birthYear = "";
						}
						else
							birthYear =  Integer.toString((int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue());
						
						if(currentSheetOnFile.getRow(i).getCell(j+1) == null){
							birthMonth = "";
						}
						else
							birthMonth = Integer.toString((int)currentSheetOnFile.getRow(i).getCell(j+1).getNumericCellValue());
						
						if(currentSheetOnFile.getRow(i).getCell(j+2) == null){
							birthDay = "";
						}
						else 
							birthDay = Integer.toString((int)currentSheetOnFile.getRow(i).getCell(j+2).getNumericCellValue());
			
						if(birthYear == ""){
							//combinedBirthValues = "";
							sqlBirthDate = null;
							//System.out.println(birthYear);
						}
						else if(birthMonth == "" || birthDay == ""){
							sqlBirthDate = null;
							//combinedBirthValues = birthYear;
							//System.out.println(birthYear);
						}
						else{
							combinedBirthValues = birthMonth + "/" + birthDay + "/" + birthYear;
							//System.out.println(combinedBirthValues);
						}
						if(combinedBirthValues != ""){
							birthDates = dateEntries.parse(combinedBirthValues);
							sqlBirthDate = new java.sql.Date(birthDates.getTime());
							rowContents.add(1, sqlBirthDate);
						}
						else{
							rowContents.add(1, sqlBirthDate);
						}
						//System.out.println(birthDates);
						combinedBirthValues = "";
						birthDates = null;
						sqlBirthDate = null;
						j = 3;
						continue;
					}
					if(j == 4){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rowContents.add(2, "");
							//System.out.println();
						}
						else{
							rowContents.add(2, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
							//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						}
						continue;
					}
					if(j == 5){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rowContents.add(3, "");
							//System.out.println();
						}
						else{
							rowContents.add(3, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
							//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						}
						continue;
					}
					if(j == 6){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rowContents.add(4, "");
							//System.out.println();
						}
						else{
							rowContents.add(4, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
							//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						}
						continue;
					}
					if(j == 7){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							deathYear = "";
						}
						else
							deathYear =  Integer.toString((int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue());
						
						if(currentSheetOnFile.getRow(i).getCell(j+1) == null){
							deathMonth = "";
						}
						else
							deathMonth = Integer.toString((int)currentSheetOnFile.getRow(i).getCell(j+1).getNumericCellValue());
						
						if(currentSheetOnFile.getRow(i).getCell(j+2) == null){
							deathDay = "";
						}
						else 
							deathDay = Integer.toString((int)currentSheetOnFile.getRow(i).getCell(j+2).getNumericCellValue());
			
						if(deathYear == ""){
							sqlDeathDate = null;
							//combinedDeathValues = "";
							//System.out.println(combinedDeathValues);
						}
						else if(deathMonth == "" || deathDay == ""){
							sqlDeathDate = null;
							//combinedDeathValues = deathYear;
							//System.out.println(combinedDeathValues);
						}
						else{
							combinedDeathValues = deathMonth + "/" + deathDay + "/" + deathYear;
							//System.out.println(combinedDeathValues);
						}
						if(combinedDeathValues != ""){
							deathDates = dateEntries.parse(combinedDeathValues);
							sqlDeathDate = new java.sql.Date(deathDates.getTime());
							rowContents.add(5, sqlDeathDate);
						}
						else{
							rowContents.add(5, sqlDeathDate);
						}
						//System.out.println(sqlDeathDate);
						combinedDeathValues = "";
						deathDates = null;
						sqlDeathDate = null;
						j = 9;
						continue;
					}
					if(j == 10){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rowContents.add(6, "");
							//System.out.println();
						}
						else{
							rowContents.add(6, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
							//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						}
						continue;
					}
					if(j == 11){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rowContents.add(7, "");
							//System.out.println();
						}
						else{
							rowContents.add(7, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
							//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						}
						continue;
					}
					if(j == 12){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rowContents.add(8, "");
							//System.out.println();
						}
						else{
							rowContents.add(8, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
							//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						}
						continue;
					}
					if(j == 13){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							firstName = "";
						}
						else{
							firstName =  currentSheetOnFile.getRow(i).getCell(j).getStringCellValue();
						}
						if(currentSheetOnFile.getRow(i).getCell(j + 1) == null){
							lastName = "";
						}
						else{
							lastName =  currentSheetOnFile.getRow(i).getCell(j + 1).getStringCellValue();
						}
						fullName = firstName + " " + lastName;
						rowContents.add(9, fullName);
						//System.out.println(fullName);
						j = 15;
						continue;
					}
					/*if(j == 15){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rowContents.add(10, "");
							//System.out.println();
						}
						else{
							rowContents.add(10, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
							//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						}
						continue;
					}*/
					if(j == 16){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rowContents.add(10, 0);
							//System.out.println(0);
						}
						else{
							rowContents.add(10, (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue());
							//System.out.println((int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue());
						}
						continue;
					}
					if(j == 17){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rowContents.add(11, 0);
							//System.out.println(0);
						}
						else{
							rowContents.add(11, (int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue());
							//System.out.println((int)currentSheetOnFile.getRow(i).getCell(j).getNumericCellValue());
						}
						continue;
					}
					if(j == 18){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rowContents.add(12, "");
							//System.out.println();
						}
						else{
							rowContents.add(12, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
							//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						}
						continue;
					}
					if(j == 19){
						if(currentSheetOnFile.getRow(i).getCell(j) == null){
							rowContents.add(13, "");
							//System.out.println();
						}
						else{
							rowContents.add(13, currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
							//System.out.println(currentSheetOnFile.getRow(i).getCell(j).getStringCellValue());
						}
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
		
		insertIntoPlayerTable();
	}
}
