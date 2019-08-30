package com.ehr.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;;

public class TransactionHandler implements MethodInterceptor {
	private final Logger LOG = Logger.getLogger(this.getClass());
	private PlatformTransactionManager transactionManager;
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			//target method call;
			Object retObj = invocation.proceed();
			LOG.debug("-----------------------------------------------------------------");
			LOG.debug("invoke retObj="+retObj);
			LOG.debug("-----------------------------------------------------------------");
			//성공 시 커밋
			this.transactionManager.commit(status);
			return retObj;
		}catch(RuntimeException e) {
			LOG.debug("-----------------------------------------------------------------");
			LOG.debug("rollback InvocationTargetException="+e);
			LOG.debug("-----------------------------------------------------------------");
			this.transactionManager.rollback(status);
			throw e;
		}
	}
}
