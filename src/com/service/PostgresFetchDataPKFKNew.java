package com.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class PostgresFetchDataPKFKNew {

	static ResourceBundle bundle = ResourceBundle.getBundle("config");
	final static Logger logger = Logger.getLogger(PostgresFetchDataPKFKNew.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject allPKFKData = null;
		try {
			//String PropStrength__Total_Basic_Sales_Price__c = "53"
			//System.out.println(PostgresFetchDataPKFKNew.getRoundOffData(PostgresFetchDataPKFKNew.reformatExponentialValue(EvaluateString.findValueInBraces(PropStrength__Total_Basic_Sales_Price__c + "*" + EXCLUSIVE_AREA__c))));
			String sDate1="Fri Apr 03 19:29:01 IST 2020";
			Date date1=new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(sDate1);
			//System.out.println(date1);
			//System.out.println(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(date1));
//			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1));
			String url = "https://gpl.bluealgo.com:8083/Attachment/1d167537-46e5-4a64-8678-2a8f62a3b7cd.pdf";
			String fileName = url.substring( url.lastIndexOf('/')+1, url.length() );

			String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
			//System.out.println("fileNameWithoutExtn  :: "+fileNameWithoutExtn);
//			String nS = "23658600.71";
			String valueS = "23658600.";
//			valueS = valueS.indexOf(".") < 0 ? valueS
//					: valueS.replaceAll(".0*$", "").replaceAll("\\.$", "");
			//System.out.println(valueS.split("\\.").length);
			if(valueS.indexOf(".") != -1 && valueS.split("\\.").length > 1 && valueS.split("\\.")[1].length() == 1 && valueS.split("\\.")[1].equals("0")){
				valueS = valueS.replaceAll("\\.0", "");
				System.out.println(valueS);
			}else if(valueS.indexOf(".") != -1 && valueS.split("\\.").length == 1){
				valueS = valueS.replaceAll("\\.", "");
			}
			
////			System.out.println("nS :: " + Math.round(Double.parseDouble(nS)));
//			String dS = "14.00";
//			Double d = Double.parseDouble(dS);
//			if (d % 1 == 0) {
//				System.out.println("if");
//				DecimalFormat format = new DecimalFormat("0.#");
//				System.out.println(format.format(d));
//			} else {
//				System.out.println("else");
//			}
			// String s =
			// PostgresFetchDataPKFKNew.callGetService("https://d4u1.gplapps.com:8085/HerokuCommonAPI/getAttachmentDocuments?attachmentId=00P2s000000emDDEAY");
			// JSONObject attachmentBase64ResponseObj = new JSONObject(s);
			// if(attachmentBase64ResponseObj.has("attachments") &&
			// attachmentBase64ResponseObj.getJSONArray("attachments").length()
			// > 0){
			// JSONObject insideJsonBase64 =
			// attachmentBase64ResponseObj.getJSONArray("attachments").getJSONObject(0);
			// ReadDirectory.getGPLImageUrlNew(bundle.getString("propertyImagePath"),"3456.jpg",insideJsonBase64.getString("attachmentBody"),bundle.getString("propertyImageURL"));
			// }
			// String webServiceJson="{\r\n" +
			// "\"webservice\" :
			// {\"host\":\"ec2-23-23-50-231.compute-1.amazonaws.com\",\"databasename\":\"dfchgt5o680aok\",\"username\":\"ubb3mhh56quq28\",\"password\":\"p40b3519da1cda5a8064ad44a89f19aee833c231d97b5996ebd63ce3d059e8bbd\",\"port\":\"5432\",\"webservicetype\":\"database\",\"databasetype\":\"postgres\",\"schema\":\"salesforce\"},\r\n"
			// +
			// "\"PK\":{\"field\":\"sfid\",
			// \"object\":\"propstrength__application_booking__c\",\"value\":\"a10O0000004iFWGIA2\"},\r\n"
			// +
			// "\"FK\":[\r\n" +
			// "{\"field\":\"sfid\",
			// \"object\":\"propstrength__projects__c\",\"pkfield\":\"propstrength__project__c\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
			// +
			// "{\"field\":\"sfid\",
			// \"object\":\"contact\",\"pkfield\":\"propstrength__primary_customer__c\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
			// +
			// "{\"field\":\"sfid\",
			// \"object\":\"propstrength__property__c\",\"pkfield\":\"propstrength__property__c\",\"pkobject\":\"propstrength__application_booking__c\"}\r\n"
			// +
			// "]\r\n" +
			// "}";
			String webServiceJson = bundle.getString("webservicepath");
			/*
			 * String webServiceJson = "{\r\n" +
			 * "\"webservice\" : {\"host\":\"ec2-3-217-249-217.compute-1.amazonaws.com\",\"databasename\":\"dfjs7psaki4b4p\",\"username\":\"ub9lonr30og61\",\"password\":\"pcd061cdff923f502a780d6028fe03e93640297509284f4d43927bf0d67ca4dde\",\"port\":\"5432\",\"webservicetype\":\"database\",\"databasetype\":\"postgres\",\"schema\":\"salesforce\"},\r\n"
			 * +
			 * "\"PK\":{\"field\":\"sfid\", \"object\":\"propstrength__application_booking__c\",\"value\":\"a0m2s0000004CZAAA2\"},\r\n"
			 * + "\"FK\":[\r\n" +
			 * "{\"field\":\"sfid\", \"object\":\"propstrength__projects__c\",\"pkfield\":\"propstrength__project__c\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
			 * +
			 * "{\"field\":\"sfid\", \"object\":\"contact\",\"pkfield\":\"propstrength__primary_customer__c\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
			 * +
			 * "{\"field\":\"sfid\", \"object\":\"propstrength__property__c\",\"pkfield\":\"propstrength__property__c\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
			 * +
			 * "{\"field\":\"propstrength__application_booking__c\", \"object\":\"propstrength__payment_plan_details__c\",\"pkfield\":\"sfid\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
			 * +
			 * "{\"field\":\"propstrength__application_booking__c\", \"object\":\"propstrength__other_charges_opted__c\",\"pkfield\":\"sfid\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
			 * +
			 * "{\"field\":\"propstrength__application__c\", \"object\":\"propstrength__applicant_detail__c\",\"pkfield\":\"sfid\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n"
			 * +
			 * "{\"field\":\"project__c\", \"object\":\"bank__c\",\"pkfield\":\"propstrength__project__c\",\"pkobject\":\"propstrength__application_booking__c\"}\r\n"
			 * + "]\r\n" + "}";
			 */
			// System.out.println(webServiceJson);
			// {"field":"propstrength__application_booking__c","object":"propstrength__payment_plan_details__c","pkfield":"sfid","pkobject":"PropStrength__Application_Booking__c"},
			JSONObject objAllJson = new JSONObject(webServiceJson);
			JSONObject objWebServiceJson = objAllJson.getJSONObject("webservice");
			if (objWebServiceJson.has("webservicetype")
					& objWebServiceJson.getString("webservicetype").equals("database")
					& objWebServiceJson.getString("databasetype").equals("postgres")) {
				Connection conn = PostgresFetchDataPKFKNew.getPostGresConnection(objWebServiceJson.getString("host"),
						objWebServiceJson.getString("databasename"), objWebServiceJson.getString("username"),
						objWebServiceJson.getString("password"), objWebServiceJson.getString("port"));
				// PostgresFetchDataPKFKNew.getApplicationBookingId(conn);

				String schema = "";
				String schemaTable = "";
				JSONObject pkObj = objAllJson.getJSONObject("PK");
				if (objWebServiceJson.has("schema")) {
					if (!objWebServiceJson.getString("schema").equals("")) {
						schema = objWebServiceJson.getString("schema") + "." + pkObj.getString("object");
						schemaTable = objWebServiceJson.getString("schema");
					} else {
						schema = pkObj.getString("object");
					}
				} else {
					schema = pkObj.getString("object");
				}
				allPKFKData = PostgresFetchDataPKFKNew.getPKData(conn, pkObj.getString("field"), schema,
						pkObj.getString("value"), pkObj.getString("object"));
				System.out.println("pkdata :: " + allPKFKData);
				allPKFKData = PostgresFetchDataPKFKNew.getFkData(conn, allPKFKData, objAllJson.getJSONArray("FK"),
						schemaTable);
				allPKFKData = PostgresFetchDataPKFKNew.getProjectHead(conn, allPKFKData);
				// allPKFKData =
				// PostgresFetchDataPKFKNew.addImages(allPKFKData);
				System.out.println("alldata :: " + allPKFKData);
				logger.info("alldata :: " + allPKFKData);

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error  :: " + e.getMessage());
			logger.info("error  :: " + e.getMessage());
		}
	}

	public static Connection getPostGresConnection(String host, String databasename, String username, String password,
			String port) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + databasename, username,
					password);
//			 System.out.println(conn);
		} catch (Exception e) {
			System.out.println("getPostGresConnection()  :: " + e.getMessage());
		}
		return conn;
	}

	public static JSONObject getApplicationBookingId(Connection conn) {
		JSONObject pkData = new JSONObject();
		try {
			// String SQL_SELECT = "select * from
			// salesforce.propstrength__applicant_detail__c where
			// propstrength__application__c = 'a0m2s0000004CZAAA2'";
			// String SQL_SELECT = "select * from
			// salesforce.propstrength__other_charges_opted__c where
			// propstrength__application_booking__c = 'a0m2s0000004CZAAA2'";
			String SQL_SELECT = "select * from salesforce.user where sfid = '00590000001uo58AAA'";
			// String SQL_SELECT = "select * from
			// salesforce.propstrength__application_booking__c where sfid like
			// 'a0m6F00000JkPcn%'";
			PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (conn != null) {
				// System.out.println("Connected to the database!");
				ResultSetMetaData meta = resultSet.getMetaData();
				int columnCount = meta.getColumnCount();

				while (resultSet.next()) {
					for (int column = 1; column <= columnCount; column++) {
						Object value = resultSet.getObject(column);
						String columnName = meta.getColumnName(column);

						if (value != null) {
							if (!pkData.has(columnName)) {
								// pkData.put(object+"."+columnName,
								// value.toString());
							}
							// if(columnName.equals("sfid")){
							System.out.println("column :: " + columnName + " : value :: " + value.toString());
							// }

						} else {
							if (!pkData.has(columnName)) {
								// pkData.put(object+"."+columnName, "");
							}
							// System.out.println("column :: " + columnName + "
							// : value :: ");
						}
					}
					// System.out.println(applnBooking);
				}
				// System.out.println("getPKData :: " + pkData);

			} else {
				System.out.println("getPKData()  :: Failed to make connection!");
			}

		} catch (Exception e) {
			System.out.println("getPKData()  :: " + e.getMessage());
		}
		return pkData;
	}

	public static JSONObject getPKData(Connection conn, String primaryKey, String primaryObject, String primaryValue,
			String object) {
		JSONObject pkData = new JSONObject();
		try {
			// System.out.println(primaryObject);
			// like // 'a0m6F00000JkPcn%'"
			// String SQL_SELECT = "select * from " + primaryObject + " where "
			// + primaryKey + " ='" + primaryValue + "'";
			String SQL_SELECT = "select * from " + primaryObject + " where " + primaryKey + " like '" + primaryValue
					+ "%' ";
			// System.out.println("SQL_SELECT :: "+SQL_SELECT);
			PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (conn != null) {
				// System.out.println("Connected to the database!");
//				System.out.println("PKData===========");
				ResultSetMetaData meta = resultSet.getMetaData();
				int columnCount = meta.getColumnCount();

				while (resultSet.next()) {
//					System.out.println("region  :: "+resultSet.getString("region__c"));
					for (int column = 1; column <= columnCount; column++) {
						Object value = resultSet.getObject(column);
						String columnName = meta.getColumnName(column);
						
//						System.out.println(object + "." + columnName);
						if (value != null) {
							if (!pkData.has(columnName)) {
								String valueS = value.toString();
								if(valueS.indexOf(".") != -1 && valueS.split("\\.").length > 1 && valueS.split("\\.")[1].length() == 1 && valueS.split("\\.")[1].equals("0")){
									valueS = valueS.replaceAll("\\.0", "");
									//System.out.println(valueS);
								}
								if(resultSet.getString("region__c").toLowerCase().equals("pune") && (columnName.equals("propstrength__revised_agreement_amount__c") || columnName.equals("allotment_amount_required__c"))){
									//System.out.println("if :: "+columnName);
									valueS = valueS.indexOf(".") < 0 ? valueS
											: valueS.replaceAll("0*$", "").replaceAll("\\.$", "");
									valueS = PostgresFetchDataPKFKNew.getRoundOffData(valueS);
								}
								if(resultSet.getString("region__c").toLowerCase().equals("bangalore") && columnName.equals("propstrength__cp_number_of_parking_purchased__c")){
									valueS = Currency.convertToWord(valueS);
								}
								
								pkData.put(object + "." + columnName, valueS);
							}
							// System.out.println("column :: " + columnName + "
							// : value :: " + value.toString());

						} else {
							if (!pkData.has(columnName)) {
								pkData.put(object + "." + columnName, "");
							}
							// System.out.println("column :: " + columnName + "
							// : value :: ");
						}
					}
					// System.out.println(applnBooking);
				}
				// System.out.println("getPKData :: " + pkData);

			} else {
				System.out.println("getPKData()  :: Failed to make connection!");
			}

		} catch (Exception e) {
			System.out.println("getPKData()  :: " + e.getMessage());
		}
		return pkData;
	}

	public static JSONObject getFkData(Connection conn, JSONObject allPKFKData, JSONArray fkKeySchema,
			String schemaTable) {

		try {
			if (conn != null) {
				for (int i = 0; i < fkKeySchema.length(); i++) {
					JSONObject fkObject = fkKeySchema.getJSONObject(i);
					if (fkObject.getString("object").equals("propstrength__payment_plan_details__c")) {
						allPKFKData = PostgresFetchDataPKFKNew.getPaymentDetailsMain(conn, allPKFKData, fkObject,
								schemaTable);
					} else if (fkObject.getString("object").equals("propstrength__other_charges_opted__c")) {
						allPKFKData = PostgresFetchDataPKFKNew.getPaymentOtherChargesOpted(conn, allPKFKData, fkObject,
								schemaTable);
					} else if (fkObject.getString("object").equals("propstrength__applicant_detail__c")) {
						allPKFKData = PostgresFetchDataPKFKNew.getApplicantDetail(conn, allPKFKData, fkObject,
								schemaTable);
					} else {
						String fkSchemaObject = "";
						if (!schemaTable.equals("")) {
							fkSchemaObject = schemaTable + "." + fkObject.getString("object");
						} else {
							fkSchemaObject = fkObject.getString("object");
						}
//						 System.out.println(fkSchemaObject);
						if (allPKFKData.has(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))) {
							// System.out.println(allPKFKData.get(fkObject.getString("pkfield")).toString());
							String SQL_SELECT = "select * from " + fkSchemaObject + " where "
									+ fkObject.getString("field") + " ='"
									+ allPKFKData
											.get(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))
											.toString()
									+ "'";
//							 System.out.println(SQL_SELECT);
							PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
							ResultSet resultSet = preparedStatement.executeQuery();

							// System.out.println("Connected to the database!");
							ResultSetMetaData meta = resultSet.getMetaData();
							int columnCount = meta.getColumnCount();
//							System.out.println("=="+fkObject.getString("object"));
							while (resultSet.next()) {
								for (int column = 1; column <= columnCount; column++) {
									Object value = resultSet.getObject(column);
									String columnName = meta.getColumnName(column);
//									System.out.println(fkObject.getString("pkobject") + "." + fkObject.getString("object") + "." + columnName);
									
									if (value != null) {
										if (!allPKFKData.has(columnName)) {
											String valueS = value.toString();
											valueS = valueS.indexOf(".") < 0 ? valueS
													: valueS.replaceAll("0*$", "").replaceAll("\\.$", "");
											allPKFKData.put(fkObject.getString("pkobject") + "."
													+ fkObject.getString("object") + "." + columnName, valueS);
										}
//										 System.out.println(fkObject.getString("object")  + " :: column :: " +
//										 columnName + " : value :: " +
//										 value.toString());

									} else {
										if (!allPKFKData.has(columnName)) {
											allPKFKData.put(fkObject.getString("pkobject") + "."
													+ fkObject.getString("object") + "." + columnName, "");
										}
										// System.out.println("column :: " +
										// columnName + " : value :: ");
									}
								}
								// System.out.println(applnBooking);
							}
						}

					}

				}
				// System.out.println("getFkData() :: " + allPKFKData);
			} else {
				System.out.println("getFkData()  :: Failed to make connection!");
			}
		} catch (Exception e) {
			System.out.println("getFkData()  :: " + e.getMessage());
		}
		return allPKFKData;
	}

	public static JSONObject getPaymentDetails(Connection conn, JSONObject allPKFKData, JSONObject fkObject,
			String schemaTable) {
		try {
			if (conn != null) {
				String fkSchemaObject = "";
				if (!schemaTable.equals("")) {
					fkSchemaObject = schemaTable + "." + fkObject.getString("object");
				} else {
					fkSchemaObject = fkObject.getString("object");
				}
				// System.out.println(fkSchemaObject);
				if (allPKFKData.has(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))) {
					// System.out.println(allPKFKData.get(fkObject.getString("pkfield")).toString());
					String SQL_SELECT = "select * from " + fkSchemaObject + " where " + fkObject.getString("field")
							+ " ='"
							+ allPKFKData.get(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))
									.toString()
							+ "'";
					// System.out.println(SQL_SELECT);
					PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT + "order by name");
					ResultSet resultSet = preparedStatement.executeQuery();

					JSONArray arr = new JSONArray();
					int iCount = 0;
					float amountpercent = 0.0f;
					double totalamount = 0.0d;
					while (resultSet.next()) {
						String strAmtPercent = "0";
						String strAmtDue = "0";
						String strMileStone = "null";
						Object propstrength__amount_percent__c = resultSet.getObject("propstrength__amount_percent__c");
						Object propstrength__milestone_name__c = resultSet.getObject("milestone_name__c");
						// Object propstrength__amount_due_plus_tax__c =
						// resultSet.getObject("propstrength__amount_due_plus_tax__c");
						Object propstrength__amount_due_plus_tax__c = resultSet
								.getObject("propstrength__amount_due__c");
						if (propstrength__amount_percent__c != null) {

							strAmtPercent = propstrength__amount_percent__c.toString();
							strAmtPercent = strAmtPercent.indexOf(".") < 0 ? strAmtPercent
									: strAmtPercent.replaceAll("0*$", "").replaceAll("\\.$", "");
						}
						if (propstrength__milestone_name__c != null) {
							strMileStone = propstrength__milestone_name__c.toString();
						}
						if (propstrength__amount_due_plus_tax__c != null) {
							strAmtDue = propstrength__amount_due_plus_tax__c.toString();
							strAmtDue = strAmtDue.indexOf(".") < 0 ? strAmtDue
									: strAmtDue.replaceAll("0*$", "").replaceAll("\\.$", "");
							if(allPKFKData.getString("propstrength__application_booking__c.region__c").toLowerCase().equals("pune")){
								strAmtDue = getRoundOffData(strAmtDue);
							}else{
								strAmtDue = String.valueOf(new BigDecimal(strAmtDue).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
						}
						// System.out.println("milestone_name__c ::
						// "+resultSet.getString("milestone_name__c"));
						// System.out.println("milestone_name__c ::
						// "+resultSet.getString("id"));
						// System.out.println("milestone_name__c ::
						// "+resultSet.getString("id"));
						// System.out.println("milestone_name__c ::
						// "+resultSet.getString("id"));
						// System.out.println("milestone_name__c ::
						// "+resultSet.getString("id"));
						// System.out.println("milestone_name__c ::
						// "+resultSet.getString("id"));
						if (strMileStone.toLowerCase().indexOf("dummy") == -1) {
							JSONObject subJson = new JSONObject();
							subJson.put("<<msname>>", strMileStone);
							subJson.put("<<amtpertage>>", strAmtPercent);
							subJson.put("<<ttlamtdue>>",strAmtDue);
							subJson.put("<<srno>>", String.valueOf(iCount + 1));
							arr.put(subJson);
							iCount++;
							amountpercent = amountpercent + Float.parseFloat(strAmtPercent);
							totalamount = totalamount + Double.parseDouble(strAmtDue);
						}

					}
					String totalAmtDue = "";
					if(allPKFKData.getString("propstrength__application_booking__c.region__c").toLowerCase().equals("pune")){
						//totalAmtDue = getRoundOffData(String.valueOf(new BigDecimal(totalamount).setScale(2, BigDecimal.ROUND_HALF_UP)));
						if(allPKFKData.has("propstrength__application_booking__c.propstrength__revised_agreement_amount__c")){
							totalAmtDue = allPKFKData.getString("propstrength__application_booking__c.propstrength__revised_agreement_amount__c");
						}else{
							totalAmtDue = getRoundOffData(String.valueOf(new BigDecimal(totalamount).setScale(2, BigDecimal.ROUND_HALF_UP)));
						}
					}else{
						totalAmtDue = String.valueOf(new BigDecimal(totalamount).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					if(!allPKFKData.getString("propstrength__application_booking__c.region__c").toLowerCase().equals("bangalore")){
						JSONObject subJson1 = new JSONObject();
						subJson1.put("<<msname>>", "Grand Total");
						subJson1.put("<<amtpertage>>", amountpercent);
						subJson1.put("<<ttlamtdue>>",totalAmtDue);
						subJson1.put("<<srno>>", "");
						arr.put(subJson1);
					}
					allPKFKData.put("tablearray", new JSONObject().put("milestone", arr));

				}

				// System.out.println("getFkData() :: " + allPKFKData);
			} else {
				System.out.println("getFkData()  :: Failed to make connection!");
			}
		} catch (Exception e) {
			System.out.println("getFkData()  :: " + e.getMessage());
		}
		return allPKFKData;

	}

	public static JSONObject getPaymentDetailsMain(Connection conn, JSONObject allPKFKData, JSONObject fkObject,
			String schemaTable) {
		try {
			String strRegion = allPKFKData.getString("propstrength__application_booking__c.region__c");
			if (strRegion.toLowerCase().equals("noida")) {
				allPKFKData = getPaymentDetailsNCR(conn, allPKFKData, fkObject, schemaTable);
			} else {
				allPKFKData = getPaymentDetails(conn, allPKFKData, fkObject, schemaTable);
			}
		} catch (Exception e) {
			System.out.println("getPaymentDetailsMain  :: error :: " + e.getMessage());
		}
		return allPKFKData;
	}

	public static JSONObject getPaymentDetailsNCR(Connection conn, JSONObject allPKFKData, JSONObject fkObject,
			String schemaTable) {
		try {
			if (conn != null) {
				String fkSchemaObject = "";
				if (!schemaTable.equals("")) {
					fkSchemaObject = schemaTable + "." + fkObject.getString("object");
				} else {
					fkSchemaObject = fkObject.getString("object");
				}
				// System.out.println(fkSchemaObject);
				if (allPKFKData.has(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))) {
					// System.out.println(allPKFKData.get(fkObject.getString("pkfield")).toString());
					String SQL_SELECT = "select * from " + fkSchemaObject + " where " + fkObject.getString("field")
							+ " ='"
							+ allPKFKData.get(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))
									.toString()
							+ "'";
					// System.out.println(SQL_SELECT);
					PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT + "order by name",
							ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					ResultSet resultSet = preparedStatement.executeQuery();

					JSONArray arr = new JSONArray();
					int iCount = 0;
					// float amountpercent = 0.0f;
					// double totalamount = 0.0d;
					while (resultSet.next()) {
						// System.out.println("row :: "+resultSet.getRow());
						String strAmtPercent = "0";
						String strAmtDue = "0";
						String strMileStone = "null";
						Object propstrength__amount_percent__c = resultSet.getObject("propstrength__amount_percent__c");
						Object propstrength__milestone_name__c = resultSet.getObject("milestone_name__c");
						// Object propstrength__amount_due_plus_tax__c =
						// resultSet.getObject("propstrength__amount_due_plus_tax__c");
						// Object propstrength__amount_due_plus_tax__c =
						// resultSet.getObject("propstrength__amount_due__c");
						Object propstrength__amount_due_plus_tax__c = resultSet
								.getObject("propstrength__amount_due_plus_tax__c");
						if (propstrength__amount_percent__c != null) {
							strAmtPercent = PostgresFetchDataPKFKNew
									.reformatExponentialValue(propstrength__amount_percent__c.toString());
						}
						if (propstrength__milestone_name__c != null) {
							strMileStone = propstrength__milestone_name__c.toString();
						}
						if (propstrength__amount_due_plus_tax__c != null) {
							strAmtDue = propstrength__amount_due_plus_tax__c.toString();
						}

						if (strMileStone.toLowerCase().indexOf("dummy") == -1) {
							JSONObject subJson = new JSONObject();

							subJson.put("<<msname>>", strMileStone);
							if (strAmtPercent.equals("0") || strAmtPercent.equals("") || strAmtPercent.equals(" ")) {
								subJson.put("<<msdescription>>", strAmtDue);
							} else {
								System.out.println("iCount  :: " + iCount);
								if (resultSet.isLast()) {
									subJson.put("<<msdescription>>", strAmtPercent + "% of COP + Taxes + AMC");
								} else if (resultSet.getRow() == 2) {
									subJson.put("<<msdescription>>", strAmtPercent + "% of COP + Taxes - AM");
								} else {
									subJson.put("<<msdescription>>", strAmtPercent + "% of COP + Taxes");
								}

							}
							// subJson.put("<<ttlamtdue>>",String.valueOf(new
							// BigDecimal(strAmtDue).setScale(2,
							// BigDecimal.ROUND_HALF_UP)));
							// subJson.put("<<srno>>", String.valueOf(iCount +
							// 1));
							arr.put(subJson);
							iCount++;
							// amountpercent = amountpercent +
							// Float.parseFloat(strAmtPercent);
							// totalamount = totalamount +
							// Double.parseDouble(String.valueOf(new
							// BigDecimal(strAmtDue).setScale(2,
							// BigDecimal.ROUND_HALF_UP)));
						}

					}
					// JSONObject subJson1 = new JSONObject();
					// subJson1.put("<<msname>>", "Grand Total");
					// subJson1.put("<<amtpertage>>", amountpercent);
					// subJson1.put("<<ttlamtdue>>",
					// String.valueOf(new BigDecimal(totalamount).setScale(2,
					// BigDecimal.ROUND_HALF_UP)));
					// subJson1.put("<<srno>>", "");
					// arr.put(subJson1);
					allPKFKData.put("tablearray", new JSONObject().put("milestone", arr));

				}

				// System.out.println("getFkData() :: " + allPKFKData);
			} else {
				System.out.println("getFkData()  :: Failed to make connection!");
			}
		} catch (Exception e) {
			System.out.println("getFkData()  :: " + e.getMessage());
		}
		return allPKFKData;

	}

	public static JSONObject getPaymentOtherChargesOpted(Connection conn, JSONObject allPKFKData, JSONObject fkObject,
			String schemaTable) {
		try {
			if (conn != null) {
				String fkSchemaObject = "";
				if (!schemaTable.equals("")) {
					fkSchemaObject = schemaTable + "." + fkObject.getString("object");
				} else {
					fkSchemaObject = fkObject.getString("object");
				}
				// System.out.println(fkSchemaObject);
				if (allPKFKData.has(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))) {
					// System.out.println(allPKFKData.get(fkObject.getString("pkfield")).toString());
					String SQL_SELECT = "select * from " + fkSchemaObject + " where " + fkObject.getString("field")
							+ " ='"
							+ allPKFKData.get(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))
									.toString()
							+ "'";
//					 System.out.println(SQL_SELECT);
					PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
					ResultSet resultSet = preparedStatement.executeQuery();

					JSONArray arr = new JSONArray();
					int iCount = 0;
					float amountpercent = 0.0f;
					double totalamount = 0.0d;
					while (resultSet.next()) {
						String strAmt = "0";
						String strOtherChargesName = "null";
						Object propstrength__amount__c = resultSet.getObject("propstrength__amount__c");
						Object other_charges_name__c = resultSet.getObject("other_charges_name__c");
						if (propstrength__amount__c != null) {
							strAmt = propstrength__amount__c.toString();
						}
						if (other_charges_name__c != null) {
							strOtherChargesName = other_charges_name__c.toString();
						}
						if(allPKFKData.getString("propstrength__application_booking__c.region__c").toLowerCase().equals("pune")){
						allPKFKData.put(fkObject.getString("pkobject") + "." + fkObject.getString("object") + "."
								+ strOtherChargesName, getRoundOffData(reformatExponentialValue(strAmt)));
						}else{
							allPKFKData.put(fkObject.getString("pkobject") + "." + fkObject.getString("object") + "."
									+ strOtherChargesName, reformatExponentialValue(strAmt));
						}
						//System.out.println(fkObject.getString("pkobject") + "." + fkObject.getString("object") + "."+ strOtherChargesName);
					}
				}

				// System.out.println("getPaymentOtherChargesOpted() :: " +
				// allPKFKData);
			} else {
				System.out.println("getFkData()  :: Failed to make connection!");
			}
		} catch (Exception e) {
			System.out.println("getFkData()  :: " + e.getMessage());
		}
		return allPKFKData;

	}

	public static String reformatExponentialValue(String exponentialValue) {
		String result = "";
		try {
			// BigDecimal myNumber= new BigDecimal(exponentialValue);
			// result=myNumber.intValue();
			result = String.valueOf(new BigDecimal(exponentialValue).setScale(2, BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			result = exponentialValue;
		}
		return result;
	}

	public static JSONObject getApplicantDetail(Connection conn, JSONObject allPKFKData, JSONObject fkObject,
			String schemaTable) {

		try {
			if (conn != null) {
				String fkSchemaObject = "";
				if (!schemaTable.equals("")) {
					fkSchemaObject = schemaTable + "." + fkObject.getString("object");
				} else {
					fkSchemaObject = fkObject.getString("object");
				}
				// System.out.println(fkSchemaObject);
				if (allPKFKData.has(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))) {
					// System.out.println(allPKFKData.get(fkObject.getString("pkfield")).toString());
					String SQL_SELECT = "select * from " + fkSchemaObject + " where " + fkObject.getString("field")
							+ " ='"
							+ allPKFKData.get(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))
									.toString()
							+ "'";
					// System.out.println(SQL_SELECT);
					PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
					ResultSet resultSet = preparedStatement.executeQuery();

					// System.out.println("Connected to the database!");
					ResultSetMetaData meta = resultSet.getMetaData();
					int columnCount = meta.getColumnCount();

					while (resultSet.next()) {
						// System.out.println("dffdff ::
						// "+resultSet.getObject("propstrength__active__c"));
						if (resultSet.getObject("propstrength__active__c").toString().equals("true")) {
							String applicantType = resultSet.getObject("propstrength__type__c").toString()
									.replace(" ", "").toLowerCase();
							String propstrength__applicant_acc__c = resultSet
									.getObject("propstrength__applicant_acc__c").toString();
							// System.out.println("propstrength__applicant_acc__c
							// :: "+propstrength__applicant_acc__c);
							// for (int column = 1; column <= columnCount;
							// column++) {
							// Object value = resultSet.getObject(column);
							// String columnName = meta.getColumnName(column);
							//
							// if (value != null) {
							// if (!allPKFKData.has(columnName)) {
							// allPKFKData.put(fkObject.getString("pkobject") +
							// "."
							// + fkObject.getString("object") + "." +
							// applicantType + "." + columnName,
							// value.toString());
							// }
							// // System.out.println("column :: " +
							// // columnName + " : value :: " +
							// // value.toString());
							//
							// } else {
							// if (!allPKFKData.has(columnName)) {
							// allPKFKData.put(fkObject.getString("pkobject") +
							// "."
							// + fkObject.getString("object") + "." +
							// applicantType + "." + columnName,
							// "");
							// }
							// // System.out.println("column :: " +
							// // columnName + " : value :: ");
							// }
							// }
							// Get Account Details
							String SQL_SELECTAcc = "select * from salesforce.contact where sfid='"
									+ propstrength__applicant_acc__c + "'";
							// System.out.println(SQL_SELECTAcc);
							PreparedStatement preparedStatementAcc = conn.prepareStatement(SQL_SELECTAcc);
							ResultSet resultSetAcc = preparedStatementAcc.executeQuery();

							// System.out.println("Connected to the database!");
							ResultSetMetaData metaAcc = resultSetAcc.getMetaData();
							int columnCountAcc = metaAcc.getColumnCount();
							// System.out.println(columnCountAcc);
							while (resultSetAcc.next()) {
								for (int columnAcc = 1; columnAcc <= columnCountAcc; columnAcc++) {
									Object value = resultSetAcc.getObject(columnAcc);
									String columnName = metaAcc.getColumnName(columnAcc);
									//System.out.println(fkObject.getString("pkobject") + "." + fkObject.getString("object")+ "." + applicantType + "." + columnName);	
									if (value != null) {
										if (!allPKFKData.has(columnName)) {
											
											String valueS = value.toString();
//											if(!columnName.equals("salutation")){
//											valueS = valueS.indexOf(".") < 0 ? valueS
//													: valueS.replaceAll("0*$", "").replaceAll("\\.$", "");
//											}
											if(valueS.indexOf(".") != -1 && valueS.split("\\.").length > 1 && valueS.split("\\.")[1].length() == 1 && valueS.split("\\.")[1].equals("0")){
												valueS = valueS.replaceAll("\\.0", "");
												//System.out.println(valueS);
											}
											allPKFKData.put(
													fkObject.getString("pkobject") + "." + fkObject.getString("object")
															+ "." + applicantType + "." + columnName,
													valueS);
										}
										// System.out.println("column :: " +
										// columnName + " : value :: " +
										// value.toString());

									} else {
										if (!allPKFKData.has(columnName)) {
											allPKFKData.put(
													fkObject.getString("pkobject") + "." + fkObject.getString("object")
															+ "." + applicantType + "." + columnName,
													"");
										}
										// System.out.println("column :: " +
										// columnName + " : value :: ");
									}
								}
							}

						}
						// System.out.println(applnBooking);
					}

				}

				// System.out.println("getApplicant() :: " + allPKFKData);
			} else {
				System.out.println("getApplicant()  :: Failed to make connection!");
			}
		} catch (Exception e) {
			System.out.println("getApplicant()  :: " + e.getMessage());
		}
		return allPKFKData;
	}

	public static JSONObject getChildFkData(Connection conn, JSONObject allPKFKData, JSONArray fkKeySchema,
			String schemaTable) {

		try {
			if (conn != null) {
				for (int i = 0; i < fkKeySchema.length(); i++) {
					JSONObject fkObject = fkKeySchema.getJSONObject(i);

					String fkSchemaObject = "";
					if (!schemaTable.equals("")) {
						fkSchemaObject = schemaTable + "." + fkObject.getString("object");
					} else {
						fkSchemaObject = fkObject.getString("object");
					}
					// System.out.println(fkSchemaObject);
					if (allPKFKData.has(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))) {
						// System.out.println(allPKFKData.get(fkObject.getString("pkfield")).toString());
						String SQL_SELECT = "select * from " + fkSchemaObject + " where " + fkObject.getString("field")
								+ " ='"
								+ allPKFKData.get(fkObject.getString("pkobject") + "." + fkObject.getString("pkfield"))
										.toString()
								+ "'";
						// System.out.println(SQL_SELECT);
						PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
						ResultSet resultSet = preparedStatement.executeQuery();

						// System.out.println("Connected to the database!");
						ResultSetMetaData meta = resultSet.getMetaData();
						int columnCount = meta.getColumnCount();

						while (resultSet.next()) {
							for (int column = 1; column <= columnCount; column++) {
								Object value = resultSet.getObject(column);
								String columnName = meta.getColumnName(column);

								if (value != null) {
									if (!allPKFKData.has(columnName)) {
										allPKFKData.put(fkObject.getString("pkobject") + "."
												+ fkObject.getString("object") + "." + columnName, value.toString());
									}
									// System.out.println("column :: " +
									// columnName + " : value :: " +
									// value.toString());

								} else {
									if (!allPKFKData.has(columnName)) {
										allPKFKData.put(fkObject.getString("pkobject") + "."
												+ fkObject.getString("object") + "." + columnName, "");
									}
									// System.out.println("column :: " +
									// columnName + " : value :: ");
								}
							}
							// System.out.println(applnBooking);
						}
					}

				}
				// System.out.println("getFkData() :: " + allPKFKData);
			} else {
				System.out.println("getFkData()  :: Failed to make connection!");
			}
		} catch (Exception e) {
			System.out.println("getFkData()  :: " + e.getMessage());
		}
		return allPKFKData;
	}

	public static String callPostService(String urlStr, String fullJson) {
		StringBuilder sb = null;
		try {
			URL object = new URL(urlStr);
			if (urlStr.indexOf("https") != -1) {
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}

				} };

				try {
					SSLContext sc = SSLContext.getInstance("SSL");
					sc.init(null, trustAllCerts, new java.security.SecureRandom());
					HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// Create all-trusting host name verifier
				HostnameVerifier allHostsValid = new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				};
				// Install the all-trusting host verifier
				HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
				/*
				 * end of the fix
				 */
			}

			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			// System.out.println("fullJson :: "+fullJson);
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(fullJson);
			wr.flush();

			// display what returns the POST request

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
		} catch (Exception e) {
			System.out.println("error :: " + e.getMessage());
			return e.getMessage();
		}
		return sb.toString();
	}

	public static String callGetService(String urlStr) {
		StringBuilder sb = null;
		try {
			URL obj = new URL(urlStr);
			if (urlStr.indexOf("https") != -1) {
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}

				} };

				try {
					SSLContext sc = SSLContext.getInstance("SSL");
					sc.init(null, trustAllCerts, new java.security.SecureRandom());
					HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// Create all-trusting host name verifier
				HostnameVerifier allHostsValid = new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				};
				// Install the all-trusting host verifier
				HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
				/*
				 * end of the fix
				 */
			}
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);

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
		} catch (Exception e) {
			return e.getMessage();
		}
		return sb.toString();
	}

	public static JSONObject addImages(JSONObject allPKFKData) {
		try {
			if (allPKFKData.has("propstrength__application_booking__c.propstrength__property__c.sfid")) {
				allPKFKData.put("propstrength__property__c.bizimagepath",
						ReadDirectory.getGPLImageUrl(bundle.getString("propertyImagePath"),
								allPKFKData.getString(
										"propstrength__application_booking__c.propstrength__property__c.sfid"),
								bundle.getString("propertyImageURL")));
			}
			if (allPKFKData
					.has("propstrength__application_booking__c.propstrength__property__c.propstrength__floor__c")) {
				allPKFKData.put("propstrength__floor__c.bizimagepath",
						ReadDirectory.getGPLImageUrl(bundle.getString("floorImagePath"),
								allPKFKData.getString(
										"propstrength__application_booking__c.propstrength__property__c.propstrength__floor__c"),
								bundle.getString("floorImageURL")));
			}
		} catch (Exception e) {
			System.out.println("addImages  :: " + e.getMessage().toString());
		}
		return allPKFKData;
	}

	public static JSONObject addImagesNew(JSONObject allPKFKData) {
		try {
			String attachmentIdResponse = "";
			JSONObject attachmentIdResponseObj = null;
			JSONObject attachmentBase64ResponseObj = null;
			String attachmentBase64Response = "";
			if (allPKFKData.has("propstrength__application_booking__c.propstrength__property__c.sfid")) {
				allPKFKData.put("propstrength__property__c.bizimagepath", "");
				attachmentIdResponse = PostgresFetchDataPKFKNew.callGetService(bundle.getString("getAttachmentId")
						+ allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.sfid"));
				attachmentIdResponseObj = new JSONObject(attachmentIdResponse);
				if (attachmentIdResponseObj.has("properties")) {
					if (attachmentIdResponseObj.getJSONArray("properties").length() > 0) {
						JSONArray propertyArr = attachmentIdResponseObj.getJSONArray("properties").getJSONObject(0)
								.getJSONArray("propertyAttachments");
						if (propertyArr.length() > 0) {
							for (int i = 0; i < propertyArr.length(); i++) {
								JSONObject insideJson = propertyArr.getJSONObject(i);
								if (insideJson.has("attachmentType")
										&& (insideJson.getString("attachmentType").equals("image/jpeg") || insideJson.getString("attachmentType").equals("image/png"))) {
									attachmentBase64Response = PostgresFetchDataPKFKNew
											.callGetService(bundle.getString("getBase64FromAttachmentId")
													+ insideJson.getString("attachmentId"));
									attachmentBase64ResponseObj = new JSONObject(attachmentBase64Response);
									if (attachmentBase64ResponseObj.has("attachments")
											&& attachmentBase64ResponseObj.getJSONArray("attachments").length() > 0) {
										JSONObject insideJsonBase64 = attachmentBase64ResponseObj
												.getJSONArray("attachments").getJSONObject(0);
										allPKFKData.put("propstrength__property__c.bizimagepath",
												ReadDirectory.getGPLImageUrlNew(bundle.getString("propertyImagePath"),
														allPKFKData.getString(
																"propstrength__application_booking__c.propstrength__property__c.sfid")
																+ ".jpg",
														insideJsonBase64.getString("attachmentBody"),
														bundle.getString("propertyImageURL")));
									} else {
										allPKFKData.put("propstrength__property__c.bizimagepath", "");
									}
									break;
								}
							}
						} else {
							allPKFKData.put("propstrength__property__c.bizimagepath", "");
						}
					} else {
						allPKFKData.put("propstrength__property__c.bizimagepath", "");
					}
				} else {
					allPKFKData.put("propstrength__property__c.bizimagepath", "");
				}
				// allPKFKData.put("propstrength__property__c.bizimagepath",
				// ReadDirectory.getGPLImageUrl(bundle.getString("propertyImagePath"),
				// allPKFKData.getString(
				// "propstrength__application_booking__c.propstrength__property__c.sfid"),
				// bundle.getString("propertyImageURL")));
			}
			if (allPKFKData
					.has("propstrength__application_booking__c.propstrength__property__c.propstrength__floor__c")) {
				// allPKFKData.put("propstrength__floor__c.bizimagepath",ReadDirectory.getGPLImageUrl(bundle.getString("floorImagePath"),allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.propstrength__floor__c"),bundle.getString("floorImageURL")));
				allPKFKData.put("propstrength__floor__c.bizimagepath", "");
				attachmentIdResponse = PostgresFetchDataPKFKNew
						.callGetService(bundle.getString("getAttachmentId") + allPKFKData.getString(
								"propstrength__application_booking__c.propstrength__property__c.propstrength__floor__c"));
				attachmentIdResponseObj = new JSONObject(attachmentIdResponse);
				if (attachmentIdResponseObj.has("floors")) {
					if (attachmentIdResponseObj.getJSONArray("floors").length() > 0) {
						JSONArray floorArr = attachmentIdResponseObj.getJSONArray("floors").getJSONObject(0)
								.getJSONArray("floorAttachments");
						if (floorArr.length() > 0) {
							for (int i = 0; i < floorArr.length(); i++) {
								JSONObject insideJson = floorArr.getJSONObject(i);
								if (insideJson.has("attachmentType")
										&& (insideJson.getString("attachmentType").equals("image/jpeg") || insideJson.getString("attachmentType").equals("image/png"))) {
									attachmentBase64Response = PostgresFetchDataPKFKNew
											.callGetService(bundle.getString("getBase64FromAttachmentId")
													+ insideJson.getString("attachmentId"));
									attachmentBase64ResponseObj = new JSONObject(attachmentBase64Response);
									if (attachmentBase64ResponseObj.has("attachments")
											&& attachmentBase64ResponseObj.getJSONArray("attachments").length() > 0) {
										JSONObject insideJsonBase64 = attachmentBase64ResponseObj
												.getJSONArray("attachments").getJSONObject(0);
										allPKFKData.put("propstrength__floor__c.bizimagepath",
												ReadDirectory.getGPLImageUrlNew(bundle.getString("floorImagePath"),
														allPKFKData.getString(
																"propstrength__application_booking__c.propstrength__property__c.propstrength__floor__c")
																+ ".jpg",
														insideJsonBase64.getString("attachmentBody"),
														bundle.getString("floorImageURL")));
									} else {
										allPKFKData.put("propstrength__floor__c.bizimagepath", "");
									}
									break;
								}
							}
						} else {
							allPKFKData.put("propstrength__floor__c.bizimagepath", "");
						}
					} else {
						allPKFKData.put("propstrength__floor__c.bizimagepath", "");
					}
				} else {
					allPKFKData.put("propstrength__floor__c.bizimagepath", "");
				}
			}

			if (allPKFKData.has(
					"propstrength__application_booking__c.propstrength__property__c.propstrength__property_type__c")) {
				// allPKFKData.put("propstrength__floor__c.bizimagepath",ReadDirectory.getGPLImageUrl(bundle.getString("floorImagePath"),allPKFKData.getString("propstrength__application_booking__c.propstrength__property__c.propstrength__floor__c"),bundle.getString("floorImageURL")));
				allPKFKData.put("propstrength__property_type__c.bizimagepath", "");
				attachmentIdResponse = PostgresFetchDataPKFKNew
						.callGetService(bundle.getString("getAttachmentId") + allPKFKData.getString(
								"propstrength__application_booking__c.propstrength__property__c.propstrength__property_type__c"));
				attachmentIdResponseObj = new JSONObject(attachmentIdResponse);
				if (attachmentIdResponseObj.has("propertyTypes")) {
					if (attachmentIdResponseObj.getJSONArray("propertyTypes").length() > 0) {
						JSONArray propertyTypeAttachmentsArr = attachmentIdResponseObj.getJSONArray("propertyTypes")
								.getJSONObject(0).getJSONArray("propertyTypeAttachments");
						if (propertyTypeAttachmentsArr.length() > 0) {
							for (int i = 0; i < propertyTypeAttachmentsArr.length(); i++) {
								JSONObject insideJson = propertyTypeAttachmentsArr.getJSONObject(i);
								if (insideJson.has("attachmentType")
										&& (insideJson.getString("attachmentType").equals("image/jpeg") || insideJson.getString("attachmentType").equals("image/png"))) {
									attachmentBase64Response = PostgresFetchDataPKFKNew
											.callGetService(bundle.getString("getBase64FromAttachmentId")
													+ insideJson.getString("attachmentId"));
									attachmentBase64ResponseObj = new JSONObject(attachmentBase64Response);
									if (attachmentBase64ResponseObj.has("attachments")
											&& attachmentBase64ResponseObj.getJSONArray("attachments").length() > 0) {
										JSONObject insideJsonBase64 = attachmentBase64ResponseObj
												.getJSONArray("attachments").getJSONObject(0);
										allPKFKData.put("propstrength__property_type__c.bizimagepath",
												ReadDirectory.getGPLImageUrlNew(
														bundle.getString("propertyTypeImagePath"),
														allPKFKData.getString(
																"propstrength__application_booking__c.propstrength__property__c.propstrength__property_type__c")
																+ ".jpg",
														insideJsonBase64.getString("attachmentBody"),
														bundle.getString("propertyTypeImageURL")));
									} else {
										allPKFKData.put("propstrength__property_type__c.bizimagepath", "");
									}
									break;
								}
							}
						} else {
							allPKFKData.put("propstrength__property_type__c.bizimagepath", "");
						}
					} else {
						allPKFKData.put("propstrength__property_type__c.bizimagepath", "");
					}
				} else {
					allPKFKData.put("propstrength__property_type__c.bizimagepath", "");
				}
			}

		} catch (Exception e) {
			System.out.println("addImagesNew  :: " + e.getMessage().toString());
		}
		return allPKFKData;
	}

	public static JSONObject getProjectHead(Connection conn, JSONObject allPKFKData) {
		try {
			allPKFKData.put("propstrength__application_booking__c.propstrength__projects__c.project_head__c.name", "");
			if (allPKFKData.has("propstrength__application_booking__c.propstrength__projects__c.project_head__c")) {
				String SQL_SELECT = "select * from salesforce.user where sfid ='" + allPKFKData.getString(
						"propstrength__application_booking__c.propstrength__projects__c.project_head__c") + "'";
//				System.out.println("SQL_SELECT  :: " + SQL_SELECT);
				PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (conn != null) {

					while (resultSet.next()) {
						Object name = resultSet.getObject("name");
						if (name != null) {
							allPKFKData.put(
									"propstrength__application_booking__c.propstrength__projects__c.project_head__c.name",
									name);
						}
					}
					// System.out.println("getPKData :: " + pkData);

				} else {
					System.out.println("getProjectHead()  :: Failed to make connection!");
				}
			}
		} catch (Exception e) {
			System.out.println("getProjectHead()  :: " + e.getMessage());
		}

		return allPKFKData;
	}

	public static String getRoundOffData(String expression) {
		try {
			// System.out.println("nS ::
			// "+Math.round(Double.parseDouble(expression)));
			expression = String.valueOf(Math.round(Double.parseDouble(expression)));
		} catch (Exception e) {
			//System.out.println("getRoundOffData()  :: " + e.getMessage());
		}

		return expression;
	}
	
    public static String renameFileTo(String strOldFileName,String strNewFileName)
    {	
    	String fileUrl = "";
    	File oldfile = null;
    	File newfile = null;
		oldfile = new File(bundle.getString("GPLAgreementLetterSavePath") + strOldFileName + ".docx");
		newfile = new File(bundle.getString("GPLAgreementLetterSavePath") + strNewFileName + ".docx");
		
		if(oldfile.renameTo(newfile)){
			System.out.println("Rename succesful");
		}else{
			System.out.println("Rename failed");
		}
		
		oldfile = new File(bundle.getString("GPLAgreementLetterSavePath") + strOldFileName + ".pdf");
		newfile = new File(bundle.getString("GPLAgreementLetterSavePath") + strNewFileName + ".pdf");
		
		if(oldfile.renameTo(newfile)){
			System.out.println("Rename succesful");
		}else{
			System.out.println("Rename failed");
		}
		fileUrl = bundle.getString("GPLAgreementLetterURL") + strNewFileName + ".pdf";
		return fileUrl;
    	
    }

}
