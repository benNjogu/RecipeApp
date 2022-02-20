package com.keytech.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.keytech.commands.RecipeCommand;
import com.keytech.exceptions.NotFoundException;
import com.keytech.service.RecipeService;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
		return "recipe/show";
	}
	
	@RequestMapping("recipe/new")
	public String newRecipe(Model model) {
		
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@RequestMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		
		model.addAttribute("recipe", recipeService.findCommandById(new Long(id)));
		return "recipe/recipeform";
	}
	
	@PostMapping("recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		return "redirect:/recipe/"+ savedCommand.getId()+"/show";
	}
	
	@GetMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		
		log.debug("deleting id: "+id);
		recipeService.deleteById(Long.valueOf(id));
		return "redirect:/";
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound() {
		log.error("Handling not found exception");
		ModelAndView modelAndView =  new ModelAndView();
		modelAndView.setViewName("404error");
		return modelAndView;
	}
	
}
