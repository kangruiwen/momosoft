package main.socket.study.m2017_11_12.nio;

import java.net.InetSocketAddress;  
import java.net.SocketException;  
import java.nio.ByteBuffer;  
import java.nio.channels.SocketChannel; 
/**
 * 功能：
 * 	创建一个非阻塞式TCP回显客户端。可能阻塞的I/O操作包括建立连接，读和写。通过使用非阻塞式信道，这些操作豆浆
 * 	立即返回。
 * 说明：
 *  考虑一个即时消息服务器，可能有上千个客户端同时连接到服务器，但是在任何时刻只有非常少量的消息需要读取和分发（如果采用线程池或者一线程一客户端方式，则会非常浪费资源），
 * 	这就需要一种方法能阻塞等待，直到有一个信道可以进行I/O操作。NIO的Selector选择器就实现了这样的功能，一个Selector实例可以同时检查一组信道的I/O状态，它就类似一个观察者，
 * 	只要我们把需要探知的SocketChannel告诉Selector,我们接着做别的事情，当有事件（比如，连接打开、数据到达等）发生时，它会通知我们，传回一组SelectionKey,我们读取这些Key,
 * 	就会获得我们刚刚注册过的SocketChannel,然后，我们从这个Channel中读取数据，接着我们可以处理这些数据。
 * 	Selector内部原理实际是在做一个对所注册的Channel的轮询访问，不断的轮询(目前就这一个算法)，一旦轮询到一个Channel有所注册的事情发生，比如数据来了，它就会读取Channel中的数据，
 * 	并对其进行处理。要使用选择器，需要创建一个Selector实例，并将其注册到想要监控的信道上（通过Channel的方法实现）。最后调用选择器的select（）方法，
 * 	该方法会阻塞等待，直到有一个或多个信道准备好了I/O操作或等待超时，或另一个线程调用了该选择器的wakeup（）方法。现在，在一个单独的线程中，通过调用select（）方法，
 * 	就能检查多个信道是否准备好进行I/O操作，由于非阻塞I/O的异步特性，在检查的同时，我们也可以执行其他任务。
 * 
 * 基于NIO的TCP连接的建立步骤：
 * 	 服务端
 * 		1、创建一个Selector实例；
 * 		2、将其注册到各种信道，并指定每个信道上感兴趣的I/O操作；
 * 		3、重复执行：
 * 			1）调用一种select（）方法；
 * 			2）获取选取的键列表；
 * 			3）对于已选键集中的每个键：
 * 				a、获取信道，并从键中获取附件（如果为信道及其相关的key添加了附件的话）；
 * 				b、确定准备就绪的操纵并执行，如果是accept操作，将接收的信道设置为非阻塞模式，并注册到选择器；
 * 				c、如果需要，修改键的兴趣操作集；
 * 				d、从已选键集中移除键
 * 	客户端
 * 		与基于多线程的TCP客户端大致相同，只是这里是通过信道建立的连接，但在等待连接建立及读写时，我们可以异步地执行其他任务。
 * @user momo
 * 2017年11月12日
 */
public class TCPEchoClientNonblocking {  
	//客户端整体与SocketTCP编程相同
    public static void client(String args[]) throws Exception{  
        if ((args.length < 2) || (args.length > 3))   
        throw new IllegalArgumentException("参数不正确");  
        //第一个参数作为要连接的服务端的主机名或IP  
        String server = args[0];   
        //第二个参数为要发送到服务端的字符串  
        byte[] argument = args[1].getBytes();  
        //如果有第三个参数，则作为端口号，如果没有，则端口号设为7  
        int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 33811;  
        //创建一个信道，并设为非阻塞模式  
        SocketChannel clntChan = SocketChannel.open();  
        clntChan.configureBlocking(false);  
        //向服务端发起连接  
        //由于该套接字是非阻塞式的，因此对connect方法的调用可能会在连接建立之前返回，如果在返回之前已经成功的建立了连接，那么返回true，否则为false
        //对于返回false这种情况，任何试图发送或者接收数据的操作系统都将抛出NotYetConnectedException异常
        //因此我们通过持续调用finishConnect方法来轮询连接状态，他在建立连接之前一直返回false，直到成功建立连接后，返回true
        if (!clntChan.connect(new InetSocketAddress(server, servPort))){  
            //不断地轮询连接状态，直到完成连接  
            while (!clntChan.finishConnect()){  
                //在等待连接的时间里，可以执行其他任务，以充分发挥非阻塞IO的异步特性  
                //这里为了演示该方法的使用，只是一直打印"."  
                System.out.print(".");    
            }  
        }  
        //为了与后面打印的"."区别开来，这里输出换行符  
        System.out.print("\n");  
        //分别实例化用来读写的缓冲区  
        ByteBuffer writeBuf = ByteBuffer.wrap(argument);  
        ByteBuffer readBuf = ByteBuffer.allocate(argument.length);  
        //接收到的总的字节数  
        int totalBytesRcvd = 0;   
        //每一次调用read（）方法接收到的字节数  
        int bytesRcvd;   
        //循环执行，直到接收到的字节数与发送的字符串的字节数相等  
        while (totalBytesRcvd < argument.length){  
            //如果用来向通道中写数据的缓冲区中还有剩余的字节，则继续将数据写入信道  
            if (writeBuf.hasRemaining()){  
                clntChan.write(writeBuf);  
            }  
            //如果read（）接收到-1，表明服务端关闭，抛出异常  
            if ((bytesRcvd = clntChan.read(readBuf)) == -1){  
                throw new SocketException("Connection closed prematurely");  
            }  
            //计算接收到的总字节数  
            totalBytesRcvd += bytesRcvd;  
            //在等待通信完成的过程中，程序可以执行其他任务，以体现非阻塞IO的异步特性  
            //这里为了演示该方法的使用，同样只是一直打印"."  
            System.out.print(".");   
        }  
        //打印出接收到的数据  
        System.out.println("Received: " +  new String(readBuf.array(), 0, totalBytesRcvd));  
        //关闭信道  ，与套接字相似，信道在完成任务之后也需要关闭资源
        clntChan.close();  
    }  
}  
