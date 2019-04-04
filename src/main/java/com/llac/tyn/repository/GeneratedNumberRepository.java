package com.llac.tyn.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.llac.tyn.model.GeneratedNumber;

@Repository
public interface GeneratedNumberRepository extends CrudRepository<GeneratedNumber, Long> {

}
