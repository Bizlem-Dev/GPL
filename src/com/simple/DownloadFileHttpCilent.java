package com.simple;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
 
public class DownloadFileHttpCilent {
 
    public static void main(String[] args) throws Exception {
        DownloadFileHttpCilent client = new DownloadFileHttpCilent();
       // client.download();
        
        String attachFileurl="https://test.bluealgo.com:8083/portal/content/services/6445/G1/DocTigerAdvanced/Communication/MailTemplate/Mailtest1/Attachment/ZSxdsa.docx";
        
        if(attachFileurl.lastIndexOf("/")!=-1) {
			String Filename=attachFileurl.substring(attachFileurl.lastIndexOf("/")+1);
		System.out.println(Filename);
    }
        
        
        
    	String[] attachFileurlarr=attachFileurl.split(",");
		for(int i=0; i<attachFileurlarr.length; i++) {
			String attachurl1=attachFileurlarr[i].trim();
		if(attachurl1.startsWith("http:") && attachurl1.lastIndexOf("/")!=-1) {
			String Filename=attachurl1.substring(attachurl1.lastIndexOf("/")+1);
System.out.println("Filename "+Filename);
			//boolean status= download( attachurl1,  attachmentPath,  Filename) ;
		//	System.out.println("status "+status);

    // if(status==true) {
    //	 attarr.put(Filename);
     //     }
		}
		}
        }
 
    public void download() {
        try {
        	
        	
				
				
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://test.bluealgo.com:8083/portal/content/services/6445/G1/DocTigerAdvanced/Communication/MailTemplate/Mailtest1/Attachment/ZSxdsa.docx");
 
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
 
            int responseCode = response.getStatusLine().getStatusCode();
 
            System.out.println("Request Url: " + request.getURI());
            System.out.println("Response Code: " + responseCode);
 
            InputStream is = entity.getContent();
 
            String filePath = "D:\\ABChttps.docx";
            FileOutputStream fos = new FileOutputStream(new File(filePath));
 
            int inByte;
            while ((inByte = is.read()) != -1) {
                fos.write(inByte);
            }
 
            is.close();
            fos.close();
 
            client.close();
            System.out.println("File Download Completed!!!");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}