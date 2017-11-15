package main.socket.tcp.bio.lock;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 功能：
 * 
 * 说明：
 *  查阅相关资料，仔细阅读了书上的每个细节，在通过对比书上的代码和自己的代码，发现了问题所在。问题就出现在read（）方法上，这里的重点是read（）方法何时返回-1，
 *  在一般的文件读取中，这代表流的结束，亦即读取到了文件的末尾，但是在Socket套接字中，这样的概念很模糊，因为套接字中数据的末尾并没有所谓的结束标记，无法通过其自身表示传输的数据已经结束，
 *  那么究竟什么时候read（）会返回-1呢？答案是：当TCP通信连接的一方关闭了套接字时。
 *  再次分析改过后的代码，客户端用到了read（）返回-1这个条件，而服务端也用到了，只有二者有一方关闭了Socket，另一方的read（）方法才会返回-1，而在客户端打印输出前，
 *  二者都没有关闭Socket，因此，二者的read（）方法都不会返回-1，程序便阻塞在此处，都不往下执行，这便造成了死锁。 反过来，再看书上的给出的代码，在客户端代码的while循环中，
 *  我们的条件是totalBytesRcvd < data.length，而不是(bytesRcvd = in.read())!= -1，这样，客户端在收到与其发送相同的字节数之后便会退出while循环，
 *  再往下执行，便是关闭套接字，此时服务端的read（）方法检测到客户端的关闭，便会返回-1，从而继续往下执行，也将套接字关闭。因此，不会产生死锁。 那么，如果在客户端不知道反馈回来的数据的情况下，
 *  该如何避免死锁呢？Java的Socket类提供了shutdownOutput（）和shutdownInput（）另个方法，用来分别只关闭Socket的输出流和输入流，而不影响其对应的输入流和输出流，
 *  那么我们便可以在客户端发送完数据后，调用shutdownOutput（）方法将套接字的输出流关闭，这样，服务端的read（）方法便会返回-1，继续往下执行，最后关闭服务端的套接字，
 *  而后客户端的read（）方法也会返回-1，继续往下执行，直到关闭套接字。
 *  
 * 总结：
 * 	由于read（）方法只有在另一端关闭套接字的输出流时，才会返回-1，而有时候由于我们不知道所要接收数据的大小，因此不得不用read（）方法返回-1这一判断条件，那么此时，
 * 合理的程序设计应该是先关闭网络输出流（亦即套接字的输出流），再关闭套接字。
 * 
 * @user momo 2017年11月9日
 */
public class TCPEchoClient {
	public static void main(String[] args) throws IOException {

		if ((args.length < 2) || (args.length > 3)) // Test for correct of args
			throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

		String server = args[0]; // Server name or IP address
		// Convert argument String to bytes using the default character encoding
		byte[] data = args[1].getBytes();

		int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

		// Create socket that is connected to server on specified port
		Socket socket = new Socket(server, servPort);
		System.out.println("Connected to server...sending echo string");

		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();

		out.write(data); // Send the encoded string to the server
		// 在这儿加入关闭流的方法，下边的错误做法即可消失      AAAAAAA
		socket.shutdownOutput();  
		// Receive the same string back from the server
		int totalBytesRcvd = 0; // Total bytes received so far
		int bytesRcvd; // Bytes received in last read
		
		// 正确的做法1
		/*while (totalBytesRcvd < data.length) {
			if ((bytesRcvd = in.read(data, totalBytesRcvd, data.length - totalBytesRcvd)) == -1)
				throw new SocketException("Connection closed prematurely");
			totalBytesRcvd += bytesRcvd;
		}*/
		
		// 错误的做法
		while((bytesRcvd = in.read())!= -1){  
		    data[totalBytesRcvd] = (byte)bytesRcvd; 
		    System.out.println((char)data[totalBytesRcvd]);  
		    totalBytesRcvd++;  
		}  
		// 输出完之后

		System.out.println("Received: " + new String(data));

		socket.close(); // Close the socket and its streams
	}
}
