package com.keytech.commands;

import java.util.HashSet;
import java.util.Set;

import com.keytech.domain.Category;
import com.keytech.domain.Difficulty;
import com.keytech.domain.Ingredient;
import com.keytech.domain.Notes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

	private Long id;
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	private String directions;
	private Byte[] image;
	private Difficulty difficulty;
	private NotesCommand notes;
	private Set<IngredientCommand> ingredients = new HashSet<>();
	private Set<CategoryCommand> categories = new HashSet<>();
}
