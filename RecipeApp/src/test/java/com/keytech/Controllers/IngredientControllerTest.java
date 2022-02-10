package com.keytech.Controllers;

import static org.mockito.Matchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.keytech.commands.IngredientCommand;
import com.keytech.commands.RecipeCommand;
import com.keytech.service.IngredientService;
import com.keytech.service.RecipeService;
import com.keytech.service.UnitOfMeasureService;

class IngredientControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	IngredientService ingredientService;
	
	@Mock
	UnitOfMeasureService unitOfMeasureService;
	
	IngredientController controller;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testListIngredients() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(any())).thenReturn(recipeCommand);
		
		//when
		mockMvc.perform(get("/recipe/1/ingredients"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list"))
				.andExpect(model().attributeExists("recipe"));
		
		//then
		verify(recipeService, times(1)).findCommandById(any());
		
	}
	
	@Test
	public void testUpdateIngredientForm() throws Exception {
		//given
		IngredientCommand command = new IngredientCommand();
		
		//when
		when(ingredientService.findByRecipeIdandIngredientId(any(), any())).thenReturn(command);
		when(unitOfMeasureService.listAllUOMs()).thenReturn(new HashSet<>());
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(model().attributeExists("uomList"));
		
	}

}
