package com.camel.camela.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.camel.camela.entity.User;

//@Component
public class RESTProducer extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		restConfiguration()
			.component("servlet")
			//.contextPath("/api2")			// no use - useless
			//.host("localhost")
			//.port(9999)						// not used, as already defined in appl.props
			
			
			.apiContextPath("/api-doc")			// http://localhost:8000/camel/api-doc
			.bindingMode(RestBindingMode.json);
			
		
		rest()
			.path("/hello")						// http://localhost:8000/camel/hello -> http://localhost:8000/api/hello
			.consumes("application/json")
			.produces("application/json")
			
			.get().to("direct:hello")			// Defined in StaticMessageSender.java
			
			.post().type(User.class).outType(User.class).to("bean:upperNameBean")	// POST http://localhost:8000/camel/hello  -> http://localhost:8000/api/hello, Body: { "name": "dsrawat", "salary": 777.2}

		;
		
		
		// Defined in StaticMessageSender.java
		//from("direct:hello")
		//	.transform().constant("Hello world");
			


	}
}

@Component
class UpperNameBean {
	public User convertNameToUpperCase(User user) {
		user.setName(user.getName().toUpperCase());
		return user;
	}
}
