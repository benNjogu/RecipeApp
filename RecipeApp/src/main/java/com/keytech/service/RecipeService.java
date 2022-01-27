package com.keytech.service;

import java.util.Set;

import com.keytech.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(Long l);
	
}
