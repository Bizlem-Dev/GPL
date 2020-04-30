package com.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import com.sun.jersey.core.util.Base64;

public class ReadDirectory {
	static ResourceBundle bundle = ResourceBundle.getBundle("config");
	public static void main(String[] args) {
		//D:\\vivek\\doctiger\\Godrej\\GPLImages\\property\\
		System.out.println(ReadDirectory.getGPLImageUrl(bundle.getString("propertyImagePath"), "a1s6F00000BrXOzQAN", bundle.getString("propertyImageURL")));
		System.out.println(ReadDirectory.getGPLImageUrl(bundle.getString("floorImagePath"), "a1J6F00000S5N4kUAF", bundle.getString("floorImageURL")));
	}

	public static String getGPLImageUrl(String dirPath, String matchingPattern, String imageUrl) {
		String imageLinkWithFile = "";
		try {
			File f = new File(dirPath);
			File[] matchingFiles = f.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.startsWith(matchingPattern);
				}
			});

			if (matchingFiles.length > 0) {
				System.out.println(matchingFiles[0].getName());
				imageLinkWithFile = imageUrl + matchingFiles[0].getName();
			}
		} catch (Exception e) {
			System.out.println("ReadDirectory getGPLImageUrl Error :: "+e.getMessage().toString());
		}
		return imageLinkWithFile;

	}
	
	public static String getGPLImageUrlNew(String dirPath, String file_name, String filedata,String imageUrl) {
		String imageLinkWithFile = "";
		try {
			byte[] bytes = Base64.decode(filedata);
			OutputStream stream = new FileOutputStream(dirPath+file_name);
			stream.write(bytes);
			stream.close();
			imageLinkWithFile = imageUrl + file_name;
		} catch (Exception e) {
			System.out.println("ReadDirectory getGPLImageUrlNew Error :: "+e.getMessage().toString());
		}
		return imageLinkWithFile;

	}

}