package com.infy.catalyst.otsc.publish.integration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.infy.catalyst.otsc.domain.Offer;
import com.infy.catalyst.otsc.service.OfferService;
import com.infy.catalyst.otsc.service.impl.OfferServiceImpl;

public class TMFProductOfferingPriceModel {

	//private final Logger log = LoggerFactory.getLogger(TMFProductOfferingPriceModel.class);

/*	private String id;
	private String type;
	private String name;
	private String priceType;
	private String recurringChargePeriodType;
	private Map<String, String> unitOfMeasure;
	private Map<String, Object> price;*/

	public TMFProductOfferingPriceModel() {
		super();
	}

	@Handler
	public  void transformOfferToTMFProductOffering(Exchange exchange) {
		Offer inputOffer = (Offer) exchange.getIn().getBody();

/*		TMFProductOfferingPriceModel obj = new TMFProductOfferingPriceModel();
		obj.id = inputOffer.getId();
		obj.type = "ProdOfferPriceCharge";
		
		ArrayList plans = (ArrayList) inputOffer.getOffer_params().get("Plans");
		for (int i=0; i<plans.size();i++) {
			Map<String, Map<String, Object>> plan = (Map<String, Map<String, Object>>) plans.get(i);
			Map<String, Object> basic_pricing_parameters = (Map<String, Object>) plan.get("basic_pricing_parameters");
			obj.name = (String) basic_pricing_parameters.get("name");
			obj.priceType = (String) basic_pricing_parameters.get("priceType");
			obj.recurringChargePeriodType = (String) basic_pricing_parameters.get("recurringChargePeriodType");
			obj.price = new HashMap<String, Object>();
			obj.price.put("amount", basic_pricing_parameters.get("price_amount"));
			obj.price.put("units", "USD");
		}*/

		Map<String, Object> productOfferingPrice = new HashMap<String, Object>();
		productOfferingPrice.put("id", inputOffer.getId());
		productOfferingPrice.put("@type", "ProdOfferPriceCharge");
		
		ArrayList plans = (ArrayList) inputOffer.getOffer_params().get("Plans");
		for (int i=0; i<plans.size();i++) {
			Map<String, Map<String, Object>> plan = (Map<String, Map<String, Object>>) plans.get(i);
			Map<String, Object> basic_pricing_parameters = (Map<String, Object>) plan.get("basic_pricing_parameters");
			productOfferingPrice.put("name", basic_pricing_parameters.get("name"));
			productOfferingPrice.put("priceType", basic_pricing_parameters.get("priceType"));
			productOfferingPrice.put("recurringChargePeriodType", basic_pricing_parameters.get("recurringChargePeriodType"));
			
			HashMap<String, Object> price = new HashMap<String, Object>();
			price.put("amount", basic_pricing_parameters.get("price_amount"));
			price.put("units", "USD");
			productOfferingPrice.put("price", price);
		}
		
		Gson gson = new Gson();
		String tmfProductOfferingPrice = gson.toJson(productOfferingPrice);

		//log.debug("TMFProductOfferingPrice: " + tmfProductOfferingPrice);
		exchange.getIn().setBody(tmfProductOfferingPrice);
	}


}
