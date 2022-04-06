package com.camel.camela.routes.eip;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class CBRRouting extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		
		from("file:files/input")
		.split()
		.tokenize("\n")
		.to("direct:test");
		
		from("direct:test")
		.choice()
			.when(body().contains("line2"))
				.to("jms:queue:TestQueue2")
			.otherwise()
				.to("activemq:TestQueue");
	}

}
