package com.keytech.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.keytech.commands.NotesCommand;
import com.keytech.domain.Notes;

class NotesCommandToNotesTest {

	public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Notes";
	
	NotesCommandToNotes converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new NotesCommandToNotes();
	}

	@Test
	void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new NotesCommand()));
	}
	
	@Test
	void testConvert() {
		//given
		NotesCommand command = new NotesCommand();
		command.setId(ID_VALUE);
		command.setRecipeNotes(RECIPE_NOTES);
		
		//when
		Notes notes = converter.convert(command);
		
		//then
		assertEquals(ID_VALUE, notes.getId());
		assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
		
	}

}
