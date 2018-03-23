package main.io.randomAccessFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author momo
 * @time 2017年11月14日下午1:58:07
 */
public class WriteFile {
	public static void main(String[] args) {
		String filePath = "E:\\poetry11.txt";
		RandomAccessFile raf = null;
		File file = null;
		try {
			file = new File(filePath);
			// 以读写的方式打开一个RandomAccessFile对象
			raf = new RandomAccessFile(file, "rw");
			// 将记录指针移动到该文件的最后
			raf.seek(raf.length());
			// 向文件末尾追加内容
			raf.writeUTF("这是追加内容。。");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
