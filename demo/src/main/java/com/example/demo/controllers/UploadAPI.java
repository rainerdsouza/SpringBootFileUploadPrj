package com.example.demo.controllers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadAPI {

	
	 @RequestMapping("/upload")
	    public String hello(Model model, @RequestParam(defaultValue="World") MultipartFile file) {
	        model.addAttribute("file", file);
     
        	 
        	if(!file.isEmpty()){
        		
            	// A service class can be created to handle all of this. 
        		//I put all the logic in the controller due to time constraint
        		
	        try {
        		byte[] bytes = file.getBytes();
        	    Path path = Paths.get("C://temp//"+ file.getOriginalFilename()) ;
        		Files.write(path, bytes);	
				BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
				FileOwnerAttributeView ownerAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
		        UserPrincipal owner = ownerAttributeView.getOwner();
		        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		        // Model object can be created to handle all these values.
		        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/main/resources/metaData.txt", true), "utf-8"));
		        writer.write("File Name: " + file.getOriginalFilename());
		        writer.newLine();
		        writer.write("File Owner: "+ owner.getName());
		        writer.newLine();
		        writer.write("File Creation Time: " + format.format((attr.creationTime().toMillis())));
		        writer.newLine();
		        writer.write("File Last Access Time: " + format.format((attr.lastAccessTime().toMillis())));
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return "File " + file.getOriginalFilename()+ " Has been Succesfully Uploaded.";
	    }
        	else{
        		
        		return "Please Select a File to upload";
        	}
	 }
}
