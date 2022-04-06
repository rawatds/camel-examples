package com.camel.camela.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camel.camela.entity.CurrencyExchangeRate;
import com.camel.camela.repo.CurrencyExchangeRateRepo;

@Service
public class CurrencyExchangeRateService {
	
	@Autowired
	CurrencyExchangeRateRepo currExchRateRepo;
	
	
	public CurrencyExchangeRate getById(Integer id) {
		return currExchRateRepo.findById(id).get();
	}

	public List<CurrencyExchangeRate> getAll() {
		return currExchRateRepo.findAll();
	}
	
	public CurrencyExchangeRate saveCurrExchRate(CurrencyExchangeRate newRate) {
		return currExchRateRepo.save(newRate);
	}
}
