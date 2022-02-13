package com.keytech.Controllers;

import static org.mockito.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.keytech.commands.RecipeCommand;
import com.keytech.service.ImageService;
import com.keytech.service.RecipeService;

class ImageControllerTest {

	@Mock
	ImageService imageService;
	
	@Mock
	RecipeService recipeService;
	
	ImageController controller;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new ImageController(imageService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testShowUploadForm() throws Exception {
		//given
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		when(recipeService.findCommandById(any())).thenReturn(command);
		
		//when
		mockMvc.perform(get("/recipe/1/image"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("recipe"));
		
		//then
		verify(recipeService, times(1)).findCommandById(any());
		
	}

	@Test
	void testHandleImagePost() throws Exception {
		//given
		//when
		//then
		MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
				"Java Evangelist".getBytes());
		
		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/recipe/1/show"));
		
		verify(imageService, times(1)).saveImageFile(any(), any());
		
	}
	
	@Test
	void renderImageFromDb() throws Exception {
		//given
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		
		String string = "fake image text";
		Byte[] bytesBoxed = new Byte[string.getBytes().length];
		int i = 0;
		for(byte primByte : string.getBytes()) {
			bytesBoxed[i++] = primByte;
		}
		
		command.setImage(bytesBoxed);
		when(recipeService.findCommandById(any())).thenReturn(command);
		
		//when
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		byte[] responseBytes = response.getContentAsByteArray();
		
		//then
		assertEquals(string.getBytes().length, responseBytes.length);
		
	}

}
