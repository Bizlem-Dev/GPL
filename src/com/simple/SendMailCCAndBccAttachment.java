package com.simple;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import com.service.SMTPMXLookup;
 
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.ASM;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.BccSettings;
import com.sendgrid.helpers.mail.objects.ClickTrackingSetting;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.FooterSetting;
import com.sendgrid.helpers.mail.objects.GoogleAnalyticsSetting;
import com.sendgrid.helpers.mail.objects.MailSettings;
import com.sendgrid.helpers.mail.objects.OpenTrackingSetting;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.sendgrid.helpers.mail.objects.Setting;
import com.sendgrid.helpers.mail.objects.SpamCheckSetting;
import com.sendgrid.helpers.mail.objects.SubscriptionTrackingSetting;
import com.sendgrid.helpers.mail.objects.TrackingSettings;
public class SendMailCCAndBccAttachment {
	
	private static final String MAIL_SERVER = "smtp";
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 587;
    // GMail password
// "doctigertest@gmail.com"); doctiger@123
    
	public static void main(String[] args) {
		SendMailCCAndBccAttachment sm=new SendMailCCAndBccAttachment();
        // Message info       
        String[] to = { "mohitraj.ranu@gmail.com" ,"mohit.raj@bizlem.io" }; // list of recipient email addresses
        String[] cc={ "abhishek.tiwari@bizlem.io" };
        String[] bcc={};
        String subject = "Java sendgrid test";
        String body = "Welcome to  Mail Template!<h1>Hello</h1>";	
        String[] attachments= {};
		String attachmentPath="";
		String fromId="";
		String fromPass="";
		try {
			sm.sendFromGMail(fromId,fromPass,to, cc, bcc, subject, body,  attachments,attachmentPath);
			System.out.println("Email Sent....!");
		} catch (Exception ex) {
			System.out.println("Could not send email....!");
			ex.printStackTrace();
		}
	}
 
	public String sendFromGMail(String fromId, String fromPass,String[] to, String[] cc, String[] bcc, String subject, String body, String[] attachments,String attachmentPath) {
 //Extract host============================================
	String 	SMTP_HOST_NAME_custom = SMTPMXLookup.isAddressValid(fromId);
//=========================================================		
		String resp="";
//---------------------------------------------STEP 1---------------------------------------------
	 final String USER_NAME =fromId;  // GMail user name (just the part before "@gmail.com")
	final String PASSWORD = fromPass;
    	System.out.println("\n 1st ===> Setup SMTP Mail Server Properties..!");
    	
    	// Get system properties
        Properties properties = System.getProperties();
        
        // Setup mail server        
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST_NAME_custom);
       // properties.put("mail.smtp.host", SMTP_HOST_NAME);
        properties.put("mail.smtp.user", USER_NAME);
        properties.put("mail.smtp.password", PASSWORD);
        properties.put("mail.smtp.port", SMTP_HOST_PORT);
        properties.put("mail.smtp.auth", "true");
        
      //---------------------------------------------STEP 2---------------------------------------------
 
        
     	System.out.println("\n\n 2nd ===> Get Mail Session..");
        // Get the Session object.
     	
     	// creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        };
        
