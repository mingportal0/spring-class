package com.ehr;

public class AppleSpeaker implements Speaker {
	private String brandNm = "AppleSpeaker";
	
	public AppleSpeaker(){
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
		LOG.info("powerOn : "+brandNm+"의 볼륨을 낮춘다.");
		LOG.info("------------------------------------");
	}

}
