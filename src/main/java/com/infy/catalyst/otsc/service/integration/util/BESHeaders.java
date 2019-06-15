package com.infy.catalyst.otsc.service.integration.util;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BESHeaders {
	
	private final Logger log = LoggerFactory.getLogger(BESHeaders.class);
	 
	public BESHeaders() {
		super();
	}
	
	
	@Handler
	public  void addHeadersToBESModel(Exchange exchange) {		
		log.debug("OfferRouter Processor: Adding headers");
		exchange.getIn().setHeader("Accept-Encoding", "gzip,deflate");
      	exchange.getIn().setHeader("Message-Seq", "201618471525220");
      	exchange.getIn().setHeader("Be-Id", "18");
      	exchange.getIn().setHeader("Region-Id", "18");
      	exchange.getIn().setHeader("Login-System-Code", "esb");
      	exchange.getIn().setHeader("Password", "Abc1234%1Hblsqt!");
      	exchange.getIn().setHeader("Remote-Ip", "127.0.0.1");
      	exchange.getIn().setHeader("Operator-Id", "bes1");
      	exchange.getIn().setHeader("Org-Id", "18");              	
      	exchange.getIn().setHeader("Channel-Type", "601");
      	exchange.getIn().setHeader("Msg-Language-Code", "en_US");
      	exchange.getIn().setHeader("Time-Type", "1");
      	exchange.getIn().setHeader("Content-Type", "application/json");      	
		
	}

}
