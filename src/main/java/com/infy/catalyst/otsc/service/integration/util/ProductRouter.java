package com.infy.catalyst.otsc.service.integration.util;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductRouter extends RouteBuilder {
	private final Logger log = LoggerFactory.getLogger(OfferRouter.class);
	  
    @Override
    public void configure() throws Exception {
    	
    	from("direct:productToBESRoute")
    	.bean(BESProductModel.class) 
    	.bean(BESHeaders.class) 
    	.process(new Processor() {
            public void process(Exchange exchange) throws Exception { 
              	exchange.getIn().setHeader(Exchange.HTTP_METHOD, "POST");
              	log.debug("Calling Product Creation API to BES" + exchange.getIn());
              }
          })    	 
    	.to("https://117.78.47.214:17700/openAPI/httpservices/product-mgmt/v1/products")
    	.process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
	           Integer code = exchange.getIn().getHeader(exchange.HTTP_RESPONSE_CODE, Integer.class);
	            
	           if(code == 200 || code == 201){
	            	log.debug("PRODUCT: " + exchange.getIn() + " Created Succesfully");
	            }
	            else{
	            	log.debug("Failed To create Product: " + exchange.getIn().getBody());
	            }
			}
    		 
    	 });    	  
      
    }

}
