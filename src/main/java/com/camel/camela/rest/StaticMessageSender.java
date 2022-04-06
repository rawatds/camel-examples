package com.camel.camela.rest;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class StaticMessageSender  extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("direct:hello")
			.transform().constant("Hello world");

	}

}
