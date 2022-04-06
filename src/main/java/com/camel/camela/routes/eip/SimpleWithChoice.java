package com.camel.camela.routes.eip;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class SimpleWithChoice extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		
		from("file:files/input")
		.routeId("My-File-Route")
		.log("${file:ext}")
		.transform().body(String.class)
		.choice()
			.when(simple("${file:ext} ends with 'xml'"))
				.log("File is xml: ${file:name}")
			.when(simple("${body} contains 'USD'"))
				.log("Non-Xml but contains USD")
			.otherwise()
				.log("Non-XML file")
		.end()
		.to("file:files/output");
	}


}
