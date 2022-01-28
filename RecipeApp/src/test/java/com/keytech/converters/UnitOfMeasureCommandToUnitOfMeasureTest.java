package com.keytech.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.keytech.commands.UnitOfMeasureCommand;
import com.keytech.domain.UnitOfMeasure;

class UnitOfMeasureCommandToUnitOfMeasureTest {


    public static final Long ID = 1L;
	public static final String DESCRIPTION = "description";
	
	UnitOfMeasureCommandToUnitOfMeasure converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new UnitOfMeasureCommandToUnitOfMeasure();
	}

	@Test
	void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new UnitOfMeasureCommand()));
	}
	
	@Test
	void testConvert() {
		//given
		UnitOfMeasureCommand command = new UnitOfMeasureCommand();
		command.setId(ID);
		command.setDescription(DESCRIPTION);
		
		//when
		UnitOfMeasure uom = converter.convert(command);
		
		//then
		assertNotNull(uom);
		assertEquals(ID, uom.getId());
		assertEquals(DESCRIPTION, uom.getDescription());
		
	}

}
