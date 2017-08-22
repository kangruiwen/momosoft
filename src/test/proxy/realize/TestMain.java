package test.proxy.realize;
/**
 * @author momo
 * @time 2017年8月21日下午3:19:24
 * 
 */
public class TestMain {

	public static void main(String[] args) throws Exception {
		Subject s = new TrueSubject();
		InvocationHandler h = new ProxySubject(s);
		Subject ss = (Subject)Proxy.newProxyInstance(new Class[]{Subject.class} , h);
		ss.sayHello();
	}
	
	
}
