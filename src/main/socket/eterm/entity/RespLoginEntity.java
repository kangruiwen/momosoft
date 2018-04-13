package main.socket.eterm.entity;

import java.util.List;

/**
 * 登录应答包封装类
 * @author kangrw
 * @time 2018年4月12日下午4:58:54
 * 
 */
public class RespLoginEntity {
	private byte[] msg;// 整个报文
	private int length; // 报文长度，注意不是message的长度
	private int sessionNums; // 可以建立的会话数目
	private List<byte[]> sessionList; //分别存储不同会话的 H1 H2 A1 RID SID
	private byte state; // 状态标志0x01=成功， 0x00=失败
	
	
	
	public byte[] getMsg() {
		return msg;
	}
	public void setMsg(byte[] msg) {
		this.msg = msg;
	}
	public boolean isPassed() {
		return state == 0x01;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getSessionNums() {
		return sessionNums;
	}
	public void setSessionNums(int sessionNums) {
		this.sessionNums = sessionNums;
	}
	public List<byte[]> getSessionList() {
		return sessionList;
	}
	public void setSessionList(List<byte[]> sessionList) {
		this.sessionList = sessionList;
	}
	public byte getState() {
		return state;
	}
	public void setState(byte state) {
		this.state = state;
	}
}
