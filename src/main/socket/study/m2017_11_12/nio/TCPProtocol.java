package main.socket.study.m2017_11_12.nio;

import java.nio.channels.SelectionKey;  
import java.io.IOException;  
/**
 * 功能：该接口定义了通用TCPSelectorServer类与特定协议之间的接口， 它把与具体协议相关的处理各种I/O的操作分离了出来，以使不同协议都能方便地使用这个基本的服务模式。 
 * @user momo
 * 2017年11月12日
 */
public interface TCPProtocol{  
    //accept I/O形式  
    void handleAccept(SelectionKey key) throws IOException;  
    //read I/O形式  
    void handleRead(SelectionKey key) throws IOException;  
    //write I/O形式  
    void handleWrite(SelectionKey key) throws IOException;  
}  