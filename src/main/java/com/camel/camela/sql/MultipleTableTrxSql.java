package com.camel.camela.sql;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camel.camela.entity.CurrencyCreator;
import com.camel.camela.entity.CurrencyExchangeRate;
import com.camel.camela.repo.CurrencyCreatorRepo;
import com.camel.camela.repo.CurrencyExchangeRateRepo;

@Component
public class MultipleTableTrxSql extends RouteBuilder {

	
	@Autowired SQLForTwoTables sqlForTwoTables;
	
	@Override
	public void configure() throws Exception {
		
		// Saving data in multle tables
		
		from("direct:sql-insert")
		.process(exch -> {
			//CurrencyExchangeRate cer = exch.getIn().getBody(CurrencyExchangeRate.class);
			Map<String, Object> maps = new HashMap<>();
			maps.put("id", (int)(Math.random()*100000));
			maps.put("from_curr", "PPP");
			maps.put("to_curr", "QQQ");
			maps.put("rate", 123);
			
			// for table-2
			maps.put("creator", "dharmender");
			
			exch.getIn().setBody(maps);
		})
		.log("${body}")					// {from_curr=PPP, to_curr=QQQ, creator=dharmender, rate=123, id=26487}
		//.to("{{sql.insert}}")
		.transacted()
		.bean(sqlForTwoTables)	
		.log("${body}")
		;
		
		
		from("timer:insert-timer?period=10000&repeatCount=1")
		.log("${body}")
		.to("direct:sql-insert")
		;
		
	}

}


@Component
class SQLForTwoTables {
	
	@Autowired
	CurrencyExchangeRateRepo currExchRepo;
	
	@Autowired CurrencyCreatorRepo currCreateRepo;
	
	boolean throwException = true;
	
	//@Transactional
	public String saveDataInTwoTables(@Body Map<String, Object> body, @Headers Map<String, Object>headers) throws Exception  {
		System.err.println(body);
		//System.err.println(headers);
		
		CurrencyExchangeRate currExch = new CurrencyExchangeRate();
		currExch.setId((int) body.get("id"));
		currExch.setFromCurr((String) body.get("from_curr"));
		currExch.setToCurr((String) body.get("to_curr"));
		currExch.setRate((int) body.get("rate"));
		
		CurrencyCreator currCreator = new CurrencyCreator();
		currCreator.setId((int) body.get("id"));
		currCreator.setCreator((String)body.get("creator"));
		
		currExchRepo.save(currExch);
		
		//if (throwException) {
		//	throw new Exception("Hey! my disk is full!");
		//}
		
		currCreateRepo.save(currCreator);

		return "Data saved in two tables succesfully";
	}
}
