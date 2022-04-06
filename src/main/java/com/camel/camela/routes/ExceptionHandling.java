package com.camel.camela.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class ExceptionHandling extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("file:files/input")
		.doTry()
			.log("before processing ...")
			.process(new ExProcessor())
			.log("after processing ...")
		.doCatch(Exception.class) 
			.process((e) -> {
				System.err.println("Exception caught with " + e.getMessage().getBody());
			})
			.log("Everything done!");
		
	}

}

class ExProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		throw new Exception("Sorry I cannot process the message!");
		
	}
	
}