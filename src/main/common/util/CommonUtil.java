package main.common.util;

import java.util.UUID;

/**
 * @author momo
 * @time 2017年8月17日上午9:58:56
 */
public class CommonUtil {
	
	/**
     * 获取唯一键
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
