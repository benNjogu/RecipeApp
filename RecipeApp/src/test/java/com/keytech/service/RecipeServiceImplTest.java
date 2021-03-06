package com.keytech.service;


import static org.mockito.Matchers.any;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import com.keytech.RecipeApp.RecipeAppApplication;
import com.keytech.converters.RecipeCommandToRecipe;
import com.keytech.converters.RecipeToRecipeCommand;
import com.keytech.domain.Recipe;
import com.keytech.exceptions.NotFoundException;
import com.keytech.repositories.RecipeRepository;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, 
				new RecipeCommandToRecipe(null , null, null), 
				new RecipeToRecipeCommand(null, null, null));
	}
	
	@Test
	void testGetRecipes() {
		Recipe recipe = new Recipe();
		HashSet<Recipe> recipeData = new HashSet<>();
		recipeData.add(recipe);
		
		when(recipeRepository.findAll()).thenReturn(recipeData);
		
		Set<Recipe> recipes = recipeService.getRecipes();
		
		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1)).findAll();
	}
	
	@Test
	void getRecipeByIdTestNotFound() {
		Assertions.assertThrows(NotFoundException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {

				Optional<Recipe> recipeOptional = Optional.empty();
				when(recipeRepository.findById(any())).thenReturn(recipeOptional);
				Recipe recipeReturned = recipeService.findById(1l);
			}
		});
		

		
	}
	
	@Test
	void testGetRecipeById() {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		when(recipeRepository.findById(any())).thenReturn(recipeOptional);
		
		Recipe returnedRecipe = recipeService.findById(1L);
		
		assertNotNull("Null recipe returned", returnedRecipe);
		verify(recipeRepository, times(1)).findById(any());
		verify(recipeRepository, never()).findAll();
		
	}
	
	@Test
	void testDeleteById() throws Exception {
		//given
		Long idToDelete = 2L;
		
		//when
		recipeService.deleteById(idToDelete);
		
		//then
		verify(recipeRepository,times(1)).deleteById(any());
	}

}
