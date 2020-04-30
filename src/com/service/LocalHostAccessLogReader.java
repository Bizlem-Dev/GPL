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

public class LocalHostAccessLogReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			// Creating a regular expression for the records
			final String regex = "^(\\S+) (\\S+) (\\S+) " + "\\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)"
					+ " (\\S+)\\s*(\\S+)?\\s*\" (\\d{3}) (\\S+)";

			final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

			FileInputStream fstream = new FileInputStream(
					"D:\\vivek\\doctiger\\Godrej\\war\\localhost_access_log.2020-01-25.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;

			String newLine = "";
			/* read log line by line */
			while ((strLine = br.readLine()) != null) {
				/* parse strLine to obtain what you want */
				// sb.append(strLine);
				newLine = newLine + strLine + "\n";
			}
			// Calendar cal = Calendar.getInstance();
			// cal.set(Calendar.YEAR, 2020);
			// cal.set(Calendar.MONTH, 0);
			// cal.set(Calendar.DATE, 25);
			// cal.set(Calendar.HOUR_OF_DAY, 12);
			// cal.set(Calendar.MINUTE, 4);
			// cal.set(Calendar.SECOND, 30);

			String strstartDate = "2020-01-25 12:04:12";
			String strendDate = "2020-01-25 13:54:12";
			Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strstartDate);
			// cal.set(Calendar.HOUR_OF_DAY, 13);
			// cal.set(Calendar.MINUTE, 53);
			// cal.set(Calendar.SECOND, 53);

			Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strendDate);
			System.out.println("startdate :: " + startDate);
			System.out.println("endDate :: " + endDate);
			final Matcher matcher = pattern.matcher(newLine);
			while (matcher.find()) {

				String IP = matcher.group(1);
				String urlString = matcher.group(6);
				String DateString = matcher.group(4).split(" ")[0].replaceFirst(":", " ");
				Date date1 = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss").parse(DateString);
				if (date1.getTime() > startDate.getTime() && date1.getTime() < endDate.getTime()) {
					if (urlString.contains("DocumentTracking")) {
					System.out.println("IP :: " + IP);
					System.out.println("urlString :: " + urlString);
					}
				}
			}
			fstream.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static String getStringFromFile(String filePath,String strStartDate,String strEndDate){
		String newLine = "";
		try{
			Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strStartDate);
			String localhostDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
			filePath = filePath + "localhost_access_log."+localhostDate+".txt";
		FileInputStream fstream = new FileInputStream(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;

		/* read log line by line */
		while ((strLine = br.readLine()) != null) {
			/* parse strLine to obtain what you want */
			// sb.append(strLine);
			newLine = newLine + strLine + "\n";
		}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return newLine;
	}

}
