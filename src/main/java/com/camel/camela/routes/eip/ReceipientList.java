package com.camel.camela.routes.eip;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class ReceipientList extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		
		from("file:files/input")
		.split()
		.tokenize("\n")
		.to("direct:test");
		
		from("direct:test")
		.wireTap("activemq:WireTapQ")
		.process((exch) -> {
			String recipient = exch.getIn().getBody().toString();
			String receipientQueue = "activemq:" + recipient;
			exch.getIn().setHeader("queue", receipientQueue);
		})
		.recipientList(header("queue"));


	}

}
