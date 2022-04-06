package com.camel.camela.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class JmsMsgReceiver extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("activemq:TestQueue")
		.log("READ: ${body}")
		.to("file:/c:/tmp/queues.txt")
		.to("log:mylog")
		//.wait(20000L);
		;
	}

}
