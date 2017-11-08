package main.socket.protocol.mydefine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author momo
 * @time 2017年11月7日下午10:23:37
 * 
 * 对消息进行编码的程序。与基于文本的格式相反，二进制格式使用固定大小的消息，每条消息由一个特殊字节开始，
 * 该字节的最高六位为一个”魔术值“010101，该字节的最低两位对两个布尔值进行了编码，消息的第二个字节总是0，
 * 第三、四个字节包含了candidateID值，只有响应消息的最后8个字节才包含了选票总数信息。
 * 
 */

public class VoteMsgBinCoder implements VoteMsgCoder {

	// manifest constants for encoding
	public static final int MIN_WIRE_LENGTH = 4;
	public static final int MAX_WIRE_LENGTH = 16;
	public static final int MAGIC = 0x5400; // 0101 0100 0000 0000
	public static final int MAGIC_MASK = 0xfc00;// 1111 1100 0000 0000
	public static final int MAGIC_SHIFT = 8;
	public static final int RESPONSE_FLAG = 0x0200;// 0000 0010 0000 0000
	public static final int INQUIRE_FLAG = 0x0100; // 0000 0001 0000 0000

	public byte[] toWire(VoteMsg msg) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteStream); // converts
																	// ints

		short magicAndFlags = MAGIC;
		if (msg.isInquiry()) {
			magicAndFlags |= INQUIRE_FLAG;
		}
		if (msg.isResponse()) {
			magicAndFlags |= RESPONSE_FLAG;
		}
		out.writeShort(magicAndFlags);
		// We know the candidate ID will fit in a short: it's > 0 && < 1000
		out.writeShort((short) msg.getCandidateID());
		if (msg.isResponse()) {
			out.writeLong(msg.getVoteCount());
		}
		out.flush();
		byte[] data = byteStream.toByteArray();
		return data;
	}

	public VoteMsg fromWire(byte[] input) throws IOException {
		// sanity checks
		if (input.length < MIN_WIRE_LENGTH) {
			throw new IOException("Runt message");
		}
		ByteArrayInputStream bs = new ByteArrayInputStream(input);
		DataInputStream in = new DataInputStream(bs);
		int magic = in.readShort();
		if ((magic & MAGIC_MASK) != MAGIC) {
			throw new IOException("Bad Magic #: "
					+ ((magic & MAGIC_MASK) >> MAGIC_SHIFT));
		}
		boolean resp = ((magic & RESPONSE_FLAG) != 0);
		boolean inq = ((magic & INQUIRE_FLAG) != 0);
		int candidateID = in.readShort();
		if (candidateID < 0 || candidateID > 1000) {
			throw new IOException("Bad candidate ID: " + candidateID);
		}
		long count = 0;
		if (resp) {
			count = in.readLong();
			if (count < 0) {
				throw new IOException("Bad vote count: " + count);
			}
		}
		// Ignore any extra bytes
		return new VoteMsg(resp, inq, candidateID, count);
	}
}  
