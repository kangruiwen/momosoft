package main.common.regex;

import java.util.regex.Pattern;

/**
 * @author momo
 * @time 2017年10月19日上午8:44:44
 */
public class RegexUtil {
	
	/**
	 * 简单的判断是否为1开头的十一位数字
	 * @param regStr
	 * @return
	 */
	public static boolean isTelNum(String regStr) {
		String pattern = "^1[\\d]{10}";
		boolean isMatch = Pattern.matches(pattern, regStr);
		return isMatch;
	}
	
	public static void main(String[] args) {
		String num = "15669056938";
		System.out.println(isTelNum(num));
	}

}
