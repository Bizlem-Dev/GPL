package com.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class LocalHostAccessLogReaderNew {

	public static void main(String[] args) {
		String strStartDate = "";
		String strEndDate = "2020-01-25 13:54:12";
		String strFilePath = "D:\\vivek\\doctiger\\Godrej\\war\\";
		String strSearch = "DocumentTracking";
		String multiLine = LocalHostAccessLogReaderNew.getStringFromFile(strFilePath, strStartDate, strEndDate);
		// System.out.println("multiLine :: "+multiLine);
		JSONObject formattedJson = LocalHostAccessLogReaderNew.getFormattedJson(multiLine, strStartDate, strEndDate, strSearch);
		System.out.println(formattedJson);
	}

	public static String getStringFromFile(String filePath, String strStartDate, String strEndDate) {
		String newLine = "";
		try {
			Date startDate = null;
			if(strStartDate != ""){
			 startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStartDate);
			}else{
				startDate = new Date();
			}
			String localhostDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
			filePath = filePath + "localhost_access_log." + localhostDate + ".txt";
			FileInputStream fstream = new FileInputStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;

			/* read log line by line */
			while ((strLine = br.readLine()) != null) {
				/* parse strLine to obtain what you want */
				// sb.append(strLine);
				newLine = newLine + strLine + "\n";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return newLine;
	}

	public static JSONObject getFormattedJson(String multiline, String strStartDate, String strEndDate, String strSearch) {
		JSONObject mainJson = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			final String regex = "^(\\S+) (\\S+) (\\S+) " + "\\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)"
					+ " (\\S+)\\s*(\\S+)?\\s*\" (\\d{3}) (\\S+)";

			final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
			
			// System.out.println("startdate :: " + startDate);
			// System.out.println("endDate :: " + endDate);
			final Matcher matcher = pattern.matcher(multiline);
			while (matcher.find()) {
				String IP = matcher.group(1);
				String urlString = matcher.group(6);
				String DateString = matcher.group(4).split(" ")[0].replaceFirst(":", " ");
				Date logDate = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss").parse(DateString);
				String FormattedDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logDate);
				if(strStartDate != ""){
				Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStartDate);
				Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strEndDate);
				if (logDate.getTime() > startDate.getTime() && logDate.getTime() < endDate.getTime()) {
					if (urlString.contains(strSearch)) {

						if (urlString.indexOf("___") != -1) {
							JSONObject subJson = new JSONObject();
							urlString = urlString.substring(urlString.lastIndexOf("/") + 1, urlString.lastIndexOf("."));
//							System.out.println("IP :: " + IP);
//							System.out.println("FormattedDateString :: " + FormattedDateString);
//							System.out.println("urlString :: " + urlString);
//							System.out.println("funnale name :: " + urlString.split("___")[0]);
							subJson.put("FunnelName", urlString.split("___")[0]);
							subJson.put("SubFunnelName", urlString.split("___")[1]);
							subJson.put("campaignname", urlString.split("___")[2]);
							subJson.put("CampaignId", urlString.split("___")[3]);
							subJson.put("group", urlString.split("___")[4]);
							subJson.put("Created_By", urlString.split("___")[5]);
							subJson.put("SubscriberEmail", urlString.split("___")[6]);
							subJson.put("ip", IP);
							subJson.put("opentime", FormattedDateString);
							arr.put(subJson);
						}

					}
				}
				}else{
					//System.out.println("else");
					if (urlString.contains(strSearch)) {

						if (urlString.indexOf("___") != -1) {
							JSONObject subJson = new JSONObject();
							urlString = urlString.substring(urlString.lastIndexOf("/") + 1, urlString.lastIndexOf("."));
//							System.out.println("IP :: " + IP);
//							System.out.println("FormattedDateString :: " + FormattedDateString);
//							System.out.println("urlString :: " + urlString);
//							System.out.println("funnale name :: " + urlString.split("___")[0]);
							subJson.put("FunnelName", urlString.split("___")[0]);
							subJson.put("SubFunnelName", urlString.split("___")[1]);
							subJson.put("campaignname", urlString.split("___")[2]);
							subJson.put("CampaignId", urlString.split("___")[3]);
							subJson.put("group", urlString.split("___")[4]);
							subJson.put("Created_By", urlString.split("___")[5]);
							subJson.put("SubscriberEmail", urlString.split("___")[6]);
							subJson.put("ip", IP);
							subJson.put("opentime", FormattedDateString);
							arr.put(subJson);
						}

					}
				}
			}
			mainJson.put("data", arr);
		} catch (Exception e) {

		}
		return mainJson;
	}
}
