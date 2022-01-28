package com.keytech.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.keytech.commands.CategoryCommand;
import com.keytech.domain.Category;

@ExtendWith(MockitoExtension.class)
class CategoryCommandToCategoryTest {

	private static final Long ID = 1L;
	private static final String DESCRIPTION = "description";
	
	CategoryCommandToCategory converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new CategoryCommandToCategory();
	}

	@Test
	void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new CategoryCommand()));
	}
	
	@Test
	void testConvert() {
		//given
		CategoryCommand command = new CategoryCommand();
		command.setId(ID);
		command.setDescription(DESCRIPTION);
		
		//when
		Category category = converter.convert(command);
		
		//then
		assertEquals(ID, category.getId());
		assertEquals(DESCRIPTION, category.getDescription());
		
	}

}
