package main.socket.protocol.mydefine;

import java.io.IOException;

/**
 * @author momo
 * @time 2017年11月7日下午10:12:09
 * 
 * 我们定义一个VoteMsgCoder接口，它提供了对投票消息进行序列化和反序列化的方法。
 * toWrie（）方法用于根据一个特定的协议，将投票消息转换成一个字节序列，
 * fromWire（）方法则根据相同的协议，对给定的字节序列进行解析，并根据信息的内容返回一个该消息类的实例。
 * 
 */
public interface VoteMsgCoder {
	byte[] toWire(VoteMsg msg) throws IOException;

	VoteMsg fromWire(byte[] input) throws IOException;
}
