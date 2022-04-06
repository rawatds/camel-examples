package com.camel.camela.sql;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.builder.RouteBuilder;

//@Component
public class SelectSQL extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// Get all Records
		from("timer:sql-timer-all?period=10000")
		.routeId("sql-select-all")
		.to("{{sql.select.all}}")
		.log("${body}");
		
		System.out.println("---------------------------");
		
		
		// Get selected Records
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("toCurrParam", "INR");
		
		from("timer:sql-timer?period=10000")
		.routeId("sql-select")
		.setHeader("toCurrParam", constant("INR"))	// Caused by: java.sql.SQLException: Number of parameters mismatch. Expected: 1, was: 0
		//.setBody(params)	// java.sql.SQLException: Invalid argument value: java.io.NotSerializableException
		
		.to("{{sql.select}}")
		.log("${body}");
		
		
		
		from("direct:sql-insert")
		.process(exch -> {
			//CurrencyExchangeRate cer = exch.getIn().getBody(CurrencyExchangeRate.class);
			Map<String, Object> maps = new HashMap<>();
			maps.put("id", (int)(Math.random()*100000));
			maps.put("from_curr", "XXX");
			maps.put("to_curr", "YYY");
			maps.put("rate", 123);
			
			exch.getIn().setBody(maps);
		})
		.log("${body} - ${headers}")
		.to("{{sql.insert}}")
		.log("${body}")
		;
		
		
		from("timer:insert-timer?period=60000")
		.log("${body}")
		.to("direct:sql-insert")
		;
		
	}

}

