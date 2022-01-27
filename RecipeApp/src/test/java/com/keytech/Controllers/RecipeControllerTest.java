package com.keytech.Controllers;

import static org.mockito.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.keytech.domain.Recipe;
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
	}

	@Test
	void testShowById() throws Exception {
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		when(recipeService.findById(any())).thenReturn(recipe);

		mockMvc.perform(get("/recipe/show/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}

}