        Session session = Session.getInstance(properties, auth);        
 
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);
 
        try {
        	
        	//---------------------------------------------
        	
        	// Set From: header field of the header.
            message.setFrom(new InternetAddress(USER_NAME));
            
          //---------------------------------------------
            
            InternetAddress[] toAddress = new InternetAddress[to.length];
 
            // To get the array of toaddresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }
            
            // Set To: header field of the header.
            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            
            InternetAddress[] ccAddress = new InternetAddress[cc.length];
            
            // To get the array of ccaddresses
            for( int i = 0; i < cc.length; i++ ) {
                ccAddress[i] = new InternetAddress(cc[i]);
            }
            
            // Set cc: header field of the header.
            for( int i = 0; i < ccAddress.length; i++) {
                message.addRecipient(Message.RecipientType.CC, ccAddress[i]);
            }
            
            InternetAddress[] bccAddress = new InternetAddress[bcc.length];
            
            // To get the array of bccaddresses
            for( int i = 0; i < bcc.length; i++ ) {
                bccAddress[i] = new InternetAddress(bcc[i]);
            }
            
            // Set bcc: header field of the header.
            for( int i = 0; i < bccAddress.length; i++) {
                message.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
            }
            
            // Set Subject: header field
            message.setSubject(subject);
                                  
            // Now set the date to actual message
            message.setSentDate(new Date());
            
            // Now set the actual message
//            message.setContent(body,"text/html");         
            
            Multipart multipart = new MimeMultipart();

            BodyPart bodypart = new MimeBodyPart();
            bodypart.setContent(body, "text/html");

            multipart.addBodyPart(bodypart);
            try {
            if (attachments!=null){
                for (int co=0; co<attachments.length; co++){
                	try {
                    bodypart = new MimeBodyPart();
                    File file = new File(attachmentPath+attachments[co]);
                    
                    DataSource datasource = new FileDataSource(file);
                    bodypart.setDataHandler(new DataHandler(datasource));
                    bodypart.setFileName(file.getName());
                    multipart.addBodyPart(bodypart);
                	}catch (Exception e) {
						// TODO: handle exception
					}
                }
            }else {
            	
            }
            }catch (Exception e) {
            	resp=e.toString();
			}
            message.setContent(multipart);
            
          //---------------------------------------------STEP 3---------------------------------------------
            
    		System.out.println("\n\n 3rd ===> Get Session and Send Mail");
            // Send message
            Transport transport = session.getTransport(MAIL_SERVER);
            transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, USER_NAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Sent Message Successfully....");
        }
       
        catch (Exception me) {
           	resp=resp+me.toString();
        }
        return resp+"Mail Sent Successfully";
      //---------------------------------------------------------------------------------------------------
        
    }

	public String sendFromSes(String fromId, String fromPass,String[] to, String[] cc, String[] bcc, String subject, String body, String[] attachments,String attachmentPath
			,String from, String fromname, String host, String replyto
			) {
		 //Extract host============================================
//			String 	SMTP_HOST_NAME_custom = "email-smtp.us-east-1.amazonaws.com";
		String 	SMTP_HOST_NAME_custom = host;
//			final String FROM = "welcome@leadaconvert.com";
		final String FROM = fromname;
//			final String FROMNAME = "Geetanjali A";
			final String FROMNAME = from;
		//=========================================================		
				String resp="";
		//---------------------------------------------STEP 1---------------------------------------------
//			final String USER_NAME ="AKIAW53HZKAAZLUXWQBW";  // GMail user name (just the part before "@gmail.com")
				final String USER_NAME =fromId;
//			final String PASSWORD = "BG7WKSAQSR/kEVrv9RMDdJUtcFWLUkd6ww5jat8tV9mM";
				final String PASSWORD = fromPass;
		    System.out.println("\n 1st ===> Setup SMTP Mail Server Properties..!");
		    	
		    	// Get system properties
//		        Properties properties = System.getProperties();
		        
		        // Setup mail server        
		       /* properties.put("mail.smtp.starttls.enable", "true");
		        properties.put("mail.smtp.host", SMTP_HOST_NAME_custom);
		       // properties.put("mail.smtp.host", SMTP_HOST_NAME);
		        properties.put("mail.smtp.user", USER_NAME);
		        properties.put("mail.smtp.password", PASSWORD);
		        properties.put("mail.smtp.port", SMTP_HOST_PORT);
		        properties.put("mail.smtp.auth", "true");*/
		        
		     // Create a Properties object to contain connection configuration information.
		        Properties props = System.getProperties();
		        props.put("mail.transport.protocol", "smtp");
		        props.put("mail.smtp.port", SMTP_HOST_PORT); 
		        props.put("mail.smtp.starttls.enable", "true");
		        props.put("mail.smtp.auth", "true");
		        
		        
		      //---------------------------------------------STEP 2---------------------------------------------
		 
		        
		     	System.out.println("\n\n 2nd ===> Get Mail Session..");
		        // Get the Session object.
		     	
		     // Create a Session object to represent a mail session with the specified properties. 
		     	Session session = Session.getDefaultInstance(props);      
		 
		     // Create a message with the specified information. 
		     	MimeMessage message = new MimeMessage(session);
		 
		        try {
		        	
		        	//---------------------------------------------
		        	
		        	// Set From: header field of the header.
		        	message.setFrom(new InternetAddress(FROM,FROMNAME));
		            
		          //---------------------------------------------
		            
		            InternetAddress[] toAddress = new InternetAddress[to.length];
		 
		            // To get the array of toaddresses
		            for( int i = 0; i < to.length; i++ ) {
		                toAddress[i] = new InternetAddress(to[i]);
		            }
		            
		            // Set To: header field of the header.
		            for( int i = 0; i < toAddress.length; i++) {
		                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		            }
		            
		            InternetAddress[] ccAddress = new InternetAddress[cc.length];
		            
		            // To get the array of ccaddresses
		            for( int i = 0; i < cc.length; i++ ) {
		                ccAddress[i] = new InternetAddress(cc[i]);
		            }
		            
		            // Set cc: header field of the header.
		            for( int i = 0; i < ccAddress.length; i++) {
		                message.addRecipient(Message.RecipientType.CC, ccAddress[i]);
		            }
		            
		            InternetAddress[] bccAddress = new InternetAddress[bcc.length];
		            
		            // To get the array of bccaddresses
		            for( int i = 0; i < bcc.length; i++ ) {
		                bccAddress[i] = new InternetAddress(bcc[i]);
		            }
		            
		            // Set bcc: header field of the header.
		            for( int i = 0; i < bccAddress.length; i++) {
		                message.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
		            }
		            
		            // Set Subject: header field
		            message.setSubject(subject);
		                                  
		            // Now set the date to actual message
		            message.setSentDate(new Date());
		            message.setReplyTo(new InternetAddress[] 
		            	      {new InternetAddress(replyto)});
		            
		            // Now set the actual message
//		            message.setContent(body,"text/html");         
		            
		            Multipart multipart = new MimeMultipart();

		            BodyPart bodypart = new MimeBodyPart();
		            bodypart.setContent(body, "text/html");

		            multipart.addBodyPart(bodypart);
		            try {
		            if (attachments!=null){
		                for (int co=0; co<attachments.length; co++){
		                	try {
		                    bodypart = new MimeBodyPart();
		                    File file = new File(attachmentPath+attachments[co]);
		                    
		                    DataSource datasource = new FileDataSource(file);
		                    bodypart.setDataHandler(new DataHandler(datasource));
		                    bodypart.setFileName(file.getName());
		                    multipart.addBodyPart(bodypart);
		                	}catch (Exception e) {
								// TODO: handle exception
							}
		                }
		            }else {
		            	
		            }
		            }catch (Exception e) {
		            	resp=e.toString();
					}
		            message.setContent(multipart);
		            
		          //---------------------------------------------STEP 3---------------------------------------------
		            
		    		System.out.println("\n\n 3rd ===> Get Session and Send Mail");
		            // Send message
		            Transport transport = session.getTransport();
		         // Connect to Amazon SES using the SMTP username and password you specified above.
		            transport.connect(SMTP_HOST_NAME_custom, USER_NAME, PASSWORD);
		            transport.sendMessage(message, message.getAllRecipients());
		            transport.close();
		            System.out.println("Sent Message Successfully....");
		        }
		       
		        catch (Exception me) {
		           	resp=resp+me.toString();
		        }
		        return resp+"Mail Sent Successfully";
		      //---------------------------------------------------------------------------------------------------
		        
		    }
	
	
	public String sendFromSendGrid(String fromId, String fromPass,String[] to, String[] cc, String[] bcc, String subject, String body, String[] attachments,String attachmentPath
			,String from,String fromname, String apikey
			) {
		
		String resp="";
		try {
//			SendGrid sg = new SendGrid("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");
			SendGrid sg = new SendGrid(apikey);
			//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

			sg.addRequestHeader("X-Mock", "true");

			Request request = new Request();
			Email fromEmail = new Email();
//			fromEmail.setName("Geetanjali");
			fromEmail.setName(from);
//			fromEmail.setEmail("welcome@leadaconvert.com");
			fromEmail.setEmail(fromname);
			// mail.setFrom(fromEmail);
			//Email from = new email("welcome@leadaconvert.com");
			//subject = "Doctiger Dispaly name change MailTest";
			Email toemail = null;
			 for( int i = 0; i < to.length; i++ ) {
				 toemail = new Email(to[i]);
				 break;
	            }
	            
	            
	            
	            
			// Content content = new Content("text/plain", "This mail is being sent from sendgrid account");
			Content content = new Content();
			content.setType("text/html");
			content.setValue(body);
			Mail mail = new Mail(fromEmail,subject,toemail,content);
			// mail.addContent(content);
			// Note that when you use this constructor an initial personalization object
			// is created for you. It can be accessed via
			// mail.personalization.get(0) as it is a List object
			// Mail mail = new Mail(fromEmail, subject, to, content);
			mail.setFrom(fromEmail);
			for( int i = 1; i < to.length; i++ ) {
				 Email email1 = new Email(to[i]);
				 mail.personalization.get(0).addTo(email1); 
	            }
			
			
			for( int i = 0; i < cc.length; i++ ) {
				Email cc2 = new Email();
				//cc2.setName("Example User");
				cc2.setEmail(cc[i]);
				mail.personalization.get(0).addCc(cc2);
	            }
			for( int i = 0; i < bcc.length; i++ ) {
				Email bcc2 = new Email();
				//cc2.setName("Example User");
				bcc2.setEmail(bcc[i]);
				mail.personalization.get(0).addBcc(bcc2);
	            }
			
			if (attachments!=null){
                for (int co=0; co<attachments.length; co++){
                		Attachments attachment = new Attachments();
                    File file = new File(attachmentPath+attachments[co]);
                    
                    String base64File = "";
                    FileInputStream imageInFile = new FileInputStream(file);
                        // Reading a file from file system
                        byte fileData[] = new byte[(int) file.length()];
                        imageInFile.read(fileData);
                        base64File = Base64.getEncoder().encodeToString(fileData);
                    attachment.setContent(base64File);
                    attachment.setType("application/pdf"); // search for any file can accept make it dynamic.
        			attachment.setFilename(file.getName()); // here it is taking file name from file. here we passed path plus name of attachment which are String array.
        			attachment.setDisposition("attachment");
        			attachment.setContentId("Attach Sheet");
        			mail.addAttachments(attachment);
                	
                }
            }
			
			
				request.setMethod(Method.POST);
				request.setEndpoint("mail/send");
				request.setBody(mail.build());
				Response response = sg.api(request);
				System.out.println(response.getStatusCode());
				System.out.println(response.getBody());
				System.out.println(response.getHeaders());
				
			
			/* Email email = new email("sales@doctiger.com");
			mail.personalization.get(0).addTo(email);
			Email email1 = new email("innovaters@gmail.com");
			mail.personalization.get(0).addTo(email1);*/
			/*Email email1 = new email("mohit.raj@bizlem.io");
			mail.personalization.get(0).addTo(email1);*/
			/*Email email2 = new email("chetan.agarwal@bizlem.com");
			mail.personalization.get(0).addTo(email2);*/
		} catch (Exception e) {
			e.printStackTrace();
			resp=resp+e.toString();
		}
		
		return resp+"Mail Sent Successfully";
		
	}
	
	
}
 