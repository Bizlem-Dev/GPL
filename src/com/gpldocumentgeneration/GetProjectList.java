package com.gpldocumentgeneration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class GetProjectList
 */
@WebServlet("/GetProjectList")
public class GetProjectList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundle = ResourceBundle.getBundle("config");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProjectList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out= response.getWriter();
		String regionName=request.getParameter("regionName");
		
		try {
			
			if( regionName!=null && regionName!="" ){
				
				String  webServiceJson = bundle.getString("webservicepath");
				JSONObject objAllJson = new JSONObject(webServiceJson);
				
				JSONObject objWebServiceJson = objAllJson.getJSONObject("webservice");
				if(objWebServiceJson.has("webservicetype") & objWebServiceJson.getString("webservicetype").equals("database") & objWebServiceJson.getString("databasetype").equals("postgres")){
					 Connection conn = DocGenerationMethods.getHerokuConnection(objWebServiceJson.getString("host"), 
							objWebServiceJson.getString("databasename"), objWebServiceJson.getString("username"), objWebServiceJson.getString("password"), objWebServiceJson.getString("port"));
					 
					 JSONObject data = DocGenerationMethods.getProject(regionName, conn);
					 out.println(data);
				}
				
				
				
			} // blank check closed 
			
		} catch (Exception e) {
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		doGet(request, response);
	}

}
