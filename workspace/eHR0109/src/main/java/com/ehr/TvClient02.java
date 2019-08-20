package com.ehr;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TvClient02 {
	public final static Logger LOG = Logger.getLogger(TvClient02.class);
	
	public static void main(String[] args) {
		AbstractApplicationContext context = new GenericXmlApplicationContext("applicationContext02.xml");
		LOG.info("----------------------------------");
		LOG.info("context : "+context);
		LOG.info("----------------------------------");
		
		SamsungTV samsungTV = (com.ehr.SamsungTV) context.getBean("samsungTV");
		SamsungTV samsungTV1 = (com.ehr.SamsungTV) context.getBean("samsungTV");
		SamsungTV samsungTV2 = (com.ehr.SamsungTV) context.getBean("samsungTV");
		
		LOG.info("----------------------------------");
		LOG.info("SamsungTV1 : "+samsungTV);
		LOG.info("SamsungTV2 : "+samsungTV1);
		LOG.info("SamsungTV3 : "+samsungTV2);
		LOG.info("----------------------------------");
		
//		samsungTV.powerOn();
//		samsungTV.volumeUp();
//		samsungTV.volumeDown();
//		samsungTV.powerOff();
//		context.close();
	}

}
