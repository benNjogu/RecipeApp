package com.keytech.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keytech.commands.RecipeCommand;
import com.keytech.converters.RecipeCommandToRecipe;
import com.keytech.converters.RecipeToRecipeCommand;
import com.keytech.domain.Recipe;
import com.keytech.exceptions.NotFoundException;
import com.keytech.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("I'm in the service");
		
		Set<Recipe> recipes = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}

	@Override
	public Recipe findById(Long l) {
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(l);
		if (!recipeOptional.isPresent()) { 
			throw new NotFoundException("Recipe Not Found, for 	ID value: " + l.toString());
		}
		
		return recipeOptional.get();
	}
	
	@Override
	@Transactional
	public RecipeCommand findCommandById(Long l) {
		
		return recipeToRecipeCommand.convert(findById(l));
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		log.debug("SavedRecipeId:"+savedRecipe.getId());
		
		return recipeToRecipeCommand.convert(savedRecipe);
	}

	@Override
	public void deleteById(Long l) {
		recipeRepository.deleteById(l);
	}

}
