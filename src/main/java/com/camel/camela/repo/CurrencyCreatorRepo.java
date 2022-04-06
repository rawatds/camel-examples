package com.camel.camela.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camel.camela.entity.CurrencyCreator;

public interface CurrencyCreatorRepo extends JpaRepository<CurrencyCreator, Integer>{

}
