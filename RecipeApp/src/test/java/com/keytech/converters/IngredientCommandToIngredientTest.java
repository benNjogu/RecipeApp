package com.keytech.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.keytech.commands.IngredientCommand;
import com.keytech.commands.UnitOfMeasureCommand;
import com.keytech.domain.Ingredient;
import com.keytech.domain.UnitOfMeasure;

@ExtendWith(MockitoExtension.class)
class IngredientCommandToIngredientTest {

	private static final Long ID = 1L;
	private static final String DESCRIPTION = "description";
	private static final BigDecimal AMOUNT =  new BigDecimal(1);
	private static final Long UOM_ID = new Long(2L);
	
	IngredientCommandToIngredient converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}
	
	@Test
	void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new IngredientCommand()));
	}

	@Test
	void testConvert() {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(ID);
		command.setDescription(DESCRIPTION);
		command.setAmount(AMOUNT);
		UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
		uom.setId(UOM_ID);
		command.setUnitOfMeasure(uom);
		
		//when
		Ingredient ingredient = converter.convert(command);
		
		//then
		assertNotNull(ingredient);
        assertNotNull(ingredient.getUnitOfMeasure());
		assertEquals(ID, ingredient.getId());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		
	}
	
	@Test
	void testConvertWithNullUOM() {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(ID);
		command.setDescription(DESCRIPTION);
		command.setAmount(AMOUNT);
		UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
		
		//when
		Ingredient ingredient = converter.convert(command);
		
		//then
		assertNotNull(ingredient);
        assertNull(ingredient.getUnitOfMeasure());
		assertEquals(ID, ingredient.getId());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		
	}

}
