package com.keytech.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keytech.commands.IngredientCommand;
import com.keytech.converters.IngredientCommandToIngredient;
import com.keytech.converters.IngredientTongredientCommand;
import com.keytech.domain.Ingredient;
import com.keytech.domain.Recipe;
import com.keytech.repositories.RecipeRepository;
import com.keytech.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

	private final IngredientTongredientCommand ingredientTongredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(IngredientTongredientCommand ingredientTongredientCommand,
			RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
		super();
		this.ingredientTongredientCommand = ingredientTongredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
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

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		if (!recipeOptional.isPresent()) {
			log.error("Recipe not found for id: " + command.getRecipeId());
			return new IngredientCommand();
		}else {
			
			Recipe recipe = recipeOptional.get();
			Optional<Ingredient> ingredientOptional = recipe
					.getIngredients()
					.stream()
					.filter(ingrediet -> ingrediet.getId().equals(command.getId()))
					.findFirst();
			
			if (ingredientOptional.isPresent()) {
				
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setAmount(command.getAmount());
				ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
						.findById(command.getUom().getId())
						.orElseThrow(() -> new RuntimeException("UOM not found")));
			}else {
				//add new ingredient
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
				
			}
			
			Recipe savedRecipe = recipeRepository.save(recipe);
			
			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
									.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
									.findFirst();
			
			//Check by description
			if (!savedIngredientOptional.isPresent()) {
				//not totally safe but best guess
				savedIngredientOptional = savedRecipe.getIngredients().stream()
						.filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
						.filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
						.filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(command.getUom().getId()))
						.findFirst();
			}
			
			//To do failure check
			return ingredientTongredientCommand.convert(savedIngredientOptional.get());
			
		}
		
	}

}
