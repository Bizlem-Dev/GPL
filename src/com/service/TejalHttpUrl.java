package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
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
@WebServlet("/callBlueAlgoUrl")
public class TejalHttpUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResourceBundle bundle = ResourceBundle.getBundle("config");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TejalHttpUrl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().println("GET");
		StringBuilder sb = null;
		try {
			//URL url = new URL("http://bluealgo.com:8087/com.carrotruleangularmongo/GetRuleEngine_Name?project_name=Test_8_Jan&user_name=jobs_bizlem.com&source=leadconverter");
			String projectname = request.getParameter("project_name");
			String user_name = request.getParameter("user_name");
			//String projectname = request.getParameter("project_name");
			String urlForRule = "http://bluealgo.com:8087/com.carrotruleangularmongo/GetRuleEngine_Name?project_name="+projectname+"&user_name="+user_name+"&source=leadconverter";
			URL url = new URL(urlForRule);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "*");
			con.setDoInput(true);
			con.setDoOutput(false);
			/**System.out.println("checkfreetrial :: "+con.getResponseCode());
			BufferedReader in = new BufferedReader(new InputStreamReader(
			con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
			System.out.println("inputLine :: "+inputLine);
			}


			in.close();**/
			sb = new StringBuilder();
			int HttpResult = con.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				// System.out.println("" + sb.toString());
			} else {
				System.out.println(con.getResponseMessage());
			}
			
			}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage().toString());
			}
		response.getWriter().println(sb.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().println("POST");
		StringBuilder builder = new StringBuilder();

		BufferedReader bufferedReaderCampaign = request.getReader();
		String line;
		while ((line = bufferedReaderCampaign.readLine()) != null) {
			builder.append(line + "\n");
		}

		JSONObject allPKFKData = null;
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
		  allPKFKData = PostgresFetchDataPKFKNew.getFkData(conn, allPKFKData, objAllJson.getJSONArray("FK"), schemaTable);
		  allPKFKData = PostgresFetchDataPKFKNew.addImages(allPKFKData);
		  response.getWriter().println("alldata :: " + allPKFKData);
		}
		
		if(allPKFKData.length() != 0){
			//if(reqJson.getString("tempname").toLowerCase().indexOf("agreement") != -1){
			String OPEN_BALC_Sq_Ft__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.open_balc_sq_ft__c");
			String Rate_Per_Unit_Area__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__rate_per_unit_area__c");
			String PropStrength__Total_Basic_Sales_Price__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__total_basic_sales_price__c");
			String EXCLUSIVE_AREA__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.exclusive_area__c");
			String PropStrength__Revised_Agreement_Amount__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__revised_agreement_amount__c");
			String COMMON_AREA__c = allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.common_area__c");
			//numeric = fieldvalue.matches("-?\\d+(\\.\\d+)?");
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
			allPKFKData.put("carpetarea", PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces(OPEN_BALC_Sq_Ft__c + "*" + Rate_Per_Unit_Area__c)));
			allPKFKData.put("exclusivearea", PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces(PropStrength__Total_Basic_Sales_Price__c + "*" + EXCLUSIVE_AREA__c)));
			allPKFKData.put("commonareacharges", PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces("(" + PropStrength__Revised_Agreement_Amount__c + "*" + COMMON_AREA__c + ")" + "/ 100")));
			//response.getWriter().println("temp name :: " +reqJson.getString("tempname"));
			//}
			String jsonString = "{\"templateName\":\"AgreementLetter\",\"AttachtempalteType\":\"TemplateLibrary\",\"esignature\":\"false\",\"twofactor\":\"false\",\"esigntype\":\"\",\"Email\":\"maitreyee.p@godrejproperties.com\",\"group\":\"NoGroup\",\"lgtype\":\"null\",\"multipeDropDown\":[\"1\"],\"Type\":\"Generation\"}";
			JSONObject jsonObj = new JSONObject(jsonString);
			jsonObj.put("templateName", reqJson.getString("tempname"));
			jsonObj.put("data", new JSONArray().put(allPKFKData));
			
			response.getWriter().println("pdf generation :: " + PostgresFetchDataPKFKNew.callPostService(bundle.getString("docGenUrl"), jsonObj.toString()));
		}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
	}

}
