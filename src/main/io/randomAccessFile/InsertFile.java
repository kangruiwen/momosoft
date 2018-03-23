package main.io.randomAccessFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author momo
 *         注：RandomAccessFile不能向文件的指定位置插入内容，如果直接将文件记录指针移动到中间某位置后开始输出，则新输出的内容会覆盖文件原有的内容
 *         ， 如果需要向指定位置插入内容，程序需要先把插入点后面的内容写入缓存区，等把需要插入的数据写入到文件后，再将缓存区的内容追加到文件后面。
 * @time 2017年11月14日下午2:14:02
 */
public class InsertFile {
	public static void insert(String filePath, long pos, String insertContent) throws IOException {
		RandomAccessFile raf = null;
		File tmp = File.createTempFile("tmp", null);
		tmp.deleteOnExit();
		try {
			// 以读写的方式打开一个RandomAccessFile对象
			raf = new RandomAccessFile(new File(filePath), "rw");
			// 创建一个临时文件来保存插入点后的数据
			FileOutputStream fileOutputStream = new FileOutputStream(tmp);
			FileInputStream fileInputStream = new FileInputStream(tmp);
			// 把文件记录指针定位到pos位置
			raf.seek(pos);
			// ------下面代码将插入点后的内容读入临时文件中保存-----
			byte[] bbuf = new byte[64];
			// 用于保存实际读取的字节数据
			int hasRead = 0;
			// 使用循环读取插入点后的数据
			while ((hasRead = raf.read(bbuf)) != -1) {
				// 将读取的内容写入临时文件
				fileOutputStream.write(bbuf, 0, hasRead);
			}
			// -----下面代码用于插入内容 -----
			// 把文件记录指针重新定位到pos位置
			raf.seek(pos);
			// 追加需要插入的内容
			raf.write(insertContent.getBytes());
			// 追加临时文件中的内容
			while ((hasRead = fileInputStream.read(bbuf)) != -1) {
				// 将读取的内容写入临时文件
				raf.write(bbuf, 0, hasRead);
			}
			fileOutputStream.close();
			fileInputStream.close();
		} catch (Exception e) {
			System.out.println("Exception");
		}
	}

	public static void main(String[] args) throws IOException {
		String filePath = "E:\\poetry.txt";
		insert(filePath, 802, "--插入指定位置指定内容--");
	}
}
