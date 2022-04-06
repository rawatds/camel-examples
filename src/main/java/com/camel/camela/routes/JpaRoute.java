package com.camel.camela.routes;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class JpaRoute extends RouteBuilder {

	@Autowired
	RandomUserGenerator randomUserGenerator;
	
	@Override
	public void configure() throws Exception {
		

		// List all products from a db table
		from("jpa:CurrencyExchangeRateddd?namedQuery=to_inr_currencies&delay=10000&consumeDelete=false")
		.routeId("read-all-to-inr-currency-exch-rates")
		//.wireTap("log:wiretap")
		
		.bean(CurrExchConv2String.class)
		.to("log:mylog")
		
		
		//.process(new DB2CurrExchConv())
		//.log("CurrExchRates: ${body.id}, ${body.fromCurr}, ${body.toCurr}, ${body.rate} ")
		;
	}
	

}


class DB2CurrExchConv implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		System.err.println(exchange.getIn().getBody());
		
	}
	
}

@Component
class CurrExchConv2String  {

	
	public String convert(@Body Object body) {
		System.err.println(">>>" + body);
		return body.toString();
		
	}
	
}