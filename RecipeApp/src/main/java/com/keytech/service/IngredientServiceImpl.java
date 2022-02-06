package com.keytech.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.keytech.commands.IngredientCommand;
import com.keytech.converters.IngredientTongredientCommand;
import com.keytech.domain.Recipe;
import com.keytech.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

	private final IngredientTongredientCommand ingredientTongredientCommand;
	private final RecipeRepository recipeRepository;
	
	public IngredientServiceImpl(IngredientTongredientCommand ingredientTongredientCommand,
			RecipeRepository recipeRepository) {
		super();
		this.ingredientTongredientCommand = ingredientTongredientCommand;
		this.recipeRepository = recipeRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdandIngredientId(Long recipeId, Long ingredientId) {
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if (!recipeOptional.isPresent()) {
			//todo impl error handling
			log.error("recipe id not found. id: "+recipeId);
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientTongredientCommand.convert(ingredient)).findFirst();
		
		if (!ingredientCommandOptional.isPresent()) {
			//todo impl error handling
			log.error("ingredient is not found: "+ingredientId);
		}
		
		return ingredientCommandOptional.get();
	}

}
