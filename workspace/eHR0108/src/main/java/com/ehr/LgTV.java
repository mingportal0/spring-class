package com.ehr;

import org.apache.log4j.Logger;

public class LgTV implements Tv {
	
	private String brandNm = "Lg";
	@Override
	public void powerOn() {
		LOG.debug("------------------------------------");
		LOG.debug("powerOn : "+brandNm+" TV의 전원을 켠  다.");
		LOG.debug("------------------------------------");
	}

	@Override
	public void powerOff() {
		LOG.debug("------------------------------------");
		LOG.debug("powerOn : "+brandNm+" TV의 전원을 끈  다.");
		LOG.debug("------------------------------------");
	}

	@Override
	public void volumeUp() {
		LOG.debug("------------------------------------");
		LOG.debug("powerOn : "+brandNm+" TV의 볼륨을 높인다.");
		LOG.debug("------------------------------------");
	}

	@Override
	public void volumeDown() {
		LOG.debug("------------------------------------");
		LOG.debug("powerOn : "+brandNm+" TV의 볼륨을 높인다.");
		LOG.debug("------------------------------------");
	}

}
