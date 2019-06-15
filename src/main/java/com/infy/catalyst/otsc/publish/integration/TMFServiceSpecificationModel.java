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

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.infy.catalyst.otsc.domain.Service;

public class TMFServiceSpecificationModel {

	//private final Logger log = LoggerFactory.getLogger(TMFServiceSpecificationModel.class);

/*	private String id;
	private String name;
	private String description;
	private String lastUpdate;
	private String lifecycleStatus;
	private String type;

	List<HashMap<String,Object>> serviceSpecCharacteristic;
*/

	public TMFServiceSpecificationModel() {
		super();
	}

	@Handler
	public  void transformServiceToTMFServiceSpecification(Exchange exchange) {
		Service inputService = (Service) exchange.getIn().getBody();

/*		TMFServiceSpecificationModel obj = new TMFServiceSpecificationModel();
		obj.id = inputService.getId();
		obj.name = inputService.getName();
		obj.description = inputService.getdescription();
		LocalDate createdDate = inputService.getCreated_date();
		DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		obj.lastUpdate = (createdDate).format(formatter_1);
		obj.lifecycleStatus = inputService.getlifecycleStatus();
		obj.type = inputService.getType();

		obj.serviceSpecCharacteristic = new ArrayList<HashMap<String,Object>>();
		Map serviceCharacteristicsMap = inputService.getService_params();
		Set<Entry<String,String>> entrySet = serviceCharacteristicsMap.entrySet();
		if(entrySet!=null && entrySet.size()>0) {
			for (Entry<String, String> entry : entrySet) {
				HashMap<String, Object> serviceCharacteristic = new HashMap<String, Object>();
				serviceCharacteristic.put("name", entry.getKey());
				serviceCharacteristic.put("valueType", "string");
				
				ArrayList<HashMap<String,Object>> serviceCharacteristicValues = new ArrayList<HashMap<String,Object>>();
				HashMap<String, Object> serviceCharacteristicValue = new HashMap<String, Object>();
				serviceCharacteristicValue.put("isDefault", true);
				serviceCharacteristicValue.put("value", entry.getValue());
				serviceCharacteristicValues.add(serviceCharacteristicValue);
				
				serviceCharacteristic.put("serviceSpecCharacteristicValue", serviceCharacteristicValues);
				obj.serviceSpecCharacteristic.add(serviceCharacteristic);
			}
		}

		Gson gson = new Gson();
		String tmfServiceSpecification = gson.toJson(obj);*/

		Map<String, Object> serviceSpecification = new HashMap<String, Object>();
		serviceSpecification.put("id", inputService.getExternalId());
		serviceSpecification.put("name", inputService.getName());
		serviceSpecification.put("description", inputService.getdescription());
		ZonedDateTime createdDate = inputService.getCreated_date();
		DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		serviceSpecification.put("lastUpdate", (createdDate).format(formatter_1));
		serviceSpecification.put("lifecycleStatus", inputService.getlifecycleStatus());
		serviceSpecification.put("@type", inputService.getType());

		List<HashMap<String,Object>> serviceSpecCharacteristic = new ArrayList<HashMap<String,Object>>();
		Map serviceCharacteristicsMap = inputService.getService_params();
		Set<Entry<String,Object>> entrySet = serviceCharacteristicsMap.entrySet();
		if(entrySet!=null && entrySet.size()>0) {
			for (Entry<String, Object> entry : entrySet) {
				HashMap<String, Object> serviceCharacteristic = new HashMap<String, Object>();
				serviceCharacteristic.put("name", entry.getKey());
				serviceCharacteristic.put("valueType", "string");
				
				ArrayList<HashMap<String,Object>> serviceCharacteristicValues = new ArrayList<HashMap<String,Object>>();
				
				if (entry.getValue() instanceof List<?>) {
					ArrayList characteristicValues = (ArrayList) entry.getValue();
					for (int i=0; i<characteristicValues.size();i++) {
						HashMap<String, Object> serviceCharacteristicValue = new HashMap<String, Object>();
						//serviceCharacteristicValue.put("isDefault", true);
						serviceCharacteristicValue.put("value", characteristicValues.get(i));
						serviceCharacteristicValues.add(serviceCharacteristicValue);
					}

				} else if (!entry.getValue().equals("")){
					HashMap<String, Object> serviceCharacteristicValue = new HashMap<String, Object>();
					//serviceCharacteristicValue.put("isDefault", true);
					serviceCharacteristicValue.put("value", entry.getValue());
					serviceCharacteristicValues.add(serviceCharacteristicValue);
				}
				
				if (serviceCharacteristicValues.size() > 0) {
					serviceCharacteristic.put("serviceSpecCharacteristicValue", serviceCharacteristicValues);
				}
				
				serviceSpecCharacteristic.add(serviceCharacteristic);
			}
		}
		serviceSpecification.put("serviceSpecCharacteristic", serviceSpecCharacteristic);

		Gson gson = new Gson();
		String tmfServiceSpecification = gson.toJson(serviceSpecification);
		//log.debug("TMFServiceSpecification: " + tmfServiceSpecification);
		exchange.getIn().setBody(tmfServiceSpecification);
	}
}