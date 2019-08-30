package com.ehr.cmn;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class PerformanceAdvice {
	private final Logger LOG=Logger.getLogger(PerformanceAdvice.class);
	
	public Object aroundLog(ProceedingJoinPoint pjp)throws Throwable{
		String method = pjp.getSignature().getName();
		
		//시작시간
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object retObj = pjp.proceed();
		stopWatch.stop();
		//종료시간	
		LOG.debug(method+"() 메소드 수행에 걸린 시간: "
		           +stopWatch.getTotalTimeMillis()+"(ms)초");
		
		return retObj;
	}
}
