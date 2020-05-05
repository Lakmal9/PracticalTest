package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient {
	
	public Connection connect() {
		 Connection con = null;

		 try {
			 
			 Class.forName("com.mysql.jdbc.Driver");
			 con= DriverManager.getConnection("jdbc:mysql://localhost/patient_db","root","");
			 //For testing
			 System.out.print("Successfully connected");
		
		 }catch(Exception e) {
			 
			 System.out.print("not connected");
			 e.printStackTrace();
			 System.out.print(e);
		 }

		 return con;
		}
	
	public String viewPatient(){
		 
		String output = "";
		try{
			Connection con = connect();
			if (con == null){
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr>"
					+"<th>Patient Name</th><th>Patient Address</th>"
					+ "<th>Patient SEX</th><th>Patient DOB</th><th>Patient Mobile</th>"
					+ "<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from p_details";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()){
				String pId = Integer.toString(rs.getInt("pId"));
				String pName = rs.getString("pName");
				String pAddress = rs.getString("pAddress");
				String pSex = rs.getString("pSex");
				String pDob = rs.getString("pDob");
				String pMobile = rs.getString("pMobile");
				
				
				// Add into the html table
				output +=  "<tr><td><input id='hidPatientIDUpdate'"
						+ "name='hidPatientIDUpdate' type='hidden'"
						+ "value='" + pId + "'>" + pName + "</td>";
				output += "<td>" + pAddress + "</td>";
				output += "<td>" + pSex + "</td>";
				output += "<td>" + pDob + "</td>"; 
				output += "<td>" + pMobile + "</td>"; 
				
				// buttons
				output += "<td><input name='btnUpdate' type='button'"
						+ "value='Update'"
						+ "class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button'"
						+ "value='Remove'"
						+ "class='btnRemove btn btn-danger' data-pid='"
						 + pId + "'>" + "</td></tr>"; 
			}	
			con.close();
			// Complete the html table
			output += "</table>";
			
			
		}catch (Exception e){
			output = "Error while reading the Patient Details.";
			System.err.println(e.getMessage());
		}
		
	return output;
	}
	
	public String enterPdetails(String name, String address, String sex, String dob, String mob){
		
		String output = "";
		try {
			
		 Connection con = connect();
		 
		 if (con == null){
			 return "Error while connecting to the database";
		 }
		 
		 // create a prepared statement
		 String query = " INSERT INTO p_details (pName, pAddress, pSex, pDob, pMobile) VALUES (?, ?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 preparedStmt.setString(1, name);
		 preparedStmt.setString(2, address);
		 preparedStmt.setString(3, sex);
		 preparedStmt.setString(4, dob);
		 preparedStmt.setString(5, mob); 
		 
		//execute the statement
		 preparedStmt.execute();
		 con.close();
		 
		//embedded into a json object
		 String newPatients = viewPatient();
		 output = "{\"status\":\"success\", \"data\": \"" + newPatients + "\"}"; 
		 
		 }catch (Exception e){
			 output = "{\"status\":\"error\", \"data\":\"Error while inserting the Patient.\"}"; 
			 System.err.println(e.getMessage());
		 }
		
		return output; 
	}
	
	public String updatePatient(String id, String name, String address, String sex, String dob, String mob){

		String output = "";

		try{
			Connection con = connect();
			if (con == null){
				return "Error while connecting to the database for updating."; 
			}
			// create a prepared statement
			String query = "UPDATE p_details SET pName=?,pAddress=?,pSex=?,pDob=?,pMobile=? WHERE pId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			
			 preparedStmt.setString(1, name);
			 preparedStmt.setString(2, address);
			 preparedStmt.setString(3, sex);
			 preparedStmt.setString(4, dob);
			 preparedStmt.setString(5, mob);
			 preparedStmt.setString(6, id);
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			//embedded into a json object
			 String newPatients = viewPatient();
			 output = "{\"status\":\"success\", \"data\": \"" + newPatients + "\"}"; 
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the Patient.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deletePatient(String pID)
	{
		
		System.out.println("This is my patient ID"+ pID);
		String output = "";
		try{
			
		 Connection con = connect();
		 if (con == null){
			 return "Error while connecting to the database for deleting.";
		 }
		 
		 // create a prepared statement
		 String query = "delete from p_details where pId=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(pID));

		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		
		//embedded into a json object
		 String newPatients = viewPatient();
		 output = "{\"status\":\"success\", \"data\": \"" +newPatients + "\"}";
		 
		}catch (Exception e){
			
			output = "{\"status\":\"error\", \"data\":\"Error while Deleting the Patient.\"}"; 
		 System.err.println(e.getMessage());
		}
		
		return output; 
	}
	
}
