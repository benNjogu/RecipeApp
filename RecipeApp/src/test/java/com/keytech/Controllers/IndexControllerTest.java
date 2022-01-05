package com.keytech.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.keytech.service.RecipeService;

class IndexControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	IndexController indexController;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		
		indexController = new IndexController(recipeService);
	}
	
	@Test
	void testGetIndexPage() {
		String viewName = indexController.getIndexPage(model);
		assertEquals("index", viewName);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"),anySet());
	}

}
