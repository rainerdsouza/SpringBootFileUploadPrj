package com.example.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;


public class UploadApiTest extends DemoApplicationTests {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testUpload() throws Exception {
		
		FileInputStream inputFile = new FileInputStream( "src/main/resources/Test.txt");  
		MockMultipartFile firstFile = new MockMultipartFile("file", "Test.txt", "multipart/form-data", inputFile);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload").file(firstFile)).andExpect(content().string("File Has been Succesfully Uploaded."));
	}
}
