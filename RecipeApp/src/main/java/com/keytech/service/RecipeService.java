package com.keytech.service;

import java.util.Set;

import com.keytech.commands.RecipeCommand;
import com.keytech.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(Long l);
	
	RecipeCommand saveRecipeCommand(RecipeCommand command);
}
