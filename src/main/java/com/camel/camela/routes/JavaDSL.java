package com.camel.camela.routes;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class JavaDSL {

	
	public static void main(String[] args) {
		
		CamelContext camelCtx = new DefaultCamelContext();
		
//		FileRoutes fileRoute = new FileRoutes();
//		try {
//			camelCtx.addRoutes(fileRoute);
//			camelCtx.start();
//			Thread.sleep(2 * 60 * 1000);
//			camelCtx.stop();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
		 //configure jms component        
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.6.112:61616");
//        camelCtx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
//		
	}
}


class FileRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:files/input")
		.log("${body}")
		.to("file:files/output")
		.log("file moved...");
		
	}
	
}

class JMSRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("jms:queue:TestQueue")
		.log("${body}")
		.to("file:files/output")
		.log("file copied from message queue...");
		
	}
	
}