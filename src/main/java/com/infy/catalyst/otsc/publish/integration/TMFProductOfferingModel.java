package com.infy.catalyst.otsc.publish.integration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.infy.catalyst.otsc.domain.Offer;
import com.infy.catalyst.otsc.repository.ProductRepository;

public class TMFProductOfferingModel {

	//private final Logger log = LoggerFactory.getLogger(TMFProductOfferingModel.class);

/*	private String id;
	private String name;
	private String description;
	private Boolean isBundle;
	private String isSellable;
	private String lastUpdate;
	private String lifecycleStatus;
	private Map<String, String> validFor;
	private String type;
	private Map<String, String> productOffering;
	private Map<String, String> category;
	List<HashMap<String,String>> productOfferingPrice;

	List<HashMap<String,String>> prodSpecCharValueUse;*/
	
	@Autowired
	private ProductRepository productRepository;
	
	public TMFProductOfferingModel() {
		super();
	}

	@Handler
	public  void transformOfferToTMFProductOffering(Exchange exchange) {
		Offer inputOffer = (Offer) exchange.getIn().getBody();

/*		TMFProductOfferingModel obj = new TMFProductOfferingModel();
		obj.id = inputOffer.getId();
		obj.name = inputOffer.getName();
		obj.description = inputOffer.getdescription();
		obj.isBundle = false;
		obj.isSellable = inputOffer.getisSellable();
		LocalDate createdDate = inputOffer.getCreated_date();
		DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		obj.lastUpdate = (createdDate).format(formatter_1);
		obj.lifecycleStatus = inputOffer.getlifecycleStatus();
		
		obj.validFor = new HashMap<String, String>();
		obj.validFor.put("startDateTime", inputOffer.getvalidFor_startDateTime());
		obj.validFor.put("endDateTime", inputOffer.getvalidFor_endDateTime());
		
		obj.type = inputOffer.getType();
		
		obj.productOffering = new HashMap<String, String>();
		obj.productOffering.put("id", inputOffer.getProduct_id());
		try {
			obj.productOffering.put("name", OfferService.class.newInstance().findOne(inputOffer.getProduct_id()).getName());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		obj.category = new HashMap<String, String>();
		obj.category.put("name", inputOffer.getcategory());
		
		obj.productOfferingPrice = new ArrayList<HashMap<String,String>>();
		
		ArrayList plans = (ArrayList) inputOffer.getOffer_params().get("Plans");
		for (int i=0; i<plans.size();i++) {
			Gson gsonPlan = new Gson();
			Map<String, Map<String, Object>> plan = (Map<String, Map<String, Object>>) plans.get(i);
			Map<String, Object> basic_pricing_parameters = (Map<String, Object>) plan.get("basic_pricing_parameters");
			HashMap<String, String> offerPrice = new HashMap<String, String>();
			offerPrice.put("id", inputOffer.getId());
			offerPrice.put("name", (String) basic_pricing_parameters.get("name"));
			obj.productOfferingPrice.add(offerPrice);
		}*/
		
		Map<String, Object> productOffering = new HashMap<String, Object>();
		productOffering.put("id", inputOffer.getId());
		productOffering.put("name", inputOffer.getName());
		productOffering.put("description", inputOffer.getdescription());
		productOffering.put("isBundle", false);
		productOffering.put("isSellable", inputOffer.getisSellable());
		ZonedDateTime createdDate = inputOffer.getCreated_date();
		DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		productOffering.put("lastUpdate", (createdDate).format(formatter_1));
		productOffering.put("lifecycleStatus", inputOffer.getlifecycleStatus());

		Map<String, String> validFor = new HashMap<String, String>();
		
		DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		String getvalidFor_startDateTime = inputOffer.getvalidFor_startDateTime();
		LocalDate startDateTime = LocalDate.parse(getvalidFor_startDateTime, formatter_2);
		validFor.put("startDateTime", ZonedDateTime.of(startDateTime, LocalTime.MIDNIGHT, ZoneId.of("Australia/Melbourne")).format(formatter_1));
		
		String getvalidFor_endDateTime = inputOffer.getvalidFor_endDateTime();
		LocalDate endDateTime = LocalDate.parse(getvalidFor_endDateTime, formatter_2);		
		validFor.put("endDateTime", ZonedDateTime.of(endDateTime, LocalTime.MIDNIGHT, ZoneId.of("Australia/Melbourne")).format(formatter_1));
		
		productOffering.put("validFor", validFor);
		
		productOffering.put("@type", inputOffer.getType());
		
		Map<String, String> productSpecification = new HashMap<String, String>();
		productSpecification.put("id", inputOffer.getProduct_id());
		productSpecification.put("name", productRepository.findOne(inputOffer.getProduct_id()).getName());
		productOffering.put("productSpecification", productSpecification);
		
		Map<String, String> category = new HashMap<String, String>();
		category.put("name", inputOffer.getcategory());
		productOffering.put("category", category);
		
		List<HashMap<String, String>> productOfferingPrice = new ArrayList<HashMap<String,String>>();
		
		Set<Entry<String, Object>> OfferParamsEntrySet = inputOffer.getOffer_params().entrySet();
		if(OfferParamsEntrySet!=null && OfferParamsEntrySet.size()>0) {
			for (Entry<String, Object> entry : OfferParamsEntrySet) {
				if (entry.getKey().equals("Plans")) {
					ArrayList plans = (ArrayList) entry.getValue();
					for (int i=0; i<plans.size();i++) {
						Map<String, Map<String, Object>> plan = (Map<String, Map<String, Object>>) plans.get(i);
						//log.debug("Plan Size: " + plan.size());
						for (Entry<String, Map<String, Object>> planEntry : plan.entrySet()) {
							//log.debug("Plan Entry Key: " + planEntry.getKey());
							if(planEntry.getKey().equals("basic_pricing_parameters"))
							{
								Map<String, Object> basic_pricing_parameters = planEntry.getValue();
								HashMap<String, String> offerPrice = new HashMap<String, String>();
								offerPrice.put("id", inputOffer.getId());
								offerPrice.put("name", (String) basic_pricing_parameters.get("name"));
								productOfferingPrice.add(offerPrice);								
							} else {
								Map<String, Object> offer_parameters = planEntry.getValue();
								Set<Entry<String, Object>> paramsEntrySet = offer_parameters.entrySet();
								//log.debug("paramsEntrySet Size : " + paramsEntrySet.size());
								if(paramsEntrySet!=null && paramsEntrySet.size()>0) {
									//log.debug("Inside paramsEntrySet");
									ArrayList<HashMap<String,Object>> prodSpecCharValueUses = new ArrayList<HashMap<String,Object>>();
									
									for (Entry<String, Object> paramEntry : paramsEntrySet) {
										HashMap<String, Object> prodSpecCharValueUse = new HashMap<String, Object>();
										prodSpecCharValueUse.put("name", paramEntry.getKey());
										prodSpecCharValueUse.put("valueType", "string");
										
										ArrayList<HashMap<String,Object>> productCharacteristicValues = new ArrayList<HashMap<String,Object>>();
										HashMap<String, Object> productCharacteristicValue = new HashMap<String, Object>();
										//productCharacteristicValue.put("isDefault", true);
										productCharacteristicValue.put("value", paramEntry.getValue());
										productCharacteristicValues.add(productCharacteristicValue);
										prodSpecCharValueUse.put("productSpecCharacteristicValue", productCharacteristicValues);
										
										prodSpecCharValueUse.put("productSpecification", productSpecification);
										prodSpecCharValueUses.add(prodSpecCharValueUse);
									}
									
									productOffering.put("prodSpecCharValueUse", prodSpecCharValueUses);
								}/* else {
									log.debug("paramsEntrySet is null or 0");
								}*/
							}
						}
					}
				}
			}
		}
		
		productOffering.put("productOfferingPrice", productOfferingPrice);
		
		Gson gson = new Gson();
		String tmfProductOffering = gson.toJson(productOffering);

		//log.debug("TMFProductOffering: " + tmfProductOffering);
		exchange.getIn().setBody(tmfProductOffering);
	}


}
