package com.llac.tyn.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llac.tyn.model.GeneratedNumber;
import com.llac.tyn.repository.GeneratedNumberRepository;

@Service
@Transactional
public class GeneratedNumberServiceImpl implements GeneratedNumberService {

	private GeneratedNumberRepository generatedNumberRepository;

	public GeneratedNumberServiceImpl() {
	}

	@Autowired
	public GeneratedNumberServiceImpl(GeneratedNumberRepository numberRepository) {
		super();
		this.generatedNumberRepository = numberRepository;
	}

	@Override
	public GeneratedNumber save(GeneratedNumber number) {
		return generatedNumberRepository.save(number);
	}
	
}
