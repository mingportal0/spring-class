package com.ehr;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
public class LogAdvice {

	private final Logger LOG=Logger.getLogger(LogAdvice.class);
	//JointPoint : 메소드로 넘어오는 파라메터, 메소드이름등 정보 획득
	//JointPont 전에 실행되는 메소드
	public void logging(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		LOG.debug("**************************");
		LOG.debug(methodName+" is calling Advice");
		LOG.debug("**************************");
	}
	
	
}
