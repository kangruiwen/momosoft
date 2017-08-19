package test.proxy.basicUse;

import java.lang.reflect.Proxy;

/**
 * @company 浙江鸿程计算机系统有限公司
 * @author kangrw
 * @time 2017年8月18日上午10:43:10
 */
public class TestMain {
	public static void main(String[] args) {
		Subject realSubject = new RealSubject();
		ProxySubject proxySub = new ProxySubject(realSubject);
		Subject sub = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[]{Subject.class}, proxySub);
		sub.sayHello();
	}
}
