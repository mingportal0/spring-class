package com.ehr;

import org.apache.log4j.Logger;

public interface Speaker {
	public static final Logger LOG = Logger.getLogger(Speaker.class);
	void volumeUp();   
	void volumeDown(); 
}
