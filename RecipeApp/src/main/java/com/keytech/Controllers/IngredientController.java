package com.keytech.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keytech.commands.IngredientCommand;
import com.keytech.commands.RecipeCommand;
import com.keytech.commands.UnitOfMeasureCommand;
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

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("Getting ingredients lists for recipe id: "+recipeId);
	
		//use command objects to avoid lazy loads errors in thymeleaf
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/ingredient/list";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredients(@PathVariable String recipeId,@PathVariable String id,
			Model model) {
		
		model.addAttribute("ingredient", ingredientService.findByRecipeIdandIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		return "recipe/ingredient/show";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId,@PathVariable String id,
			Model model) {
		
		model.addAttribute("ingredient", ingredientService.findByRecipeIdandIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		model.addAttribute("uomList", unitOfMeasureService.listAllUOMs());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/new")
	public String newRecipe(@PathVariable String recipeId, Model model) {
		
		//Make sure we have a good id value
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		//todo raise exception if null
		
		//need to return back parent id for hidden form property
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(Long.valueOf(recipeId));
		model.addAttribute("ingredient", ingredientCommand);
		
		//unit uom
		ingredientCommand.setUom(new UnitOfMeasureCommand());
		
		model.addAttribute("uomList", unitOfMeasureService.listAllUOMs());
		
		return "recipe/ingredient/ingredientform";
		
	}
	
	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		log.debug("saved recipe id: " + savedCommand.getRecipeId());
		log.debug("saved ingredient id: " + savedCommand.getId());
		
		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {
		log.debug("deleting ingredient id " + id);
		ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}

}
