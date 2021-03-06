package com.keytech.Controllers;

import static org.mockito.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.keytech.commands.RecipeCommand;
import com.keytech.domain.Recipe;
import com.keytech.exceptions.NotFoundException;
import com.keytech.service.RecipeService;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

	@Mock
	RecipeService recipeService;
	
	@InjectMocks
	RecipeController controller;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		controller = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}
	
	@Test
	void testShowById() throws Exception {
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		when(recipeService.findById(any())).thenReturn(recipe);

		mockMvc.perform(get("/recipe/1/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	void testHandleNotFound() throws Exception {
		when(recipeService.findById(any())).thenThrow(NotFoundException.class);
		mockMvc.perform(get("/recipe/1/show"))
				.andExpect(status().isNotFound())
				.andExpect(view().name("404error"));
	}
	
	@Test
	void testHandleBadRequest() throws Exception {
		mockMvc.perform(get("/recipe/asdf/show"))
			 	.andExpect(status().isBadRequest())
			 	.andExpect(view().name("400error"));
	}
	
	@Test
	void testShowByIdNotFound() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1l);
		when(recipeService.findById(any())).thenThrow(NotFoundException.class);
		mockMvc.perform(get("/recipe/1/show"))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testGetNewRecipeForm() throws Exception{
		RecipeCommand command = new RecipeCommand();
		
		mockMvc.perform(get("/recipe/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	void testPostNewRecipeForm() throws Exception{
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);
		
		when(recipeService.saveRecipeCommand(any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some description")
				.param("directions", "some directions"))
				
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/show"));
				
	}
	
	@Test
	void testPostNewRecipeFormFail() throws Exception{
		
		mockMvc.perform(post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				)
		
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("recipe"))
				.andExpect(view().name("recipe/recipeform"));
				
	}
	
	@Test
	void testGetUpdateView() throws Exception {
		
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);
		
		when(recipeService.findCommandById(any())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/2/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
		
	}
	
	@Test
	void testDeleteById() throws Exception {
		
		mockMvc.perform(get("/recipe/1/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
		
		verify(recipeService,times(1)).deleteById(any());
	}
	
}
