package main.socket.eterm.protocol;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

import main.socket.eterm.entity.RespLoginEntity;

/**
 * 登录应答包
 * @author momo
 * @time 2018年4月12日下午4:57:10
 */
public class RespLoginProtocal {
	
	/**
	 * 获取登录应答包
	 * @return
	 */
	public static RespLoginEntity getRespLoginMsg(BufferedInputStream in) throws Exception{
		RespLoginEntity entity = new RespLoginEntity();
		
		//处理head
		byte p0 = (byte)in.read(); // p0,p1组成报文长度，一般p0为0
		byte p1 = (byte)in.read(); // 报文长度
		entity.setLength(p1);
		if(p0 != 0) {
			int t = 0xffff & (p0<<8) & p1;
			entity.setLength(t);
		}
		byte p2 = (byte)in.read(); // 状态标识 1为成功，0为失败
		entity.setState(p2);
		byte p3 = (byte)in.read(); // p3,p4组成session个数，一般p3为0
		byte p4 = (byte)in.read(); // session个数
		if(p3 != 0) {
			int t = 0xffff & (p3<<8) & p4;
			entity.setSessionNums(t);
		}
		//处理报文body
		int bodyLength = entity.getLength() - 5;
		if(bodyLength <= 0 ) {
			throw new Exception();
		}
		byte[] bytes = new byte[entity.getLength() - 5];
		in.read(bytes);
		List<byte[]> sessionList = new ArrayList<byte[]>();
		byte[] session = null;
		int index = 0;
		for(int i = 0 ; i < bytes.length; i ++ ) {
			if(i % 5 == 0){
				session = new byte[5];
				index = 0;
			}
			session[index++] = bytes[i];
			if(index >= 5) {
				sessionList.add(session);
			}
		}
		entity.setSessionList(sessionList);
		return entity;
	}
	
	
}
