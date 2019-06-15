package com.infy.catalyst.otsc.service.integration.util;

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
import com.infy.catalyst.otsc.service.impl.OfferServiceImpl;

public class BESOfferingModel {
	
	//private final Logger log = LoggerFactory.getLogger(BESOfferingModel.class);
	 
	private String beId;
	private String beCode;
	private Map<String, String> ownerParty;
	private Map<String, String> releaseParty;
	private Map<String, String> offering;
	//private Map<String, String> relations;
	List<HashMap<String,String>> relations;
	
	
	public BESOfferingModel() {
		super();
	}
		
	@Handler
	public  void transformOfferToBESOffering(Exchange exchange) {
		Offer inputOffer = (Offer) exchange.getIn().getBody();
		
		BESOfferingModel obj = new BESOfferingModel();
		obj.beId = "101";
		obj.beCode = "101";
		
		obj.offering = new HashMap<String, String>();
		obj.offering.put("id",inputOffer.getId()); //this is a counter value in DB and can be initialized if needed
		obj.offering.put("code","Infosys-"+ inputOffer.getId());
		obj.offering.put("name",inputOffer.getName());
		obj.offering.put("shortName",inputOffer.getName());
		obj.offering.put("busiModeType", "B2C");
		obj.offering.put("status", "D");
		obj.offering.put("description", inputOffer.getName());
		obj.offering.put("isAutoCreate","N");
		obj.offering.put("isBundled", "N");
		obj.offering.put("isPricingPack", "N");
		obj.offering.put("isPrimary", "N");
		obj.offering.put("isSellSingly", "Y");
		obj.offering.put("paymentMode", "1");
		obj.offering.put("isMultiPurchase", "N");
		obj.offering.put("classficationId", "910023"); //Key received from Back-end
		obj.offering.put("manageCategoryId", "1000000083"); //Key received from Back-end
		obj.offering.put("offeringType", "0");
					
		obj.releaseParty = new HashMap<String, String>();
		obj.releaseParty.put("roleType", "aaa");
		obj.releaseParty.put("roleId", "187314");
		obj.releaseParty.put("id","187314");
		
		obj.ownerParty =new HashMap<String, String>();
		obj.ownerParty.put("roleType", "C");
		obj.ownerParty.put("roleId", "187314");
		obj.ownerParty.put("id","187314");
		
		
		obj.relations = new ArrayList<HashMap<String,String>>();
		
		HashMap<String,String> productRelation = new HashMap<String,String>();
		productRelation.put("type", "E"); //Exclusions
		//productRelation.put("destEntryId", inputOffer.getProduct_id());  // this is product ID and will have to be in offer
		productRelation.put("destEntryId", "1000000207");
		productRelation.put("destEntryType","P");//Type is product to which this offer is getting associated
		productRelation.put("operateType","ADD");
		obj.relations.add(productRelation);	
		
		Map<String, BESOfferingModel> offering = new HashMap<String, BESOfferingModel>();
		offering.put("offering", obj);
		
		Gson gson = new Gson();
        String besOffering = gson.toJson(offering);
            
        exchange.getIn().setBody(besOffering);
      
		//return offering;	Map<String, BESOfferingModel> 	
	}


	public Map<String, String> getOffering() {
		return offering;
	}


	public void setOffering(Map<String, String> offering) {
		this.offering = offering;
	}


	public Map<String, String> getReleaseParty() {
		return releaseParty;
	}


	public void setReleaseParty(Map<String, String> releaseParty) {
		this.releaseParty = releaseParty;
	}


	public Map<String, String> getOwnerParty() {
		return ownerParty;
	}


	public void setOwnerParty(Map<String, String> ownerParty) {
		this.ownerParty = ownerParty;
	}


	
	
	
	
	

}
