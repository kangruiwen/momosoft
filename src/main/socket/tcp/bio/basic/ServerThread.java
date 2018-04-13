package main.socket.tcp.bio.basic;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import main.socket.eterm.protocol.ReqLoginProtocol;

/**
 * @author momo
 * @time 2017年11月6日下午10:30:19
 * 
 *       该类为多线程类，用于服务端
 */
public class ServerThread implements Runnable {

	private Socket client = null;

	public ServerThread(Socket client) {
		this.client = client;
	}

	public void run() {
		try {
			OutputStream os = client.getOutputStream();
			InputStream is = client.getInputStream();
			
			byte[] buf = new byte[1024];
			while(is.read(buf) != -1) {
				ReqLoginProtocol.showProtocol(buf);
			}
			
			
			/*// 获取Socket的输出流，用来向客户端发送数据
			PrintStream out = new PrintStream(client.getOutputStream());
			// 获取Socket的输入流，用来接收从客户端发送过来的数据
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			boolean flag = true;
			while (flag) {
				// 接收从客户端发送过来的数据
				String str = buf.readLine();
				if (str == null || "".equals(str)) {
					flag = false;
				} else {
					if ("bye".equals(str)) {
						flag = false;
					} else {
						// 将接收到的字符串前面加上echo，发送到对应的客户端
						out.println("echo:" + str);
					}
				}
			}*/
			os.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
