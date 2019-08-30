package com.ehr;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
public class AroundAdvice {

	private final Logger LOG = Logger.getLogger(AroundAdvice.class);
	
	public Object arroundLog(ProceedingJoinPoint pjp) throws Throwable{
		Signature signature = pjp.getSignature();
		Object[] objs = pjp.getArgs();
		
		LOG.debug("Before*********************************"+signature.getName());
		for(Object obj: objs) {
			LOG.debug("obj="+obj);
		}
		Object retObj = pjp.proceed();
		LOG.debug("After*********************************"+signature.getName());
		
		return retObj;
	}
	
}
