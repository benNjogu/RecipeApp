package com.keytech.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keytech.commands.IngredientCommand;
import com.keytech.domain.UnitOfMeasure;
import com.keytech.service.IngredientService;
import com.keytech.service.RecipeService;
import com.keytech.service.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;
	
	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService unitOfMeasureService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("Getting ingredients lists for recipe id: "+recipeId);
	
		//use command objects to avoid lazy loads errors in thymeleaf
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/ingredient/list";
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredients(@PathVariable String recipeId,@PathVariable String id,
			Model model) {
		
		model.addAttribute("ingredient", ingredientService.findByRecipeIdandIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		return "recipe/ingredient/show";
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId,@PathVariable String id,
			Model model) {
		
		model.addAttribute("ingredient", ingredientService.findByRecipeIdandIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		model.addAttribute("uomList", unitOfMeasureService.listAllUOMs());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping
	@RequestMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		log.debug("saved recipe id: " + savedCommand.getRecipeId());
		log.debug("saved ingredient id: " + savedCommand.getId());
		
		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
	}
	
}
