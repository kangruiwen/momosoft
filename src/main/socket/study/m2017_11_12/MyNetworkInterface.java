package main.socket.study.m2017_11_12;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * 功能：对NetworkInterface 接口的一个说明
 * 
 * :NetworkInterface 实例代表了主机的一个网络接口
 * 	方法getNetworkInterfaces返回一个列表，其中包含了该主机每一个网络接口所对应的NetworkInterface实例
 * 
 * 	对于每一个网络接口来说其可能包含很多个InetAddress实例，表示与网络接口相关联的地址，这个地址列表可能包含IPV4也可能包含IPV6地址，也可能是他两个的混合
 * @user momo
 * 2017年11月12日
 */
public class MyNetworkInterface {
	
	/**
	 * 这个方法可以通过网络接口名来创建NetworkInterface对象。这个网络接口名并不是计算机名，
	 * 而是用于标识物理或逻辑网络接口的名字，一般是由操作系统设置的。网络接口名在大多数操作系统上
	 * （包括Windows、Linux和Unix）是以eth开头，后面是网络接口的索引号，从0开始。
	 * 
	 * NetworkInterface对象的toString方法可以返回网络接口的名称、显示名和这个网络接口上绑字的所有IP地址等信息。
	 * 当网络接口名不存在时，getByName返回null。方法定义如下：
	 * 
	 * public static NetworkInterface getByName(String name) throws SocketException
	 * @param name
	 */
	public static void getByName(String name) {
		NetworkInterface ni;
		try {
			ni = NetworkInterface.getByName(name);
			System.out.println((ni == null) ? "网络接口不存在!" : ni);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  除了可以使用网络接口名来得到网络接口的信息，还可以利用getByInetAddress方法来确定一个IP地址属于哪一个网络接口。
	 *  由于getByInetAddress方法必须使用一个InetAddress对象封装的IP地址来作为参数，因此，在使用getByInetAddress方法之前，
	 *  必须先创建一个InetAddress对象。但要注意不能使用远程的IP的域名来创建InetAddress对象，否则getByInetAddress将返回null。
	 *  getByInetAddress方法的定义如下：
	 *  
	 *  public static NetworkInterface getByInetAddress(InetAddress addr) throws SocketException 
	 * @param addr
	 */
	public static void getByInetAddress(String addr) {
		try {
			InetAddress local = InetAddress.getByName(addr);
			NetworkInterface ni = NetworkInterface.getByInetAddress(local);
	        System.out.println((ni == null) ? "本机不存在此IP地址!" : ni);  
		} catch (Exception e) {
			e.printStackTrace();
		}
       
	}
	
	/**
	 * NetworkInterface类提供了三个方法可以分别得到网络接口名(getName方法)、网络接口别名(getDisplayName方法)以及和网络接口绑定的所有IP地址(getInetAddresses方法)。 
	 * 
	 * getName方法:
	 * 		这个方法用来得到一个网络接口的名称。这个名称就是使用getByName方法创建NetworkInterface对象时使用的网络接口名，如eth0、ppp0等。getName方法的定义如下：
	 * 		public String getName()
	 * getDisplayName方法:
	 * 		这个方法可以得到更容易理解的网络接口名，也可以将这个网络接口名称为网络接口别名。在一些操作系统中（如Unix），getDisplayName方法和getName方法的返回值相同，
	 * 		但在Windows中getDisplayName方法一般会返回一个更为友好的名字，如Realtek RTL8139 Family PCI Fast Ethernet NIC。getDisplayName方法的定义如下：
	 * 		public String getDisplayName()
	 * getInetAddresses方法:
	 * 		NetworkInterface类可以通过getInetAddresse方法以InetAddress对象的形式返回和网络接口绑定的所有IP地址。getInetAddresses方法的定义如下：
	 * 		public Enumeration<InetAddress> getInetAddresses()
	 * 
	 * @param str
	 */
	public static void getter(String str) {
		//....
	}
	
	public static void main(String[] args) {
		// getByName("eth5"); 
		getByInetAddress("127.0.0.1");
		
	}
}
