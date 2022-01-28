package com.keytech.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.keytech.commands.CategoryCommand;
import com.keytech.domain.Category;

@ExtendWith(MockitoExtension.class)
class CategoryToCategoryCommandTest {

	private static final Long ID = 1L;
	private static final String DESCRIPTION = "description";
	
	CategoryToCategoryCommand converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new CategoryToCategoryCommand();
	}

	@Test
	void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new Category()));
	}
	
	@Test
	void testConvert() {
		//given
		Category category = new Category();
		category.setId(ID);
		category.setDescription(DESCRIPTION);
		
		//when
		CategoryCommand command = converter.convert(category);
		
		//then
		assertEquals(ID, command.getId());
		assertEquals(DESCRIPTION, command.getDescription());
		
	}

}
