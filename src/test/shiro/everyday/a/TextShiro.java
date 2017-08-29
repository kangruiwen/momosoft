package test.shiro.everyday.a;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @author momo
 * @time 2017年8月28日下午4:18:23
 * 
 * 身份认证 ： TxtRealm源
 */
public class TextShiro {

	public static void main(String[] args) {
		// 读取配置文件，初始化SecurityManager工厂
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		// 获取securityManager实例
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		// 把securityManager实例绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		// 创建token令牌，用户名/密码
		UsernamePasswordToken token = new UsernamePasswordToken("csdn", "123");
		// 得到当前执行的用户
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 身份认证
			currentUser.login(token);
			System.out.println("身份认证成功！");
		} catch (AuthenticationException e) {
			e.printStackTrace();
			System.out.println("身份认证失败！");
		}
		// 退出
		currentUser.logout();
	}

}
