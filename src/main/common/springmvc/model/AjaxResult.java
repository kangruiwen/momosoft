package main.common.springmvc.model;

/**
 * @author momo
 * @time 2017年8月22日上午10:12:40
 * 
 * 页面异步请求后返回的数据统一格式
 */

import java.util.HashMap;

public class AjaxResult extends HashMap<String, Object>{
	
	private static final long serialVersionUID = 1L;
	
	public static String codeKey = "code";
	public static String msgKey = "msg";
	public static String dataKey = "data";
	public static String codeDefault = "ok";
	public static String code_error = "error";
	public static String msgDefault = "操作成功";
	
	/**
	 * 不可修改，正确结果
	 */
	public static final AjaxResult OK_RESULT = new AjaxResult();
	
	/**
	 * 不可修改，错误结果
	 */
	public static final AjaxResult ERROR_RESULT = new AjaxResult(code_error, code_error);
	
	public AjaxResult(){
		this.put(codeKey, codeDefault);
		this.put(msgKey, msgDefault);
	}
	
	public AjaxResult(String code, String msg){
		this.put(codeKey, code);
		this.put(msgKey, msg);
	}
	
	public AjaxResult(Object data){
		this();
		this.put(dataKey, data);
	}
	
	public AjaxResult(String code, String msg, Object data){
		this(code, msg);
		this.put(dataKey, data);
	}
	

	public void setMsg(String msg){
		this.put(msgKey, msg);
	}
	public String getMsg(){
		return (String) this.get(msgKey);
	}
	
	public void setCode(String code){
		this.put(codeKey, code);
	}
	public String getCode(){
		return (String) this.get(codeKey);
	}
	
	public void setData(Object data){
		this.put(dataKey, data);
	}
	public Object getData(){
		return this.get(dataKey);
	}
}
