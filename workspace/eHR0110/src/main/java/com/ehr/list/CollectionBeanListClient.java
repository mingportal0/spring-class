package com.ehr.list;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class CollectionBeanListClient {

	static final Logger LOG = Logger.getLogger(CollectionBeanListClient.class);
	
	public static void main(String[] args) {
		AbstractApplicationContext  context = 
				new GenericXmlApplicationContext("applicationContextList.xml");
		
		LOG.info("01.context:"+context);
		CollectionBeanList colList = 
				  context.getBean("collectionBeanList", CollectionBeanList.class);
		LOG.info("02.colList:"+colList);
		
		List<String> list = colList.getAddressList();
		
		for(String addr:list) {
			LOG.info("03.addr:"+addr);
		}
	}
}
