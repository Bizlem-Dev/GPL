package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.service.EvaluateString;

/**
 * Servlet implementation class GodrejNewmail
 */
@WebServlet("/getGPLDocument")
public class GodrejProduction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResourceBundle bundle = ResourceBundle.getBundle("config");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GodrejProduction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().println("GET");
		
		JSONObject allPKFKData = null;
		try{
		String webServiceJson="{\r\n" + 
					"\"webservice\" : {\"host\":\"ec2-23-23-50-231.compute-1.amazonaws.com\",\"databasename\":\"dfchgt5o680aok\",\"username\":\"ubb3mhh56quq28\",\"password\":\"p40b3519da1cda5a8064ad44a89f19aee833c231d97b5996ebd63ce3d059e8bbd\",\"port\":\"5432\",\"webservicetype\":\"database\",\"databasetype\":\"postgres\",\"schema\":\"salesforce\"},\r\n" + 
					"\"PK\":{\"field\":\"sfid\", \"object\":\"Propstrength__Application_Booking__c\",\"value\":\"a10O0000004iFWGIA2\"},\r\n" + 
					"\"FK\":[\r\n" + 
					"{\"field\":\"sfid\", \"object\":\"Propstrength__Projects__c\",\"pkfield\":\"propstrength__project__c\",\"pkobject\":\"Propstrength__Application_Booking__c\"},\r\n" + 
					"{\"field\":\"sfid\", \"object\":\"contact\",\"pkfield\":\"propstrength__primary_customer__c\",\"pkobject\":\"propstrength__application_booking__c\"}\r\n" + 
					"]\r\n" + 
					"}";
		//System.out.println(webServiceJson);
		JSONObject objAllJson = new JSONObject(webServiceJson);
		JSONObject objWebServiceJson = objAllJson.getJSONObject("webservice");
		if(objWebServiceJson.has("webservicetype") & objWebServiceJson.getString("webservicetype").equals("database") & objWebServiceJson.getString("databasetype").equals("postgres")){
		Connection conn = PostgresFetchDataPKFK.getPostGresConnection(objWebServiceJson.getString("host"), 
				objWebServiceJson.getString("databasename"), objWebServiceJson.getString("username"), objWebServiceJson.getString("password"), objWebServiceJson.getString("port"));
		String schema = "";
		String schemaTable = "";
		JSONObject pkObj = objAllJson.getJSONObject("PK");
		if(objWebServiceJson.has("schema")){
			if(!objWebServiceJson.getString("schema").equals("")){
			schema = objWebServiceJson.getString("schema")+"."+pkObj.getString("object");
			schemaTable = objWebServiceJson.getString("schema");
			}else{
				schema = pkObj.getString("object");
			}
		}else{
			schema = pkObj.getString("object");
		}
		  allPKFKData = PostgresFetchDataPKFK.getPKData(conn, pkObj.getString("field"), schema, pkObj.getString("value"),pkObj.getString("object"));
		  allPKFKData = PostgresFetchDataPKFK.getFkData(conn, allPKFKData, objAllJson.getJSONArray("FK"), schemaTable);
		  response.getWriter().println("alldata :: " + allPKFKData);
		}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().println("POST");
		StringBuilder builder = new StringBuilder();

		BufferedReader bufferedReaderCampaign = request.getReader();
		String line;
		while ((line = bufferedReaderCampaign.readLine()) != null) {
			builder.append(line + "\n");
		}

		JSONObject allPKFKData = null;
		int proceed = 0;
		JSONObject responsePdfJson = null;
		try{
			String webServiceJson = bundle.getString("webservicepath");
			/*String webServiceJson = "{\r\n"
					+ "\"webservice\" : {\"host\":\"ec2-3-217-249-217.compute-1.amazonaws.com\",\"databasename\":\"dfjs7psaki4b4p\",\"username\":\"ub9lonr30og61\",\"password\":\"pcd061cdff923f502a780d6028fe03e93640297509284f4d43927bf0d67ca4dde\",\"port\":\"5432\",\"webservicetype\":\"database\",\"databasetype\":\"postgres\",\"schema\":\"salesforce\"},\r\n"
					+ "\"PK\":{\"field\":\"sfid\", \"object\":\"propstrength__application_booking__c\",\"value\":\"\"},\r\n"
					+ "\"FK\":[\r\n"
					+ "{\"field\":\"sfid\", \"object\":\"propstrength__projects__c\",\"pkfield\":\"propstrength__project__c\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
					+ "{\"field\":\"sfid\", \"object\":\"contact\",\"pkfield\":\"propstrength__primary_customer__c\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
					+ "{\"field\":\"sfid\", \"object\":\"propstrength__property__c\",\"pkfield\":\"propstrength__property__c\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
					+ "{\"field\":\"propstrength__application_booking__c\", \"object\":\"propstrength__payment_plan_details__c\",\"pkfield\":\"sfid\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
					+ "{\"field\":\"propstrength__application_booking__c\", \"object\":\"propstrength__other_charges_opted__c\",\"pkfield\":\"sfid\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
					+ "{\"field\":\"propstrength__application__c\", \"object\":\"propstrength__applicant_detail__c\",\"pkfield\":\"sfid\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
					+ "{\"field\":\"project__c\", \"object\":\"bank__c\",\"pkfield\":\"propstrength__project__c\",\"pkobject\":\"propstrength__application_booking__c\"}\r\n"
					+ "]\r\n" + "}";*/
		JSONObject reqJson = new JSONObject(builder.toString());
		JSONObject objAllJson = new JSONObject(webServiceJson);
		JSONObject pkJson = objAllJson.getJSONObject("PK");
		pkJson.put("value", reqJson.getString("applicationbookingid"));
		objAllJson.put("PK", pkJson);
		JSONObject objWebServiceJson = objAllJson.getJSONObject("webservice");
		if(objWebServiceJson.has("webservicetype") & objWebServiceJson.getString("webservicetype").equals("database") & objWebServiceJson.getString("databasetype").equals("postgres")){
		Connection conn = PostgresFetchDataPKFK.getPostGresConnection(objWebServiceJson.getString("host"), 
				objWebServiceJson.getString("databasename"), objWebServiceJson.getString("username"), objWebServiceJson.getString("password"), objWebServiceJson.getString("port"));
		String schema = "";
		String schemaTable = "";
		JSONObject pkObj = objAllJson.getJSONObject("PK");
		if(objWebServiceJson.has("schema")){
			if(!objWebServiceJson.getString("schema").equals("")){
			schema = objWebServiceJson.getString("schema")+"."+pkObj.getString("object");
			schemaTable = objWebServiceJson.getString("schema");
			}else{
				schema = pkObj.getString("object");
			}
		}else{
			schema = pkObj.getString("object");
		}
		  allPKFKData = PostgresFetchDataPKFKNew.getPKData(conn, pkObj.getString("field"), schema, pkObj.getString("value"),pkObj.getString("object"));
		 // System.out.println("after pkdata :: "+allPKFKData);
		  if(reqJson.getString("tempname").toLowerCase().indexOf("agreement") != -1){
//			  allotment_letter_date__c
//				propstrength__allotment_letter_sent__c
			  if(allPKFKData.has(pkObj.getString("object")+".allotment_letter_date__c") && allPKFKData.has(pkObj.getString("object")+".propstrength__allotment_letter_sent__c")){
				  if(allPKFKData.getString(pkObj.getString("object")+".allotment_letter_date__c").equals("") && allPKFKData.getString(pkObj.getString("object")+".propstrength__allotment_letter_sent__c").equals("false")){
					proceed = 1;  
				  }
			  }
		  }
		  if(proceed == 0){
		  allPKFKData = PostgresFetchDataPKFKNew.getFkData(conn, allPKFKData, objAllJson.getJSONArray("FK"), schemaTable);
		//  System.out.println("start");
//		  allPKFKData = PostgresFetchDataPKFKNew.addImages(allPKFKData);
		  allPKFKData = PostgresFetchDataPKFKNew.addImagesNew(allPKFKData);
		  allPKFKData = PostgresFetchDataPKFKNew.getProjectHead(conn, allPKFKData);
	//	  System.out.println("end");
		  }
		  //response.getWriter().println("before alldata :: " + allPKFKData);
		}
		
		if(proceed == 0){
		
		if(allPKFKData.length() != 0){
			//if(reqJson.getString("tempname").toLowerCase().indexOf("agreement") != -1){
			String OPEN_BALC_Sq_Ft__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.open_balc_sq_ft__c");
			String Rate_Per_Unit_Area__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__rate_per_unit_area__c");
			String PropStrength__Total_Basic_Sales_Price__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__total_basic_sales_price__c");
			String EXCLUSIVE_AREA__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.exclusive_area__c");
			String PropStrength__Revised_Agreement_Amount__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__revised_agreement_amount__c");
			String COMMON_AREA__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.common_area__c");
			String CARPET_AREA__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.propstrength__carpet_area__c");
			String CARPET_AREA__cNew = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.carpet_area__c");
			String SUPER_AREA__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.propstrength__super_area__c");
			String FSI_AREA__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.fsi_area_sq_mt__c");
			String CARPET_AREA_CONVERTED__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.carpet_area_converted__c");
			String Appurtenant_Area_sq_mt__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.appurtenant_area_sq_mt__c");
			String Total_Agreement_Amt = allPKFKData.getString("propstrength__application_booking__c.propstrength__total_agreement_amount__c");
			//numeric = fieldvalue.matches("-?\\d+(\\.\\d+)?");
			if(Total_Agreement_Amt.equals("null") || Total_Agreement_Amt.equals("")){
				Total_Agreement_Amt = "1";
			}
			if(OPEN_BALC_Sq_Ft__c.equals("null") || OPEN_BALC_Sq_Ft__c.equals("")){
				OPEN_BALC_Sq_Ft__c = "1";
			}
			if(Rate_Per_Unit_Area__c.equals("null") || Rate_Per_Unit_Area__c.equals("")){
				Rate_Per_Unit_Area__c = "1";
			}
			if(PropStrength__Total_Basic_Sales_Price__c.equals("null") || PropStrength__Total_Basic_Sales_Price__c.equals("")){
				PropStrength__Total_Basic_Sales_Price__c = "1";
			}
			if(EXCLUSIVE_AREA__c.equals("null") || EXCLUSIVE_AREA__c.equals("")){
				EXCLUSIVE_AREA__c = "1";
			}
			if(PropStrength__Revised_Agreement_Amount__c.equals("null") || PropStrength__Revised_Agreement_Amount__c.equals("")){
				PropStrength__Revised_Agreement_Amount__c = "1";
			}
			if(COMMON_AREA__c.equals("null") || COMMON_AREA__c.equals("")){
				COMMON_AREA__c = "1";
			}
			if(CARPET_AREA__cNew.equals("null") || CARPET_AREA__cNew.equals("")){
				CARPET_AREA__cNew = "1";
			}
			if(CARPET_AREA__c.equals("null") || CARPET_AREA__c.equals("")){
				CARPET_AREA__c = "1";
			}
			if(SUPER_AREA__c.equals("null") || SUPER_AREA__c.equals("")){
				SUPER_AREA__c = "1";
			}
			if(FSI_AREA__c.equals("null") || FSI_AREA__c.equals("")){
				FSI_AREA__c = "1";
			}
			if(CARPET_AREA_CONVERTED__c.equals("null") || CARPET_AREA_CONVERTED__c.equals("")){
				CARPET_AREA_CONVERTED__c = "1";
			}
			if(Appurtenant_Area_sq_mt__c.equals("null") || Appurtenant_Area_sq_mt__c.equals("")){
				Appurtenant_Area_sq_mt__c = "1";
			}
//			allPKFKData.put("carpetarea", PostgresFetchDataPKFKNew.getRoundOffData(PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces("(" + OPEN_BALC_Sq_Ft__c + "*" + Rate_Per_Unit_Area__c + ")" + "/ 100"))));
//			allPKFKData.put("exclusivearea", PostgresFetchDataPKFKNew.getRoundOffData(PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces("(" + PropStrength__Total_Basic_Sales_Price__c + "*" + EXCLUSIVE_AREA__c + ")" + "/ 100"))));
//			allPKFKData.put("commonareacharges", PostgresFetchDataPKFKNew.getRoundOffData(PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces("(" + PropStrength__Revised_Agreement_Amount__c + "*" + COMMON_AREA__c + ")" + "/ 100"))));
			
			allPKFKData.put("carpetarea", PostgresFetchDataPKFKNew.getRoundOffData(PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces("(" + Total_Agreement_Amt + "*" + CARPET_AREA__cNew + ")" + "/ 100"))));
			allPKFKData.put("exclusivearea", PostgresFetchDataPKFKNew.getRoundOffData(PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces("(" + Total_Agreement_Amt + "*" + EXCLUSIVE_AREA__c + ")" + "/ 100"))));
			allPKFKData.put("commonareacharges", PostgresFetchDataPKFKNew.getRoundOffData(PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces("(" + Total_Agreement_Amt + "*" + COMMON_AREA__c + ")" + "/ 100"))));
			
			allPKFKData.put("carpetareabang", PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces(CARPET_AREA__c + "*" + Rate_Per_Unit_Area__c)));
			allPKFKData.put("exclusiveareabang", PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces(EXCLUSIVE_AREA__c + "*" + Rate_Per_Unit_Area__c)));
			allPKFKData.put("commonareachargesbang", PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces("(" + SUPER_AREA__c + "*" + Rate_Per_Unit_Area__c + ")" + "-" + "(" + allPKFKData.getString("carpetareabang") + "+" + allPKFKData.getString("exclusiveareabang") + ")")));
			
			allPKFKData.put("carpetareancr", PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces(PropStrength__Total_Basic_Sales_Price__c + "/" + FSI_AREA__c + "*" + CARPET_AREA_CONVERTED__c)));
			allPKFKData.put("exclusiveareancr", PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces(PropStrength__Total_Basic_Sales_Price__c + "/" + FSI_AREA__c + "*" + Appurtenant_Area_sq_mt__c)));
			allPKFKData.put("rateperunitncr", PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces(PropStrength__Total_Basic_Sales_Price__c + "/" + FSI_AREA__c)));
			//response.getWriter().println("temp name :: " +reqJson.getString("tempname"));
			//}
			
			System.out.println("after alldata :: " + allPKFKData);
			
			String jsonString = "{\"templateName\":\"AgreementLetter\",\"AttachtempalteType\":\"TemplateLibrary\",\"esignature\":\"false\",\"twofactor\":\"false\",\"esigntype\":\"\",\"Email\":\"maitreyee.p@godrejproperties.com\",\"group\":\"NoGroup\",\"lgtype\":\"null\",\"multipeDropDown\":[\"1\"],\"Type\":\"Generation\"}";
			JSONObject jsonObj = new JSONObject(jsonString);
			jsonObj.put("templateName", reqJson.getString("tempname"));
			if(reqJson.has("temptype")){
				jsonObj.put("AttachtempalteType", reqJson.getString("temptype"));
			}else{
				jsonObj.put("AttachtempalteType", "TemplateLibrary");	
			}
			jsonObj.put("data", new JSONArray().put(allPKFKData));
			
			String responsePdf = PostgresFetchDataPKFKNew.callPostService(bundle.getString("docGenUrl"), jsonObj.toString());
			
			System.out.println("responsePdf  :: "+responsePdf);
			JSONObject singleJson = new JSONObject(new JSONArray(responsePdf).getJSONObject(0).toString());
			responsePdfJson = new JSONObject(new JSONArray(responsePdf).getJSONObject(0).toString());
			//System.out.println("singleJson 12345 :: "+singleJson);
			if(singleJson.has("documentlink")){
				Date creation_date=new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(singleJson.getString("creation_date"));
				
				String fileName = singleJson.getString("documentlink").substring( singleJson.getString("documentlink").lastIndexOf('/')+1, singleJson.getString("documentlink").length() );
				String oldFilename = fileName.substring(0, fileName.lastIndexOf('.'));
				String strNewFileName = "InstaDoc_"+allPKFKData.getString("propstrength__application_booking__c.project_name__c")+"_"+allPKFKData.getString("propstrength__application_booking__c.propstrength__primary_applicant_name__c")+"_AFS" + "_" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(creation_date);
				String nDocumentLink = PostgresFetchDataPKFKNew.renameFileTo(oldFilename, strNewFileName);
				
//				singleJson.put("pdfurl", singleJson.getString("documentlink"));
				singleJson.put("pdfurl", nDocumentLink);
				if(responsePdfJson.getString("documentlink").indexOf(bundle.getString("checkDomainAndReplace")) != -1){
				//responsePdfJson.put("documentlink", responsePdfJson.getString("documentlink").replace(bundle.getString("checkDomainAndReplace"), bundle.getString("changeDomainAndReplace")));
					responsePdfJson.put("documentlink", nDocumentLink);
				responsePdfJson.put("status", "S");
				responsePdfJson.put("message", "Pdf Generated Successfully");
				}
				//System.out.println(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(date1));
				singleJson.put("Generation_Date", new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(creation_date));
				singleJson.put("Application_Booking_Id", allPKFKData.getString("propstrength__application_booking__c.sfid"));
				singleJson.put("Customer_Name", allPKFKData.getString("propstrength__application_booking__c.propstrength__primary_applicant_name__c"));
				singleJson.put("Project_Name", allPKFKData.getString("propstrength__application_booking__c.project_name__c"));
				singleJson.put("Tower_Name", allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.tower_name__c"));
				singleJson.put("Property_Name", allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.propstrength__property_name__c"));
				singleJson.put("Region", allPKFKData.getString("propstrength__application_booking__c.region__c"));
				singleJson.put("Application_Booking", allPKFKData.getString("propstrength__application_booking__c.name"));
				singleJson.put("Floor", allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.floor_name__c"));
				singleJson.put("templatename", reqJson.getString("tempname"));
				if(reqJson.has("useremail")){
				singleJson.put("useremail", reqJson.getString("useremail"));
				}else{
					singleJson.put("useremail", "");
				}
				if(reqJson.has("username")){
				singleJson.put("username", reqJson.getString("username"));
				}else{
					singleJson.put("username", "");
				}
				singleJson.put("bizlemId", singleJson.getString("created_by"));
				System.out.println("singleJson  :: "+singleJson);
				new Thread(new Runnable() {
				     @Override
				     public void run() {
				    	 System.out.println("response from mongodb :: "+PostgresFetchDataPKFKNew.callPostService(bundle.getString("saveGPLData"), singleJson.toString()));
				     }
				}).start();
			}
			
			//response.getWriter().println("pdf generation :: " + responsePdf);
			response.getWriter().println(new JSONArray().put(responsePdfJson));
		}else{
			System.out.println("in else");
			response.getWriter().println(new JSONArray());
		}
		
		}else if(proceed == 1){
			responsePdfJson = new JSONObject();
			responsePdfJson.put("status", "E");
			responsePdfJson.put("message", "Agreement cannot be generated for this booking, as Allotment Letter is not issued");
			response.getWriter().println(new JSONArray().put(responsePdfJson));
		}
		
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
	}

}
