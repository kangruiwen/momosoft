package main.socket.study.m2017_11_13.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 功能：FileChannel 初探
 * 
 * @user momo 2017年11月13日
 */
public class FileChannelT {

	public static void main(String[] args) throws Exception {
		RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
		FileChannel inChannel = aFile.getChannel();

		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {

			System.out.println("Read " + bytesRead);
			buf.flip();

			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}

			buf.clear();
			bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}

}
