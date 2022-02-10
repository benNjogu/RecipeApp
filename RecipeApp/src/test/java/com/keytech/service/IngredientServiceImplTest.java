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
import com.keytech.converters.IngredientCommandToIngredient;
import com.keytech.converters.IngredientTongredientCommand;
import com.keytech.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.keytech.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.keytech.domain.Ingredient;
import com.keytech.domain.Recipe;
import com.keytech.repositories.RecipeRepository;
import com.keytech.repositories.UnitOfMeasureRepository;

class IngredientServiceImplTest {

	IngredientTongredientCommand command;
	IngredientCommandToIngredient command2;
	
	@Mock
	RecipeRepository repository;
	
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	IngredientService service;
	
	public IngredientServiceImplTest() {
		super();
		this.command = new IngredientTongredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.command2 = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		service = new IngredientServiceImpl(command, repository, unitOfMeasureRepository, command2);
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
	
	@Test
	public void testRecipeSavedCommand() throws Exception {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);
		
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);
		
		when(repository.findById(any())).thenReturn(recipeOptional);
		when(repository.save(any())).thenReturn(savedRecipe);
		
		//when
		IngredientCommand savedCommand = service.saveIngredientCommand(command);
		
		//then
		assertEquals(Long.valueOf(3L), savedCommand.getId());
		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any(Recipe.class));
		
	}

}
