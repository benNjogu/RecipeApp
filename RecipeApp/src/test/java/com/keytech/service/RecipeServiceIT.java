package com.keytech.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.keytech.RecipeApp.RecipeAppApplication;
import com.keytech.commands.RecipeCommand;
import com.keytech.converters.RecipeCommandToRecipe;
import com.keytech.converters.RecipeToRecipeCommand;
import com.keytech.domain.Recipe;
import com.keytech.repositories.RecipeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes={RecipeAppApplication.class})
class RecipeServiceIT {

	public static final String NEW_DESCRIPTION = "New Description";
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Autowired
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Transactional
	@Test
	void testSaveRecipeDescription() {
		//given
		Iterable<Recipe> recipes = recipeRepository.findAll();
		Recipe testRecipe = recipes.iterator().next();
		RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);
		
		//when
		testRecipeCommand.setDescription(NEW_DESCRIPTION);
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);
		
		//then
		assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
		assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
		assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
		assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
		
	}

}
