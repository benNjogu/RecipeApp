package com.keytech.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.keytech.commands.IngredientCommand;
import com.keytech.domain.Ingredient;

import lombok.Synchronized;

@Component
public class IngredientTongredientCommand implements Converter<Ingredient, IngredientCommand>{

	private UnitOfMeasureToUnitOfMeasureCommand uomConverter;

	public IngredientTongredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
		super();
		this.uomConverter = uomConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient source) {
		
		if (source == null) {
			return null;
		}
		
		final IngredientCommand command = new IngredientCommand();
		command.setId(source.getId());
		command.setDescription(source.getDescription());
		command.setAmount(source.getAmount());
		command.setUom(uomConverter.convert(source.getUnitOfMeasure()));
		return command;
	}

}
