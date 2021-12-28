package com.keytech.Controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keytech.domain.Category;
import com.keytech.domain.UnitOfMeasure;
import com.keytech.repositories.CategoryRepository;
import com.keytech.repositories.UnitOfMeasureRepository;
import com.keytech.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {

	private RecipeService recipeService;
	
	public IndexController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}


	@RequestMapping({"", "/", "index", "index.html"})
	public String getIndexPage(Model model) {
		log.debug("Getting Index Page");
		model.addAttribute("recipes", recipeService.getRecipes());
		return "index";
	}
	
}
