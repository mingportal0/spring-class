package com.ehr.reflaction;

//import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.cglib.proxy.InvocationHandler;

public class UpperHandler implements InvocationHandler {
	Object target;
	
	public UpperHandler() {}
	public UpperHandler(Object target) {
		//어떤 종류의 인터페이스를 구현한 타깃에도 적용 가능
		this.target = target;
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//Method chartAtmethod =String.class.getMethod("charAt", int.class);
		for(Object obj:args) {
			System.out.println("obj: "+obj);
		}

		Object ret = method.invoke(target, args);
		if(ret instanceof String && method.getName().startsWith("say")) {
			return ((String)ret).toUpperCase();
		}else {
			return ret;
		}
	}

}
