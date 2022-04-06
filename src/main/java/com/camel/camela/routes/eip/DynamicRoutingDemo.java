package com.camel.camela.routes.eip;

import org.apache.camel.Body;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class DynamicRoutingDemo extends RouteBuilder {

	@Autowired
	private MyDynamicRouterBean myDynamicRouterBean;
	
	@Override
	public void configure() throws Exception {
		
		from("direct:endpoint1").to("log:ep1");
		from("direct:endpoint2").to("log:ep2");
		from("direct:endpoint3").to("log:ep3");
		from("direct:endpoint4").to("log:ep4");

		from("timer:routingSlipTimer?period=10000")
		.transform().constant("Hello world")
		.dynamicRouter(method(myDynamicRouterBean));
		
		
	}

}

@Component
class MyDynamicRouterBean {
	
	int invocations = 0;
	
	public String getNextEndpoint(@Body String body) {
		
		invocations++;
		
		if (invocations % 4 == 1)
			return "direct:endpoint1";
		else if (invocations % 4 == 2)
			return "direct:endpoint2";
		else if (invocations % 4 == 3)
			return "direct:endpoint3";
		else return null;
	}
	
}