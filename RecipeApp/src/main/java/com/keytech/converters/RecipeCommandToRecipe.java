package com.keytech.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.keytech.commands.CategoryCommand;
import com.keytech.commands.IngredientCommand;
import com.keytech.commands.NotesCommand;
import com.keytech.commands.RecipeCommand;
import com.keytech.domain.Recipe;

import lombok.Synchronized;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe>{

	private NotesCommandToNotes notesConverter;
	private IngredientCommandToIngredient ingredientConverter;
	private CategoryCommandToCategory categoryConverter;

	public RecipeCommandToRecipe(NotesCommandToNotes notesConverter, IngredientCommandToIngredient ingredientConverter,
			CategoryCommandToCategory categoryConverter) {
		super();
		this.notesConverter = notesConverter;
		this.ingredientConverter = ingredientConverter;
		this.categoryConverter = categoryConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		}
		
		final Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setDescription(source.getDescription());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setCookTime(source.getCookTime());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setDirections(source.getDirections());
		recipe.setImage(source.getImage());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setNotes(notesConverter.convert(source.getNotes()));
		
		if (source.getIngredients() != null && source.getIngredients().size() > 0) {
			source.getIngredients().forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
		}
		
		if (source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories().forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
		}
		
		return recipe;
	}

	
}
