package com.camel.camela.routes;

import java.util.List;

import org.apache.activemq.command.Response;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.camel.camela.entity.User;

//@Component
public class RestRouteGet  extends RouteBuilder {

	@Autowired
	private UserListTransformer userListTransformer; 
	
	@Override
	public void configure() throws Exception {
		
		restConfiguration().host("localhost").port(8000);
		
		from("timer:mytimer?period=10000")
		.log("--------------------------")
		.log("${body}")
		
		.to("rest:get:/users2")
		.unmarshal(new ListJacksonDataFormat(User.class))

		.bean(userListTransformer)
		.log("${body}")
		
		
		
		//.bean(new JsonListTransformer(), "process")
		
		//.unmarshal(new JacksonDataFormat(User.class))
		//.unmarshal()
		//.jacksonXml(User.class)
		//.json(JsonLibrary.Jackson, User.class)
		//.unmarshal().jacksonXml(List.class)
		;

		
		//Route2
		from("timer:timer2?repeatCount=1&period=5000")
		.to("rest:get:/users2/1")
		.unmarshal(new JacksonDataFormat(User.class))
		.marshal().json()
		.log("User: ${body}")
		;
		
	}
}

@Component
class UserListTransformer {
	
	public String transformUserList(List<User> users) {
		final StringBuffer sb = new StringBuffer("\n");
		
		users.forEach(user -> {
			sb.append(user.getId() + "\t" + user.getName() + "\t" + user.getSalary() + "\n");
		});
		
		return sb.toString();
	}
	
}

@Component
class JsonListTransformer  {

	public String process(String body) throws Exception {
		System.err.println("...." + body);
		return body;
		
	}
	
}