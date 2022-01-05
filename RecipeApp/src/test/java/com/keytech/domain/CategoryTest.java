package com.keytech.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {

	Category category;
	
	@BeforeEach
	public void setUp() {
		category = new Category();
	}
	
	@Test
	void testGetId() {
		Long idValue = 4L;
		category.setId(idValue);
		
		assertEquals(idValue, category.getId());
	}

	@Test
	void testGetDescription() {
		String description = "Tanzanian Category";
		category.setDescription(description);
		assertEquals(description, category.getDescription());
	}

	@Test
	void testGetRecipes() {

		Set<Recipe> recipeSet = new HashSet<>();
		Recipe recipe = new Recipe();
		recipeSet.add(recipe);
		
		category.setRecipes(recipeSet);
		assertEquals(recipeSet.size(), 1);
		
	}

}
