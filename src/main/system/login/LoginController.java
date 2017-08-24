package main.system.login;

import main.system.springmvc.model.AjaxResult;
import main.system.user.model.UserModel;

import org.springframework.stereotype.Controller;


/**
 * @author momo
 * @time 2017年8月24日上午9:26:09
 */

@Controller
public class LoginController {
	
	public AjaxResult loginAjax(UserModel userModel) {
		
		AjaxResult ar = new AjaxResult();
		
		
		//1.shiro验证
		
		//2.加载各种缓存
		
		//3.更新用户状态
		
		return ar;
	}
}
