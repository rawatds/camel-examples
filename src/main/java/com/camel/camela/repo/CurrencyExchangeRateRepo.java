package com.camel.camela.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camel.camela.entity.CurrencyExchangeRate;

public interface CurrencyExchangeRateRepo extends JpaRepository<CurrencyExchangeRate, Integer>{

}
