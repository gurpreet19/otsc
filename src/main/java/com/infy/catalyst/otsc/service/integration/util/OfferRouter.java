package com.infy.catalyst.otsc.service.integration.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpClientConfigurer;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.http.SSLContextParametersSecureProtocolSocketFactory;
import org.apache.camel.util.jsse.KeyManagersParameters;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.apache.camel.util.jsse.TrustManagersParameters;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.infy.catalyst.otsc.domain.Offer;
import com.infy.catalyst.otsc.service.impl.OfferServiceImpl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class OfferRouter extends RouteBuilder {
	
	
	private final Logger log = LoggerFactory.getLogger(OfferRouter.class);
	  
    @Override
    public void configure() throws Exception {
    	
    	from("direct:offerToBESRoute")
    	.bean(BESOfferingModel.class) 
    	.bean(BESHeaders.class) 
    	.process(new Processor() {
            public void process(Exchange exchange) throws Exception { 
              	exchange.getIn().setHeader(Exchange.HTTP_METHOD, "POST");//You can add other http commands/verbs here like PATCH etc
              	log.debug("Calling Offer Creation API to BES" + exchange.getIn());
              }
          })    	 
    	.to("https://117.78.47.214:17700/openAPI/httpservices/product-mgmt/v1/offerings")
        //.to("http://localhost:3000/offerings")  //for debugging with local json-server
    	 .process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
	           Integer code = exchange.getIn().getHeader(exchange.HTTP_RESPONSE_CODE, Integer.class);
	            
	           if(code == 200 || code == 201){
	            	log.debug("OFFER: " + exchange.getIn() + " Created Succesfully");
	            }
	            else{
	            	log.debug("Failed To create Offer: " + exchange.getIn().getBody());
	            }
			}
    		 
    	 });    	  
      
    }
   
}
