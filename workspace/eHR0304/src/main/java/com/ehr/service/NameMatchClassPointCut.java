package com.ehr.service;

import org.springframework.aop.support.NameMatchMethodPointcut;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.springframework.aop.ClassFilter;
import org.springframework.util.PatternMatchUtils;

/**
 * 포인트컷(Pointcut)
 * 필터링된 조인 포인트: 원하는 특정 클래스에서 특정 메소드에만 적용.
 * @author sist
 */  
public class NameMatchClassPointCut extends NameMatchMethodPointcut{
	static final Logger LOG = Logger.getLogger(NameMatchClassPointCut.class);
	
	public void setMappedClassName(String mappedName) {
		this.setClassFilter(new SimpleClassFilter(mappedName));
	}
	
	
	static class SimpleClassFilter implements ClassFilter{
		String mappedName;
		public SimpleClassFilter(String mappedName) {
			this.mappedName = mappedName;
		}
		//클래스 찾기
		@Override
		public boolean matches(Class<?> clazz) {
			LOG.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			LOG.debug("1. PointCut mappedName="+mappedName);
			LOG.debug("2. PointCut clazz="+clazz);
			LOG.debug("3. PointCut PatternMatchUtils="+PatternMatchUtils.simpleMatch(mappedName, clazz.getSimpleName()));
			LOG.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return PatternMatchUtils.simpleMatch(mappedName, clazz.getSimpleName());
		}
	}
	//메소드 찾기
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		LOG.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LOG.debug("1. matches method="+method);
		LOG.debug("2. matches targetClass="+targetClass);
		LOG.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return super.matches(method, targetClass);
	}
	
}
