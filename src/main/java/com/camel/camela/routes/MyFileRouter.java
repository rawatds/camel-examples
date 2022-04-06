package com.camel.camela.routes;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class MyFileRouter extends RouteBuilder{

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	
	@Override
	public void configure() throws Exception {

		from("file:files/input")
		.log("${body}")
		.process((Exchange exch) -> {
			String originalFileName = (String) exch.getIn().getHeader(Exchange.FILE_NAME, String.class);

			exch.getIn().setHeader(Exchange.FILE_NAME, sdf.format(new Date()) + "_" +originalFileName);
		})
		.to("file:files/output")
		.log("file moved...");
		
		
		from("file:files/xml")
		.log("${body}")
		.process((Exchange exch) -> {
			String originalFileName = (String) exch.getIn().getHeader(Exchange.FILE_NAME, String.class);

			exch.getIn().setHeader(Exchange.FILE_NAME, sdf.format(new Date()) + "_" +originalFileName);
		})
		.to("file:files/output")
		.log("file moved...");
	}
	

}
