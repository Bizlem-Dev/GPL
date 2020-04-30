package com.gpldocumentgeneration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DocGenerationMethods {
	
	static ResourceBundle bundle = ResourceBundle.getBundle("config");
	
	
	public static void main(String[] args) {
		String  webServiceJson = bundle.getString("webservicepath");
		try {
			JSONObject objAllJson = new JSONObject(webServiceJson);
			
			JSONObject objWebServiceJson = objAllJson.getJSONObject("webservice");
			if(objWebServiceJson.has("webservicetype") & objWebServiceJson.getString("webservicetype").equals("database") & objWebServiceJson.getString("databasetype").equals("postgres")){
				 Connection conn = getHerokuConnection(objWebServiceJson.getString("host"), 
						objWebServiceJson.getString("databasename"), objWebServiceJson.getString("username"), objWebServiceJson.getString("password"), objWebServiceJson.getString("port"));
				 
//				 JSONObject data = getProject("Pune", conn);
				 JSONObject data = getApplnBooking("Forest Grove at Godrej Park Greens", conn);
				 System.out.println(data);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static Connection getHerokuConnection( String host,String databasename,String username,String password,String port ){
		Connection conn = null;
		try {
			
			conn = DriverManager.getConnection(
					"jdbc:postgresql://"+host+":"+port+"/"+databasename, username,
					password);
			 System.out.println(conn);
			
		} catch (Exception e) {
			System.out.println("getPostGresConnection()_Generation  :: "+e.getMessage());
		}
		return conn;
		
	}
	
	public static JSONObject getProject(String regionName,Connection conn){
		JSONObject finalObj=new JSONObject();
		try{
//			project = new JSONObject();
			regionName = "'"+regionName+"'";
//			String SQL_SELECT = "select * from salesforce.propstrength__projects__c where region__c ="+regionName;
			String SQL_SELECT = "select DISTINCT name from salesforce.propstrength__projects__c where region__c ="+regionName;
//			String SQL_SELECT = "select * from salesforce.propstrength__projects__c"; //region__c
			PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (conn != null) {
				System.out.println("Connected to the database!");
				
				ResultSetMetaData meta = resultSet.getMetaData();
				//System.out.println(meta);
				 int columnCount = meta.getColumnCount();
//				 System.out.println("columnCount:: "+columnCount);
				 
				 JSONArray arrayData=new JSONArray();
				while (resultSet.next()) {
					//System.out.println("inside");
					
					 JSONObject project = new JSONObject();
					 for (int column = 1; column <= columnCount; column++) 
					{
						 Object value = resultSet.getObject(column);
						 String columnName = meta.getColumnName(column);
						 
						
						 if (value != null) {
								if(!project.has(columnName)){
								  project.put(columnName, value.toString());
								}
								//System.out.println("column :: " + columnName + " : value :: " + value.toString());

							} else {
								if(!project.has(columnName)){
								    project.put(columnName, "");
								}
								//System.out.println("column :: " + columnName + " : value :: ");
							}
						 
						 arrayData.put(project);
						
					}
					// System.out.println(applnBooking);
				}
				
				finalObj.put("projectList", arrayData);

			} else {
				System.out.println("getProject()  :: Failed to make connection!");
			}

		}catch(Exception e){
			System.out.println("getProject()  :: "+e.getMessage());
		}
		return finalObj;
	}
	
	public static JSONObject getApplnBooking(String sfId,Connection conn){
		JSONObject finalObj=new JSONObject();
		
		try{
			
			sfId = "'"+sfId+"'";
			String SQL_SELECT = "select DISTINCT sfid,name from salesforce.propstrength__application_booking__c where project_name__c ="+sfId;
			PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (conn != null) {
				System.out.println("Connected to the database!");
				ResultSetMetaData meta = resultSet.getMetaData();
				 int columnCount = meta.getColumnCount();
				 
				 JSONArray arrayData=new JSONArray();
				while (resultSet.next()) {
					
					JSONObject applnBooking = new JSONObject();
					 for (int column = 1; column <= columnCount; column++) 
					{
						Object value = resultSet.getObject(column);
						String columnName = meta.getColumnName(column);

						if (value != null) {
							applnBooking.put(columnName, value.toString());
							System.out.println("column :: " + columnName + " : value :: " + value.toString());

						} else {
							applnBooking.put(columnName, "");
							//System.out.println("column :: " + columnName + " : value :: ");
						}
						
						arrayData.put(applnBooking);
						
					}
					// System.out.println(applnBooking);
				}
				
				finalObj.put("applicationBookingIdList", arrayData);

			} else {
				System.out.println("getApplnBooking()  :: Failed to make connection!");
			}

		}catch(Exception e){
			System.out.println("getApplnBooking()  :: "+e.getMessage());
		}
		return finalObj;
	}
	
	

}
