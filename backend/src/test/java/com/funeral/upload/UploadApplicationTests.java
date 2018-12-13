package com.funeral.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funeral.upload.service.TestService;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UploadApplicationTests {

	@Autowired
	private MockMvc mvc;

	private URL basePath;

	private static final ObjectMapper MAPPER = new ObjectMapper();




	@Before
	public void init(){
		System.out.println("SpringBoot Junit Tests started.");
	}


	@Test
	public void contextLoads() throws Exception {
		File file = ResourceUtils.getFile("classpath:test-user.json");
//		String jsonStr = FileUtils.readFileToString(file,"UTF-8");
		ObjectMapper objectMapper = new ObjectMapper();
		Map map = objectMapper.readValue(file, Map.class);
		String t_content = objectMapper.writeValueAsString(map.get("F"));
		MvcResult mr = mvc.perform(
				post("/login")
						.content(t_content)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		Map<String,String> test =new HashMap<>();

	}

	@After
	public void end(){
		System.out.println("StringBoot Junit Tests finished.");
	}


}
