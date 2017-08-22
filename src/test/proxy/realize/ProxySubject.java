package test.proxy.realize;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author kangrw
 * @time 2017年8月21日上午9:31:43
 */
public class ProxySubject implements InvocationHandler{
	
	Subject obj;
	
	public ProxySubject(Subject obj) {
		this.obj = obj;
	}

	public Object invoke(Method method) {
		try {
			System.out.println("pre say Hello ~... ");
			method.invoke(obj);
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
