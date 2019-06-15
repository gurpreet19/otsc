package com.infy.catalyst.otsc.publish.integration;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.spi.ServiceRegistry;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.infy.catalyst.otsc.domain.Product;
import com.infy.catalyst.otsc.domain.Service;
import com.infy.catalyst.otsc.repository.ServiceRepository;
import com.infy.catalyst.otsc.service.impl.OfferServiceImpl;

public class TMFProductSpecificationModel {

	//private final Logger log = LoggerFactory.getLogger(TMFProductSpecificationModel.class);

	/*	private String id;
	private String name;
	private String description;
	private String lastUpdate;
	private String lifecycleStatus;
	private String type;

	List<HashMap<String,Object>> productSpecCharacteristic;
	List<HashMap<String,String>> serviceSpecification;*/

	@Autowired
	private ServiceRepository serviceRepository;

	public TMFProductSpecificationModel() {
		super();
	}

	@Handler
	public  void transformProductToTMFProductSpecification(Exchange exchange) {
		Product inputProduct = (Product) exchange.getIn().getBody();

		/*		TMFProductSpecificationModel obj = new TMFProductSpecificationModel();
		obj.id = inputProduct.getId();
		obj.name = inputProduct.getName();
		obj.description = inputProduct.getdescription();
		LocalDate createdDate = inputProduct.getCreated_date();
		DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		obj.lastUpdate = (createdDate).format(formatter_1);
		obj.lifecycleStatus = inputProduct.getlifecycleStatus();
		obj.type = inputProduct.getType();

		obj.productSpecCharacteristic = new ArrayList<HashMap<String,Object>>();
		Map productCharacteristicsMap = inputProduct.getProduct_params();
		Set<Entry<String,String>> entrySet = productCharacteristicsMap.entrySet();
		if(entrySet!=null && entrySet.size()>0) {
			for (Entry<String, String> entry : entrySet) {
				HashMap<String, Object> productCharacteristic = new HashMap<String, Object>();
				productCharacteristic.put("name", entry.getKey());
				productCharacteristic.put("valueType", "string");

				ArrayList<HashMap<String,Object>> productCharacteristicValues = new ArrayList<HashMap<String,Object>>();
				HashMap<String, Object> productCharacteristicValue = new HashMap<String, Object>();
				productCharacteristicValue.put("isDefault", true);
				productCharacteristicValue.put("value", entry.getValue());
				productCharacteristicValues.add(productCharacteristicValue);

				productCharacteristic.put("productSpecCharacteristicValue", productCharacteristicValues);
				obj.productSpecCharacteristic.add(productCharacteristic);
			}
		}

		obj.serviceSpecification = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> service = new HashMap<String, String>();
		service.put("id", (String) inputProduct.getService_id());
		obj.serviceSpecification.add(service);*/

		Map<String, Object> productSpecification = new HashMap<String, Object>();
		productSpecification.put("id", inputProduct.getId());
		productSpecification.put("name", inputProduct.getName());
		productSpecification.put("description", inputProduct.getdescription());
		ZonedDateTime createdDate = inputProduct.getCreated_date();
		DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		productSpecification.put("lastUpdate", (createdDate).format(formatter_1));
		productSpecification.put("lifecycleStatus", inputProduct.getlifecycleStatus());
		productSpecification.put("@type", inputProduct.getType());

		List<HashMap<String,Object>>  productSpecCharacteristic = new ArrayList<HashMap<String,Object>>();
		Map productCharacteristicsMap = inputProduct.getProduct_params();
		Set<Entry<String,Object>> entrySet = productCharacteristicsMap.entrySet();
		if(entrySet!=null && entrySet.size()>0) {
			for (Entry<String, Object> entry : entrySet) {
				HashMap<String, Object> productCharacteristic = new HashMap<String, Object>();
				productCharacteristic.put("name", entry.getKey());
				productCharacteristic.put("valueType", "string");

				ArrayList<HashMap<String,Object>> productCharacteristicValues = new ArrayList<HashMap<String,Object>>();

				if (entry.getValue() instanceof List<?>) {
					ArrayList characteristicValues = (ArrayList) entry.getValue();
					for (int i=0; i<characteristicValues.size();i++) {
						HashMap<String, Object> productCharacteristicValue = new HashMap<String, Object>();
						//productCharacteristicValue.put("isDefault", true);
						productCharacteristicValue.put("value", characteristicValues.get(i));
						productCharacteristicValues.add(productCharacteristicValue);
					}

				} else if (!entry.getValue().equals("")){
					HashMap<String, Object> productCharacteristicValue = new HashMap<String, Object>();
					//productCharacteristicValue.put("isDefault", true);
					productCharacteristicValue.put("value", entry.getValue());
					productCharacteristicValues.add(productCharacteristicValue);
				}

				if (productCharacteristicValues.size() > 0) {
					productCharacteristic.put("productSpecCharacteristicValue", productCharacteristicValues);
				}

				productSpecCharacteristic.add(productCharacteristic);
			}
		}
		productSpecification.put("productSpecCharacteristic", productSpecCharacteristic);

		if (inputProduct.getService_id() != null && !inputProduct.getService_id().isEmpty()) {
			List<HashMap<String,String>> serviceSpecification = new ArrayList<HashMap<String,String>>();
			HashMap<String, String> service = new HashMap<String, String>();
			Service inputService = serviceRepository.findOne((String) inputProduct.getService_id());
			service.put("id", inputService.getExternalId());
			service.put("name", inputService.getName());
			serviceSpecification.add(service);
			productSpecification.put("serviceSpecification", serviceSpecification);
		}

		Gson gson = new Gson();
		String tmfProductSpecification = gson.toJson(productSpecification);

		//log.debug("TMFProductSpecification: " + tmfProductSpecification);
		exchange.getIn().setBody(tmfProductSpecification);
	}
}