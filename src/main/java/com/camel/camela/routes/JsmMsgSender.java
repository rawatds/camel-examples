package com.camel.camela.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class JsmMsgSender extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("timer:mytimer?period=100000")
		.transform()
		.constant("Hello world")
		.log("${body}")
		.to("activemq:TestQueue");
		
	}

}
