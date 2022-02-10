package com.keytech.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.keytech.commands.UnitOfMeasureCommand;
import com.keytech.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.keytech.repositories.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand) {
		super();
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureCommand;
	}

	@Override
	public Set<UnitOfMeasureCommand> listAllUOMs() {
		
		return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
							.map(unitOfMeasureToUnitOfMeasureCommand::convert)
							.collect(Collectors.toSet());
	}

}
