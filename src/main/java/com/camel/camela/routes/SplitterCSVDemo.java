package com.camel.camela.routes;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class SplitterCSVDemo extends RouteBuilder {

	@Autowired
	MySplitter mySplitter;
	
	@Override
	public void configure() throws Exception {

		errorHandler(deadLetterChannel("activemq:dlq"));
		getContext().setTracing(true);
		
		from("file:files/csv")
		.routeId("My-CSV-File-Route") 
		//.split(body())		-- whole file as a single text
		.unmarshal().csv()
		.split(body())		//	n different lines
		.to("activemq:csvQ")
		.wireTap("activemq:wiretap")
		.to("log:csvlog")
		//.to("file:files/output");		-- use of errorHandler
		;
		
		// Splitting a text tokens into lines
		from("file:files/input")
		.split(method(mySplitter))
		.log("${body}")
		;
		
		
		
	}
}

@Component
class MySplitter {
	public List splitter (String body) {
		return Arrays.asList(body.split(" "));
	}
}
