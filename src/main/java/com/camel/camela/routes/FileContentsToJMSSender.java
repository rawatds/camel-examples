package com.camel.camela.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class FileContentsToJMSSender extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("file:files/input")
		.split()
		.tokenize("\n")
		.log(">> ${body}")
		.to("activemq:TestQueue");
				
	}

}
