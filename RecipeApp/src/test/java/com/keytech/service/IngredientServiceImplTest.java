package com.keytech.service;

import static org.mockito.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.keytech.commands.IngredientCommand;
import com.keytech.converters.IngredientTongredientCommand;
import com.keytech.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.keytech.domain.Ingredient;
import com.keytech.domain.Recipe;
import com.keytech.repositories.RecipeRepository;

class IngredientServiceImplTest {

	IngredientTongredientCommand command;
	
	@Mock
	RecipeRepository repository;
	
	IngredientService service;
	
	public IngredientServiceImplTest() {
		super();
		this.command = new IngredientTongredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		service = new IngredientServiceImpl(command, repository);
	}

	@Test
	void testFindByRecipeIdandIngredientId() {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);
		
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		when(repository.findById(any())).thenReturn(recipeOptional);
		
		//when
		IngredientCommand ingredientCommand = service.findByRecipeIdandIngredientId(1L, 3L);
		
		//then
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(repository, times(1)).findById(any());
		
	}

}
