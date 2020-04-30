package com.simple;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class abc {
public static  void main(String [] argss) {
	// TODO Auto-generated method stub
try {
	//String	status= new SendAttachmentInEmail().sendMail("vivek@bizlem.com", "demo", "body", "pallavi@bizlem.com", "Pallu@123", null);

	/*String txt = "Welcome to  Mail Template!<h1>Hello</h1> body";
	
	 Pattern p = Pattern.compile("<([^\\s>/]+)");
	    Matcher m = p.matcher(txt);
	    while(m.find()) {
	        String tag = m.group(1);
	        System.out.println("tag: "+tag);
	    }
	*/
//	ResourceBundle bundle = ResourceBundle.getBundle("config");
//	System.out.println(bundle.getString("GenAttachmentpath"));
	ResourceBundle bundle = ResourceBundle.getBundle("config");
	System.out.println(bundle.getString("updateData"));

	
} catch ( Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}
}
