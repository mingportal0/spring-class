package com.ehr;

public class SonySpeaker implements Speaker {
	private String brandNm = "SonySpeaker";
	
	public SonySpeaker(){
		LOG.info("------------------------------------");
		LOG.info(brandNm+" 생성자");
		LOG.info("------------------------------------");
	}
	
	@Override
	public void volumeUp() {
		LOG.info("------------------------------------");
		LOG.info("powerOn : "+brandNm+"의 볼륨을 높인다.");
		LOG.info("------------------------------------");
	}

	@Override
	public void volumeDown() {
		LOG.info("------------------------------------");
		LOG.info("powerOn : "+brandNm+"의 볼륨을 높인다.");
		LOG.info("------------------------------------");
	}

}
