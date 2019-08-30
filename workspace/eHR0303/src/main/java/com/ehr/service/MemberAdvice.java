package com.ehr.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
/**
 * 어드바이스(Advice)
 * 횡단 관(공통기능)심에 해당하는 기능. 독립된 클래스로 생성.
 * @author sist
 *
 */
public class MemberAdvice implements MethodInterceptor {
	private final Logger LOG = Logger.getLogger(this.getClass());
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String method = invocation.getMethod().getName();//메소드 이름
		LOG.debug("******************************");
		LOG.debug("뽑은 메소드 이름은?="+method);
		LOG.debug("******************************");
		
		Object result = invocation.proceed();
		LOG.debug("******************************");
		LOG.debug("메소드 종료="+result);
		LOG.debug("******************************");
		return result;
	}

}
