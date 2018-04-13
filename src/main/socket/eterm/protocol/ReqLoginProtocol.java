package main.socket.eterm.protocol;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * 登录协议--数据包
 * @author momo
 * @time 2018年4月8日上午11:53:17
 */
public class ReqLoginProtocol {
	
	/**
	 * 一共是162个字节，前两个字节分别为与包长度
	 * "3832010.000000" 这个是版本号了？
	 */
	private static final byte[] SEND_LOGIN_DATA = new byte[] {
			0x01,// 协议识别字
			(byte) 0xa2, // 包长度，这里是162，可以根据eterm版本抓包后再做分析
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 16 用户登录名，不够的话补0
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 16 密码 ，不够了补0
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 16 预留，全部补0
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 12  网卡MAC地址
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 16 本机IP地址，不够补空格
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 16 版本号，不够补0
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 后边全部为预留
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	
	
	public static byte[] getReqLoginData(String userName,String passWord) throws Exception{
		byte[] data = setUserNameAndPwd(userName,passWord);
		data = getMACAddress(data);
		data = addHostAddress(data);
		return data;
	}
	
	private static byte[] setUserNameAndPwd(String userName,String passWord) {
		byte[] data = new byte[SEND_LOGIN_DATA.length];
		System.arraycopy(SEND_LOGIN_DATA, 0, data, 0,
				data.length);
		byte[] unb = userName.getBytes();
		byte[] pwb = passWord.getBytes();
		int unsize = unb.length;
		if (unsize > 16)
			unsize = 16;
		int pwsize = pwb.length;
		if (pwsize > 16)
			pwsize = 16;
		System.arraycopy(unb, 0, data, 2, unsize);
		System.arraycopy(pwb, 0, data, 18, pwsize);
		return data;
	}

	/**
	 * 添加MAC地址
	 */
	private static byte[] getMACAddress(byte[] data) throws Exception {
		InetAddress ia = InetAddress.getLocalHost();
		// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		int macLength = mac.length;
		if (macLength > 12)
			macLength = 12;
		System.arraycopy(mac, 0, data, 50, macLength);
		return data;
	}
	
	/**
	 * 添加协议IP地址
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static byte[] addHostAddress(byte[] data) throws Exception{
		InetAddress ia = InetAddress.getLocalHost();
		byte[] ip = ia.getHostAddress().getBytes();
		int ipLength = ip.length;
		if (ipLength > 16)
			ipLength = 16;
		System.arraycopy(ip, 0, data, 62, ipLength);
		return data;
	}
	
	/**
	 * 将2进制转换为16进制表示,用于测试
	 * @param resultBytes
	 * @return
	 */
	private static String fromBytesToHex(byte[] resultBytes) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < resultBytes.length; i++) {
			if (Integer.toHexString(0xFF & resultBytes[i]).length() == 1) {
				builder.append("0").append(
						Integer.toHexString(0xFF & resultBytes[i]));
			} else {
				builder.append(Integer.toHexString(0xFF & resultBytes[i]));
			}
		}
		return builder.toString();
	}
	
	public static void showProtocol(byte[] bytes) {
		String preFix = "0x";
		String str = fromBytesToHex(bytes);
		StringBuffer sb = new StringBuffer();
		char[] chars = str.toCharArray();
		sb.append(preFix + chars[0] + chars[1] + System.getProperty("line.separator"));
		sb.append(preFix + chars[2] + chars[3] + System.getProperty("line.separator"));
		for(int i = 4 ; i < chars.length - 1; i ++ ) {
			if(i == 36 || i == 68 || i == 100 || i == 124 || i == 156 || i == 188) {
				sb.append(System.getProperty("line.separator"));
			}
			sb.append(preFix + chars[i] + chars[++i] + " ");
		}
		System.out.println(sb.toString());
	}
	
	
	
	public static void main(String[] args) throws Exception{
		byte[] ret = getReqLoginData("kangruiwen","123456");
		showProtocol(ret);
	}

}
