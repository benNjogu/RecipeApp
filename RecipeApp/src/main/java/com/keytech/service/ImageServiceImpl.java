package com.keytech.service;

import java.io.IOException;
import java.util.Iterator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.keytech.domain.Recipe;
import com.keytech.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

	private final RecipeRepository recipeRepository;
	
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Transactional
	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {
		
		try {
			
			Recipe recipe = recipeRepository.findById(recipeId).get();
			Byte[] bytesObjects = new Byte[file.getBytes().length];
			
			int i = 0;
			
			for (Byte b : file.getBytes()) {
				bytesObjects[i++] = b;
			}
			
			recipe.setImage(bytesObjects);
			recipeRepository.save(recipe);
			
		} catch (IOException e) {
			//TODO handle better
			log.debug("An error occured ", e);
			e.getStackTrace();
		}
	}

}
