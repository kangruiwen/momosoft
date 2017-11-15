package main.socket.study.m2017_11_12;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 功能：为了获得本地主机的地址，示例程序利用了NetworkInterface类的功能.
 * 	IP地址实际上是分配给了主机与网络之间的连接，而不是主机本身。NetworkInterface类提供了访问主机所有接口的信息的功能。
 * 
 * 一些IPV6地址带有%d形式的后缀，其中d是一个数字。这样的地址在一个有限的范围内（通常他们是本地连接）其后缀表明了改地址所关联的特定范围
 * @user momo
 * 2017年11月12日
 */
public class InetAddressExample {
	
	public static void networkInterfaceCatch(String[] args) {
		Enumeration<NetworkInterface> interfaceList;
		
		// get the network interfaces and associated addresses for this host
		try {
			interfaceList = NetworkInterface.getNetworkInterfaces();
			if(interfaceList == null ) {
				System.out.println("-- No interfaces found -- ");
			}
			while(interfaceList.hasMoreElements()) {
				NetworkInterface iface = interfaceList.nextElement();
				System.out.println("Interface " + iface.getName() + ":");
				Enumeration<InetAddress> addrList = iface.getInetAddresses();
				if(!addrList.hasMoreElements()) {
					System.out.println("\t(No Addresses for this interface)");
				}
				while(addrList.hasMoreElements()) {
					InetAddress addr = addrList.nextElement();
					System.out.print("\tAddress " +
							((addr instanceof Inet4Address?"(v4)" 
								:(addr instanceof Inet6Address ? "(v6)":"?"))));
					System.out.println(": " + addr.getHostAddress());
				}
			}
		} catch (SocketException e) {
			System.out.println("exception 1");
		}
		// GET name(s)/address(es) of hosts given on commond line
		for(String host : args) {
			System.out.println(host + " :" );
			try {
				InetAddress[] addrList = InetAddress.getAllByName(host);
				for(InetAddress addr : addrList) {
					System.out.println("\t" + addr.getHostName() + "/" + addr.getHostAddress());
				}
			} catch (UnknownHostException e) {
				System.out.println("exception 2");
			}
		}
	}
	
	public static void main(String[] args) {
		networkInterfaceCatch(new String[]{"www.baidu.com"});
		
		try {
			// getByName 与 getAllByName的区别是，getAllbyName返回所有与域名相关的IP地址，而GetByName取第一个
			InetAddress addr = InetAddress.getByName("www.baidu.com");
			System.out.println("\t" + addr.getHostName() + "/" + addr.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
