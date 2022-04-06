package com.camel.camela.routes;

import java.util.Random;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.camel.camela.entity.User;

//@Component
public class RestRoutePost  extends RouteBuilder {

	@Autowired
	RandomUserGenerator randomUserGenerator;
	
	@Override
	public void configure() throws Exception {
		
		restConfiguration().host("localhost").port(8000);
		
		from("timer:mytimer?period=20000")
		.bean(randomUserGenerator, "getRandomUser")
		.log(">>>>>>> ${body}")
		.marshal().json(true)
		.log("#### ${body}")
		.to("rest:post:/users")
		.log("**** ${body}");
				
	}
	
	
		
	
}

@Component
class RandomUserGenerator {
	public User getRandomUser() {
		return new User(null, getRandomUserName(), new Random().nextDouble()*1000);
	}
	
	private String getRandomUserName() {

	    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();

	    // specify length of random string
	    int length = 8;

	    for(int i = 0; i < length; i++) {
	      int index = random.nextInt(alphabet.length());
	      char randomChar = alphabet.charAt(index);
	      sb.append(randomChar);
	    }

	    String randomString = sb.toString().toLowerCase();
	    return randomString;
	}

}
