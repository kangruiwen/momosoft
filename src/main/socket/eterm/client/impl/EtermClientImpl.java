package main.socket.eterm.client.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

import main.common.util.PropertiesReader;
import main.socket.eterm.client.EtermClient;
import main.socket.eterm.entity.RespLoginEntity;
import main.socket.eterm.protocol.ReqLoginProtocol;
import main.socket.eterm.protocol.RespLoginProtocal;
import main.socket.eterm.protocol.SessionOpenProtocol;

/**
 * @author momo
 * @time 2018年4月12日下午4:11:15
 */
public class EtermClientImpl implements EtermClient{
	
	private Socket socket = null; 
	private BufferedOutputStream out;
	private BufferedInputStream in;
	private Map<String,String> map;
	
	@SuppressWarnings("unchecked")
	public EtermClientImpl() {
		super();
		map = PropertiesReader.read(("/eterm.properties"));
	}
	
	/**
	 * 建立连接
	 */
	public boolean connect() {
		try{
			socket = new Socket();
			socket.connect(new InetSocketAddress(InetAddress.getByName(map.get("host")),Integer.parseInt(map.get("port"))), 30 * 1000);
			socket.setSoTimeout(30 * 1000);
			out = new BufferedOutputStream(socket.getOutputStream());
			in = new BufferedInputStream(socket.getInputStream());
			
			//发送登录包
			byte[] data = ReqLoginProtocol.getReqLoginData(map.get("username"),map.get("password"));
			out.write(data);
			out.flush();
			//接收登录应答包
			RespLoginEntity rlEntity = RespLoginProtocal.getRespLoginMsg(in);
			if(rlEntity.isPassed()) { // 成功
				// 需要初始化的session个数
				// 另外在这里还有一种情况，需要进行验证，sessionOpen的时候，第一个session的初始化时单独发送的，然而，最后几个session是作为一个报文一次性发送的？
				// TODO 这一点有待验证。
				byte[] reSend = null;
				for(int i = 0 ; i < rlEntity.getSessionNums(); i ++ ) {
					reSend = SessionOpenProtocol.getProtocolData(rlEntity.getSessionList().get(i)[0]);
					out.write(reSend); // 初始化
					out.flush();
					// 对每一个session初始化的结果进行判断 TODO  在这里有个疑问，如果有一个session初始化失败，eterm能够正常连接吗，必须初始化全部吗？
					// 现在是每一个都需要初始化正确，有一个错误就抛异常
					if(!SessionOpenProtocol.getResponceStatus(in)){
						throw new Exception();
					}
				}
			}else{
				throw new Exception();
			}
		}catch(Exception e) {
			System.err.println("连接失败，请重新连接...");
			return false;
		}finally{
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				System.err.println("关闭连接失败....");
			}
		}
		return true;
		// 至此，客户端与ETERM连接成功，接下来发送登录指令
		
	}
	
}
