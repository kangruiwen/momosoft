package test.proxy.realize;

import java.lang.reflect.Method;


/**
 * @author kangrw
 * @time 2017年8月21日上午9:14:33
 */
public interface InvocationHandler {
	Object invoke(Method method);
}
