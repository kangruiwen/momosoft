package test.proxy.basicUse;
/**
 * @author momo
 * @time 2017年8月18日上午10:38:02
 * 动态代理的真实对象
 */
public class RealSubject implements Subject{
	
	public RealSubject() {
		System.out.println("RealSubject is created");
	}
	
	public void sayHello() {
		System.out.println("我是真实对象");
	}
	
}
