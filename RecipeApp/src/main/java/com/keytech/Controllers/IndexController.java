package com.keytech.Controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keytech.domain.Category;
import com.keytech.domain.UnitOfMeasure;
import com.keytech.repositories.CategoryRepository;
import com.keytech.repositories.UnitOfMeasureRepository;

@Controller
public class IndexController {

	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@RequestMapping({"", "/", "index", "index.html"})
	public String getIndexPage() {
		
		Optional<Category> categoryOptional = categoryRepository.findByDescription("kenyan😅️");
		Optional<UnitOfMeasure> unitOptional = unitOfMeasureRepository.findByDescription("Ounce");
		
		System.out.println("Cat id is "+categoryOptional.get().getId());
		System.out.println("UOM id is "+unitOptional.get().getId());
		
		return "index";
	}
	
}
