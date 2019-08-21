package com.ehr.set;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class CollectionBeanSetClient {

	static final Logger LOG = Logger.getLogger(CollectionBeanSetClient.class);
	
	public static void main(String[] args) {
		AbstractApplicationContext  context = 
				new GenericXmlApplicationContext("applicationContextSet.xml");
		
		LOG.info("01.context:"+context);
		CollectionBeanSet colSet = 
				  context.getBean("collectionBeanSet", CollectionBeanSet.class);
		LOG.info("02.colSet:"+colSet);
		
		Set<String>  set = colSet.getAddressSet();
		
		for(String addr  :set) {
			LOG.info("03.addr:"+addr);
		}
	}
}