package main.socket.eterm.protocol;

import java.io.BufferedInputStream;

/**
 * 初始化会话
 * @author kangrw
 * @time 2018年4月13日上午10:22:16
 */
public class SessionOpenProtocol {
	
	// 报文前缀
	private static final byte[] SESSION_OPEN_PREFIX = new byte[] {
		0x01,(byte) 0xfe,// 版本号+固定字
		0x00, 0x11, // 包长度
		0x14, 0x10, 0x00, 0x02 // 固定值
	};
	
	// 报文后缀
	private static final byte[] SESSION_OPEN_SUFFIX = new byte[] {
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
	};
	
	/**
	 * 发出请求时的报文数据
	 * @param b
	 * @return
	 */
	public static byte[] getProtocolData(byte b) {
		byte[] bytes = new byte[SESSION_OPEN_PREFIX.length + SESSION_OPEN_SUFFIX.length + 1];
		int i = 0;
		for(; i < SESSION_OPEN_PREFIX.length ; i ++) {
			bytes[i] = SESSION_OPEN_PREFIX[i];
		}
		bytes[i++] = b;
		for(int j = 0; j < SESSION_OPEN_SUFFIX.length; j ++ ){
			bytes[i++] = SESSION_OPEN_PREFIX[j];
		}
		return bytes;
	}
	
	// 正确的返回格式：版本号(1byte) 0xfd 包长度(2byte 0x06 ) 0x00 H1
	// 错误的返回格式：版本号(1byte) 0xfc 包长度(2byte 0x05) 错误原因
	public static boolean getResponceStatus(BufferedInputStream in) throws Exception{
		in.read(); // 版本号
		byte p1 = (byte)in.read(); // 固定字
		int t = 0;
		while(t != -1){
			t = in.read();
		}
		if(p1 == 0xfd){
			return true;
		}
		return false;
	}
}
