package com.simple;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.service.UploadTemplateServer;

/**
 * Servlet implementation class getFileAttachmentServlet
 */
@WebServlet("/getSendGridAndSesServlet")
public class getSendGridAndSesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResourceBundle bundle = ResourceBundle.getBundle("config");
	static ResourceBundle bundleststic = ResourceBundle.getBundle("config");
	
	String status="";

    /**
     * Default constructor. 
     */
    public getSendGridAndSesServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out= response.getWriter();
		String subject="";
		String body="";
		String fromId="";
		String fromPass="";
		String attachmentPath="";
		String[] to= {};
		String[] cc={};
		String[] bcc={};
		String[] attachments={};
		
		try {
			BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result = bis.read();

			while (result != -1) {
				buf.write((byte) result);
				result = bis.read();
			}
			String res = buf.toString("UTF-8");
			JSONObject obj = new JSONObject(res);
			System.out.println("obj  ^^^^^ "+obj);
			//save attachment
			try {
				if(obj.has("attachmentScorpio")) {
					UploadTemplateServer ftp = new UploadTemplateServer(bundleststic.getString("ScorpioIp"),22,bundleststic.getString("Scorpiousername"),bundleststic.getString("Scorpiopass"));
				      String a=ftp.connect();
				JSONArray jsc=obj.getJSONArray("attachmentScorpio");
				
					for(int i=0;i<jsc.length();i++) {
						try {
							String serpath="";
							String fileurl = jsc.getString(i);
							String filename = "";
							if (fileurl != null && fileurl != "") {
								int o = fileurl.lastIndexOf("/");
								filename = fileurl.substring(o + 1, fileurl.length());
								 serpath= fileurl.substring(0, o+1);
					        out.println("serpath: " +serpath);
					        out.println("filename: " +filename);
							}
			
							out.print(a);
				String pth=ftp.download(serpath+filename, bundleststic.getString("GenAttachmentpath"));//bundleststic.getString("GenAttachmentpath"));
				out.print("resp download "+pth);
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}

			}catch (Exception e) {
				// TODO: handle exception
				e.getStackTrace();
			}
			
			subject= obj.getString("subject");
			body= obj.getString("body");
			fromId= obj.getString("fromId");
			fromPass= obj.getString("fromPass");
			attachmentPath=obj.getString("attachmentPath");
			JSONArray toarr=obj.getJSONArray("to");
			JSONArray ccarr=obj.getJSONArray("cc");
			JSONArray bccarr=obj.getJSONArray("bcc");
			JSONArray attarr=obj.getJSONArray("attachments");
			try {
			if(obj.has("attachFileurl")) {
				String attachFileurl=obj.getString("attachFileurl");
System.out.println("attachFileurl "+attachFileurl);
				String[] attachFileurlarr=attachFileurl.split(",");
				for(int i=0; i<attachFileurlarr.length; i++) {
					String attachurl1=attachFileurlarr[i].trim();
				if(attachurl1.startsWith("http:") && attachurl1.lastIndexOf("/")!=-1) {
					String Filename=attachurl1.substring(attachurl1.lastIndexOf("/")+1);
System.out.println("Filename "+Filename);
					boolean status= download( attachurl1,  attachmentPath,  Filename) ;
					System.out.println("status "+status);

             if(status==true) {
            	 attarr.put(Filename);
                  }
				}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			if(toarr.length()>0) {
			to=new String[toarr.length()];
			for(int i=0;i<toarr.length();i++) {
			to[i]=toarr.getString(i);
			}
			}
			if(ccarr.length()>0) {
			cc=new String[ccarr.length()];
			for(int i=0;i<ccarr.length();i++) {
				cc[i]=ccarr.getString(i);
				}
			
			}
			if(bccarr.length()>0) {
		    bcc=new String[bccarr.length()];
			for(int i=0;i<bccarr.length();i++) {
				bcc[i]=bccarr.getString(i);
				}
			
			}
			if(attarr.length()>0) {
				System.out.println("attarr.length() "+attarr.length());
		    attachments=new String[attarr.length()];
			for(int i=0;i<attarr.length();i++) {
			    System.out.println("attarr.getString(i) "+attarr.getString(i));
				attachments[i]=attarr.getString(i);
				}
			}
		
			
			String enabled=obj.getString("enabled");
			JSONObject jsonmailObj=null;
			
			String fromname="";
			String type="";
			String from="";
			String apikey="";
			String host="";
			String replyto="";
			
			if( obj.has("jsonmail") ){
				jsonmailObj=new JSONObject(obj.getString("jsonmail"));
			}
			
			if(jsonmailObj.has("fromname")){ 
				fromname=jsonmailObj.getString("fromname");
			}if(jsonmailObj.has("from")){ 
				from=jsonmailObj.getString("from");
			}if(jsonmailObj.has("apikey")){ 
				apikey=jsonmailObj.getString("apikey");
			}if(jsonmailObj.has("type")){ 
				type=jsonmailObj.getString("type");
			}if(jsonmailObj.has("host")){ 
				host=jsonmailObj.getString("host");
			}if(jsonmailObj.has("username")){ 
				fromId=jsonmailObj.getString("username");
			}if(jsonmailObj.has("password")){ 
				fromPass=jsonmailObj.getString("password");
			}if(jsonmailObj.has("replyto")){ 
				replyto=jsonmailObj.getString("replyto");
			}
			
			if("sendgrid".equalsIgnoreCase(enabled)){
				
				status=	new SendMailCCAndBccAttachment().sendFromSendGrid(fromId,fromPass,to, cc, bcc, subject, body,  attachments,attachmentPath,from,fromname,apikey);
				out.println("status: "+status);
				
			} // sendgrid close here
			else{
				
				if("ses".equalsIgnoreCase(enabled)){
					
					status=	new SendMailCCAndBccAttachment().sendFromSes(fromId,fromPass,to, cc, bcc, subject, body,  attachments,attachmentPath,from, fromname, host,replyto);
					out.println("status: "+status);
					
				} // ses close here
				
				else{
					if("powermta".equalsIgnoreCase(enabled)){
						status=	new SendMailCCAndBccAttachment().sendFromSes(fromId,fromPass,to, cc, bcc, subject, body, attachments,attachmentPath,from, fromname, host,replyto);
						out.println("status: "+status);
					}
				}
				
			}
			
	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public boolean download(String url, String attachpath, String filename) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
 
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
 
            int responseCode = response.getStatusLine().getStatusCode();
 
            System.out.println("Request Url: " + request.getURI());
            System.out.println("Response Code: " + responseCode);
 
            InputStream is = entity.getContent();
 
            //String filePath = "D:\\ABChttps.docx";
            FileOutputStream fos = new FileOutputStream(new File(attachpath+filename));
 
            int inByte;
            while ((inByte = is.read()) != -1) {
                fos.write(inByte);
            }
 
            is.close();
            fos.close();
 
            client.close();
            System.out.println("File Download Completed!!!");
            return true;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return false;

        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
    }

}
