package main.io.randomAccessFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 * RandomAccessFile 的使用
 * @author momo
 * @time 2017年11月14日上午11:58:13
 * 
 * long getFilePointer(); 返回文件记录指针的当前位置
 * void seek(long pos); 将文件记录指针定位到pos位置
 * 
 * RandomAccessFile有两个构造器，其实这两个构造器基本相同，只是指定文件的形式不同而已，一个使用String参数来指定文件名，一个使用File参数来指定文件本身。
 * 	除此之外，创建RandomAccessFile对象还需要指定一个mode参数。该参数指定RandomAccessFile的访问模式，有以下4个值：
 * 	“r” 以只读方式来打开指定文件夹。如果试图对改RandomAccessFile执行写入方法，都将抛出IOException异常。
 * 	“rw” 以读，写方式打开指定文件。如果该文件尚不存在，则试图创建改文件。
 * 	“rws” 以读，写方式打开指定文件。相对于”rw” 模式，还要求对文件内容每个更新都同步写入到底层设备。
 * 	“rwd” 以读，写方式打开指定文件。相对于”rw” 模式，还要求对文件内容每个更新都同步写入到底层设备。
 */
public class ReadFile {
	public static void main(String[] args) {
		String filePath="E:\\poetry.txt";
	      RandomAccessFile raf=null;
	      File file=null;
	      try {
	          file=new File(filePath);
	          raf=new RandomAccessFile(file,"r");
	          System.out.println("输入内容：");
	          System.out.println("开始文件记录指针位置：" + raf.getFilePointer());
	          //移动文件记录指针的位置,这个是移动到1000字节的位置
	          //raf.seek(1000);
	          byte[] b=new byte[1024];
	          int hasRead=0;
	          //循环读取文件
	          while((hasRead=raf.read(b))>0){
	              //输出文件读取的内容
	              System.out.print(new String(b,0,hasRead));
	          }
	      }catch (IOException e){
	          e.printStackTrace();
	      }finally {
	          try {
				raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	      }
	}
}
