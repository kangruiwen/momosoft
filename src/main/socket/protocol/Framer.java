package main.socket.protocol;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * @author momo
 * 下面给出一个自定义实现上面两种成帧技术的Demo（书上的例子），
 * 先定义一个Framer接口，它由两个方法
 * 	frameMag（）方法用来添加成帧信息并将指定消息输出到指定流，
 * 	nextMsg（）方法则扫描指定的流，从中抽取出下一条消息。
 */
public interface Framer {
	void frameMsg(byte[] message, OutputStream out) throws IOException;

	byte[] nextMsg() throws IOException;
}