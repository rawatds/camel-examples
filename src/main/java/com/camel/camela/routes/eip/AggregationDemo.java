package com.camel.camela.routes.eip;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.camel.camela.entity.CurrencyExchangeRate;


//@Component
public class AggregationDemo extends RouteBuilder {

	
	@Override
	public void configure() throws Exception {

		from("file:files/json")
		.routeId("aggregate-route")
		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchangeRate.class)
		.aggregate(simple("${body.to}"), new MyAggregationStrategy())
		.completionSize(3)
		.completionTimeout(10000)
		.to("log:aggregate-log");
		
	}

}

class MyAggregationStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

		List<Object> list = null;
		Object obj = newExchange.getIn().getBody();
		
		if (oldExchange == null) {
			list = new ArrayList<Object>();
			list.add(obj);
			newExchange.getIn().setBody(list);
			return newExchange;
		} else {
			list = oldExchange.getIn().getBody(ArrayList.class);
			list.add(obj);
			return oldExchange;
		}
	}

}