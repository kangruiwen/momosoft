package main.socket.tcp.bio.pool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 功能：连接池实现TCP链接
 * 
 * 该类通过Executor接口实现服务器
 * 这里对newCachedThreadPool 连接池进行一个说明：
 * newCachedThreadPool 建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
 * @user momo
 * 2017年11月8日
 */
public class ServerPool2 {
	
	public static void main(String[] args) throws IOException{
		//服务端在20006端口监听客户端请求的TCP连接
        ServerSocket server = new ServerSocket(20006);  
        Socket client = null;  
        //通过调用Executors类的静态方法，创建一个ExecutorService实例  
        //ExecutorService接口是Executor接口的子接口  
        Executor service = Executors.newCachedThreadPool();  
        boolean f = true;  
        while(f){  
            //等待客户端的连接  
            client = server.accept();  
            System.out.println("与客户端连接成功！");  
            //调用execute()方法时，如果必要，会创建一个新的线程来处理任务，但它首先会尝试使用已有的线程，  
            //如果一个线程空闲60秒以上，则将其移除线程池；  
            //另外，任务是在Executor的内部排队，而不是在网络中排队  
            service.execute(new ServerThread(client));  
        }   
        server.close();  
    }  
	
}
