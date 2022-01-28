package com.keytech.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.keytech.commands.UnitOfMeasureCommand;
import com.keytech.domain.UnitOfMeasure;

class UnitOfMeasureToUnitOfMeasureCommandTest {

	private static final Long ID_LONG = 1L;
	private static final String DESCRIPTION = "description";
	
	UnitOfMeasureToUnitOfMeasureCommand converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new UnitOfMeasureToUnitOfMeasureCommand();
	}
	
	@Test
	void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new UnitOfMeasure()));
	}

	@Test
	void testConvert() {
		//given
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(ID_LONG);
		uom.setDescription(DESCRIPTION);
		
		//when
		UnitOfMeasureCommand command = converter.convert(uom);
		
		//then
		assertNotNull(command);
		assertEquals(ID_LONG, command.getId());
		assertEquals(DESCRIPTION, command.getDescription());
		
	}

}
