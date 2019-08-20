package com.ehr;

import org.apache.log4j.Logger;

public class SamsungTV implements Tv {
	
	private String brandNm = "Samsung";
	
	public void destroyMethod() {
		LOG.info("------------------------------------");
		LOG.info("destroy : 객체 반환");
		LOG.info("------------------------------------");
	}
	
	public void initMethod() {
		LOG.info("------------------------------------");
		LOG.info("init : 초기화");
		LOG.info("------------------------------------");
	}
	
	@Override
	public void powerOn() {
		LOG.info("------------------------------------");
		LOG.info("powerOn : "+brandNm+" TV의 전원을 켠  다.");
		LOG.info("------------------------------------");
	}

	@Override
	public void powerOff() {
		LOG.info("------------------------------------");
		LOG.info("powerOn : "+brandNm+" TV의 전원을 끈  다.");
		LOG.info("------------------------------------");
	}

	@Override
	public void volumeUp() {
		LOG.info("------------------------------------");
		LOG.info("powerOn : "+brandNm+" TV의 볼륨을 높인다.");
		LOG.info("------------------------------------");
	}

	@Override
	public void volumeDown() {
		LOG.info("------------------------------------");
		LOG.info("powerOn : "+brandNm+" TV의 볼륨을 낮춘다.");
		LOG.info("------------------------------------");
	}

}
