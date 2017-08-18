package test.proxy.basicUse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author momo
 * @time 2017年8月18日上午10:39:57
 * 代理对象
 */
public class ProxySubject implements InvocationHandler {
	
	private Object realObj;

	public ProxySubject(Object obj) {
		this.realObj = obj;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		preInvoke();
		method.invoke(realObj, args);
		return null;
	}
	
	private void preInvoke() {
		System.out.println("在代理方法运行前...");
	}

}
