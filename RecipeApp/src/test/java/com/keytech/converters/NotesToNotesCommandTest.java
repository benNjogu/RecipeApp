package com.keytech.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.keytech.commands.NotesCommand;
import com.keytech.domain.Notes;

class NotesToNotesCommandTest {

	public static final Long ID_VALUE = 1L;
	public static final String RECIPE_NOTES = "Notes";
	NotesToNotesCommand converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new NotesToNotesCommand();
	}

	@Test
	public void testNull() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Notes()));
	}

	@Test
	void testConvert() {
		// given
		Notes notes = new Notes();
		notes.setId(ID_VALUE);
		notes.setRecipeNotes(RECIPE_NOTES);

		// when
		NotesCommand notesCommand = converter.convert(notes);

		// then
		assertEquals(ID_VALUE, notesCommand.getId());
		assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
	}

}
