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

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.infy.catalyst.otsc.domain.BundleOffer;
import com.infy.catalyst.otsc.repository.OfferRepository;

public class TMFBundleProductOfferingModel {

	//private final Logger log = LoggerFactory.getLogger(TMFBundleProductOfferingModel.class);
	
/*	private String id;
	private String name;
	private String description;
	private Boolean isBundle;
	private String isSellable;
	private LocalDate lastUpdate;
	private String lifecycleStatus;
	private Map<String, String> validFor;
	private String type;

	List<HashMap<String,String>> bundledProductOffering;*/

	@Autowired
	private OfferRepository offerRepository;
	
	public TMFBundleProductOfferingModel() {
		super();
	}

	@Handler
	public  void transformOfferToTMFBundleProductOffering(Exchange exchange) {
		BundleOffer inputBundleOffer = (BundleOffer) exchange.getIn().getBody();

/*		TMFBundleProductOfferingModel obj = new TMFBundleProductOfferingModel();
		obj.id = inputBundleOffer.getId();
		obj.name = inputBundleOffer.getName();
		obj.description = inputBundleOffer.getdescription();
		obj.isBundle = true;
		obj.isSellable = inputBundleOffer.getisSellable();
		obj.lastUpdate = inputBundleOffer.getCreated_date();
		obj.lifecycleStatus = inputBundleOffer.getlifecycleStatus();
		
		obj.validFor = new HashMap<String, String>();
		obj.validFor.put("startDateTime", inputBundleOffer.getvalidFor_startDateTime());
		obj.validFor.put("endDateTime", inputBundleOffer.getvalidFor_endDateTime());
		
		obj.type = inputBundleOffer.getType();
		
		obj.bundledProductOffering = new ArrayList<HashMap<String,String>>();
		
		for (int i=0; i<inputBundleOffer.getOffer_ids().size(); i++) {
			HashMap<String, String> offer = new HashMap<String, String>();
			offer.put("id", (String) inputBundleOffer.getOffer_ids().get(i));
			obj.bundledProductOffering.add(offer);
		}*/

		Map<String, Object> bundleProductOffering = new HashMap<String, Object>();
		bundleProductOffering.put("id", inputBundleOffer.getId());
		bundleProductOffering.put("name", inputBundleOffer.getName());
		bundleProductOffering.put("description", inputBundleOffer.getdescription());
		bundleProductOffering.put("isBundle", true);
		bundleProductOffering.put("isSellable", inputBundleOffer.getisSellable());
		ZonedDateTime createdDate = inputBundleOffer.getCreated_date();
		DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		bundleProductOffering.put("lastUpdate", (createdDate).format(formatter_1));
		bundleProductOffering.put("lifecycleStatus", inputBundleOffer.getlifecycleStatus());

		Map<String, String> validFor = new HashMap<String, String>();
		DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		String getvalidFor_startDateTime = inputBundleOffer.getvalidFor_startDateTime();
		LocalDate startDateTime = LocalDate.parse(getvalidFor_startDateTime, formatter_2);
		validFor.put("startDateTime", ZonedDateTime.of(startDateTime, LocalTime.MIDNIGHT, ZoneId.of("Australia/Melbourne")).format(formatter_1));
		
		String getvalidFor_endDateTime = inputBundleOffer.getvalidFor_endDateTime();
		LocalDate endDateTime = LocalDate.parse(getvalidFor_endDateTime, formatter_2);		
		validFor.put("endDateTime", ZonedDateTime.of(endDateTime, LocalTime.MIDNIGHT, ZoneId.of("Australia/Melbourne")).format(formatter_1));
		
		bundleProductOffering.put("validFor", validFor);
		
		bundleProductOffering.put("@type", inputBundleOffer.getType());
		
		List<HashMap<String,String>> bundledProductOffering = new ArrayList<HashMap<String,String>>();
		
		for (int i=0; i<inputBundleOffer.getOffer_ids().size(); i++) {
			HashMap<String, String> offer = new HashMap<String, String>();
			offer.put("id", (String) inputBundleOffer.getOffer_ids().get(i));
			offer.put("name", offerRepository.findOne((String) inputBundleOffer.getOffer_ids().get(i)).getName());
			bundledProductOffering.add(offer);
		}
		bundleProductOffering.put("bundledProductOffering", bundledProductOffering);
		
		Gson gson = new Gson();
		String tmfbundleProductOffering = gson.toJson(bundleProductOffering);

		//log.debug("TMFbundleProductOffering: " + tmfbundleProductOffering);
		exchange.getIn().setBody(tmfbundleProductOffering);
	}


}
