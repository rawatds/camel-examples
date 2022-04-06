package com.camel.camela.routes.eip;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class RoutingSlipDemo extends RouteBuilder {

	
	@Override
	public void configure() throws Exception {
		
		String routingSlip = "direct:endpoint1,direct:endpoint4";		// Change these to see the effect
		
		from("direct:endpoint1").to("log:ep1");
		from("direct:endpoint2").to("log:ep2");
		from("direct:endpoint3").to("log:ep3");
		from("direct:endpoint4").to("log:ep4");

		from("timer:routingSlipTimer?period=10000")
		.routingSlip(simple(routingSlip));
		
		
	}

}