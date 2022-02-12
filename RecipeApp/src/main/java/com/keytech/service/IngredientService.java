package com.keytech.service;

import com.keytech.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdandIngredientId(Long recipeId, Long ingredientId);
	
	IngredientCommand saveIngredientCommand(IngredientCommand command);
	
	void deleteById(Long recipeId, Long ingredientId);
}
