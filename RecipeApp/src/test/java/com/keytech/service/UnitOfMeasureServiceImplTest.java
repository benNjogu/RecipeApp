package com.keytech.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.keytech.commands.UnitOfMeasureCommand;
import com.keytech.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.keytech.domain.UnitOfMeasure;
import com.keytech.repositories.UnitOfMeasureRepository;

class UnitOfMeasureServiceImplTest {

	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	
	UnitOfMeasureService service;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	void testListAllUOMs() {
		//given
		Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		unitOfMeasures.add(uom1);
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		unitOfMeasures.add(uom2);
		
		when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
		
		//when
		Set<UnitOfMeasureCommand> commands = service.listAllUOMs();
		
		//then
		assertEquals(2, commands.size());
		verify(unitOfMeasureRepository, times(1)).findAll();
		
	}

}
