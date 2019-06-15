package com.infy.catalyst.otsc.service.integration.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;

import com.google.gson.Gson;
import com.infy.catalyst.otsc.domain.Offer;
import com.infy.catalyst.otsc.domain.Product;

public class BESProductModel {
	
	//private final Logger log = LoggerFactory.getLogger(BESproductModel.class);
	 
		private String beId;
		private String beCode;
		private Map<String, String> ownerParty;
		private Map<String, String> providerParty;
		private Map<String, String> product;
		
		public BESProductModel() {
			super();
		}
			
		@Handler
		public  void transformOfferToBESproduct(Exchange exchange) {
			Product inputProduct = (Product) exchange.getIn().getBody();
			
			BESProductModel obj = new BESProductModel();
			obj.beId = "101";
			obj.beCode = "101";
			
			obj.product = new HashMap<String, String>();
			obj.product.put("id",inputProduct.getId()); //this is a counter value in DB and can be initialized if needed
			obj.product.put("code","Infosys-"+ inputProduct.getId());
			obj.product.put("type", "2");
			obj.product.put("name",inputProduct.getName());
			obj.product.put("shortName",inputProduct.getName());
			obj.product.put("businessModeType", "B2C");
			obj.product.put("status", "D");
			obj.product.put("description", inputProduct.getName());			
			obj.product.put("isComposite", "N");			
			obj.product.put("isPrimary", "N");
			obj.product.put("classficationId", "910021"); //Key received from Back-end
			obj.product.put("manageCategoryId", "910020"); //Key received from Back-end
			obj.product.put("productLine", "1");
			obj.product.put("groupFlag", "0");
						
			obj.providerParty = new HashMap<String, String>();
			obj.providerParty.put("roleType", "aaa");
			obj.providerParty.put("roleId", "187314");
			obj.providerParty.put("id","187314");
			
			obj.ownerParty =new HashMap<String, String>();
			obj.ownerParty.put("roleType", "C");
			obj.ownerParty.put("roleId", "187314");
			obj.ownerParty.put("id","187314");
			
			Map<String, BESProductModel> product = new HashMap<String, BESProductModel>();
			product.put("product", obj);
			
			Gson gson = new Gson();
	        String besProduct = gson.toJson(product);
	            
	        exchange.getIn().setBody(besProduct);
	      
			
		}


}
