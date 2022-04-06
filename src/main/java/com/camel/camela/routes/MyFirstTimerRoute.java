package com.camel.camela.routes;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstTimerRoute extends RouteBuilder {

	@Autowired
	MyDateTimeBean myDTBean;
	
	@Autowired
	MyProcessingBean procBean;
	
	@Override
	public void configure() throws Exception {

//		from("timer:my-timer")
//		.to("log:my-timer");	
		// Generates log msg every seccond
		//Output: 2022-03-23 14:18:03.907  INFO 19496 --- [imer://my-timer] my-timer.log   : Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]

		
		
		from("timer:my-timer")
		.log("------------------------------")
		.log("${body}")
		.transform().constant("Hello world at "+ new Date())
		.log("${body}")
		//.transform().constant("Hello world at "+ LocalDateTime.now())
		//.bean("myDateTimeBean")
		.bean(myDTBean, "currDateTime2")
		.log("${body}")
		.process(new MyProcessor())
		.log("${body}")
		.bean(procBean)
		.log("${body}")
		.to("log:my-timer");

		// Output
		//2022-03-23 14:21:49.305  INFO 22268 --- [imer://my-timer] my-timer : Exchange[ExchangePattern: InOnly, BodyType: String, Body: Hello world]
		//2022-03-23 14:22:33.348  INFO 22268 --- [imer://my-timer] my-timer : Exchange[ExchangePattern: InOnly, BodyType: String, Body: Hello world at Wed Mar 23 14:22:31 IST 2022]
		// Time is same for all msg!!! due to constant() method
		//Bean should have only one method, any return type ... should be convertable to string OR
		// pass the method name as second arg to bean() method
		
		// Final output
//			2022-03-23 15:17:44.303  INFO 19084 --- [imer://my-timer] route1                                   : ------------------------------
//			EXCH: Current datetime2 = 2022-03-23T15:17:44.303716900
//			2022-03-23 15:17:44.303  INFO 19084 --- [imer://my-timer] route1                                   : null
//			2022-03-23 15:17:44.303  INFO 19084 --- [imer://my-timer] route1                                   : Hello world at Wed Mar 23 15:17:41 IST 2022
//			2022-03-23 15:17:44.303  INFO 19084 --- [imer://my-timer] route1                                   : Current datetime2 = 2022-03-23T15:17:44.303716900
//			2022-03-23 15:17:44.303  INFO 19084 --- [imer://my-timer] route1                                   : Current datetime2 = 2022-03-23T15:17:44.303716900
//			2022-03-23 15:17:44.303  INFO 19084 --- [imer://my-timer] c.camel.camela.routes.MyProcessingBean   : BODY: Current datetime2 = 2022-03-23T15:17:44.303716900
//			2022-03-23 15:17:44.303  INFO 19084 --- [imer://my-timer] route1                                   : Current datetime2 = 2022-03-23T15:17:44.303716900
//			2022-03-23 15:17:44.303  INFO 19084 --- [imer://my-timer] my-timer                                 : Exchange[ExchangePattern: InOnly, BodyType: String, Body: Current datetime2 = 2022-03-23T15:17:44.303716900]


	}

}


// Transformation
@Component
class MyDateTimeBean {
	
	public LocalDateTime currDateTime() {
		return LocalDateTime.now();
	}
	
	public String currDateTime2() {
		return "Current datetime2 = " + LocalDateTime.now();
	}
}


// Processing
@Component
class MyProcessingBean {
	
	Logger logger = LoggerFactory.getLogger(MyProcessingBean.class);
	
	public void processMsg(String msg) {
		logger.info("BODY: {}", msg);
	}
	
	public String currDateTime2() {
		return "Current datetime2 = " + LocalDateTime.now();
	}
}

class MyProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		System.err.println("EXCH: " +  exchange.getMessage().getBody());
		
	}
	
}
