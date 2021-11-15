package com.keytech.repositories;

import org.springframework.data.repository.CrudRepository;

import com.keytech.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{

}
