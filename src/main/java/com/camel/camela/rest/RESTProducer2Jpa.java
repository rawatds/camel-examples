package com.camel.camela.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.camel.camela.entity.CurrencyExchangeRate;
import com.camel.camela.entity.User;
import com.camel.camela.service.CurrencyExchangeRateService;

//@Component
public class RESTProducer2Jpa extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		restConfiguration()
			.component("servlet")
			//.contextPath("/api2")			// no use - useless
			//.host("localhost")
			//.port(9999)						// not used, as already defined in appl.props
			
			
			.apiContextPath("/api-doc")			// http://localhost:8000/api/api-docs
			.apiProperty("api.title", "DSR API")
			.bindingMode(RestBindingMode.json);
			
		
		rest()
			.path("/currencies")				// http://localhost:8000/camel/currencies -> http://localhost:8000/api/currencies
			.consumes("application/json")
			.produces("application/json")
			
			.get("/")
				.to("direct:list")
			
			
			.get("/{id}")
				.to("direct:list2")
			
			//.description("Get all Currencies Exch Rates")
						
			
			//.post().type(User.class).outType(User.class).to("bean:upperNameBean")	// POST http://localhost:8000/camel/hello  -> http://localhost:8000/api/hello, Body: { "name": "dsrawat", "salary": 777.2}

		;
		
		
		// Defined in StaticMessageSender.java
		from("direct:list")
		.bean(CurrencyExchangeRateService.class, "getAll");
		
		from("direct:list2")
			.log("${headers}")
			.bean(CurrencyExchangeRateService.class, "getById(${header.id})");
			


	}
}

