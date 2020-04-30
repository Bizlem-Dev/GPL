package com.service;

import java.io.BufferedReader;
import java.io.IOException;
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


@WebServlet("/callcommunicationservice")
public class GodrejCommunicationServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResourceBundle bundle = ResourceBundle.getBundle("config");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GodrejCommunicationServer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println("GET");
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
		
		response.getWriter().println("Receive JSON :: "+ builder.toString());
	}

}
