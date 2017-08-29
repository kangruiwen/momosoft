package test.httpclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

/**
 * @author momo
 * @time 2017年8月25日下午2:05:42
 * HttpURLConnection类的作用是通过HTTP协议向服务器发送请求，并可以获取服务器发回的数据。 
 * 他是jdk中的一个类
 * 
 * 并且需要注意的是下边有个MagicMatch类，它的作用是获取Content-Type类型，这在我们上传文件的时候非常的重要
 * 
 * 转自：http://blog.csdn.net/wangpeng047/article/details/38303865
 * 
 * 在使用HttpUrlConnection建立连接的时候要明白相应与请求对应的输入输出流，大概关系是这样的：
 * 
 * 		>>>------服务器 ----->>>
 * 		|					|
 * 	Output(请求)		    Input(响应)
 * 		|					|
 * 		<<<-----应用程序----<<<
 */
public class HttpUrlConnectionUtil {
	
	/**
	 * 向一个页面发送POST请求并且返回数据
	 * 流程：
	 * 读取初始url --> 将url转变为URL类对象 --> 打开URL连接 --> 进行一些连接设置 ---> 建立HttpUrlConnection连接 -->
	 * --> 读取网页内容(HttpUrlConnection.getInputStream())
	 * 
	 * 
	 */
	@Test
	public void httpUrlRequest() {
		URL url;
		try {
			url = new URL("http://localhost:8080/dsap/login_ajax");
			URLConnection rulConnection = url.openConnection();
			// 此处的urlConnection对象实际上是根据URL的 请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection,
			// 故此处最好将其转化 为HttpURLConnection类型的对象,以便用到HttpURLConnection更多的API.如下: 
			HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection; 
			
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false; 
			httpUrlConnection.setDoOutput(true); //如果需要向服务器传入一些数据，使用httpUrlConnection.getOutputStream()时，必须为true，默认为false
			// 设置是否从httpUrlConnection读入，默认情况下是true; 
			httpUrlConnection.setDoInput(true); //如果需要调用响应信息httpUrlConnection.getInputStream()，必须设为true，默认为true
			// Post 请求不能使用缓存 
			httpUrlConnection.setUseCaches(false);
			
			// 设定传送的内容类型是可序列化的java对象 (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException) 
			httpUrlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object"); 
			
			//这个方法可以传递一些post参数信息
			httpUrlConnection.setRequestProperty("usercode", "admin");
			httpUrlConnection.setRequestProperty("password", "hcss_zjhcxt");
			
			// 设定请求的方法为"POST"，默认是GET 
			httpUrlConnection.setRequestMethod("POST"); 
			
			// 超时设置，防止 网络异常的情况下，可能会导致程序僵死而不继续往下执行
			System.setProperty("sun.NET.client.defaultConnectTimeout", "30000");  
			System.setProperty("sun.net.client.defaultReadTimeout", "30000");  

			// 连接，从上述第2条中url.openConnection()至此的配置必须要在connect之前完成， 
			httpUrlConnection.connect(); 
			
			// 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法， 所以在开发中不调用上述的connect()也可以)。 
			OutputStream outStrm = httpUrlConnection.getOutputStream(); 
			
			// 现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象。 
			ObjectOutputStream objOutputStrm = new ObjectOutputStream(outStrm); 
			// 向对象输出流写出数据，这些数据将存到内存缓冲区中 
			objOutputStrm.writeObject(new String("我是测试数据")); 
			// 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream） 
			objOutputStrm.flush(); 
			// 关闭流对象。此时,不能再向对象输出流写入任何数据,先前写入的数据存在于内存缓冲区中,在调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器 
			objOutputStrm.close(); 
			
			// HttpURLConneciton获取响应
			// 调用HttpURLConnection连接对象的getInputStream()函数,将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。 并且获取响应
			InputStream is = httpUrlConnection.getInputStream(); // <===注意，实际发送请求的代码段就在这里 
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuffer sbf = new StringBuffer();
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        String result = sbf.toString();
	        System.out.println(result);
			
			// 上边的httpConn.getInputStream()方法已调用,本次HTTP请求已结束,下边向对象输出流的输出已无意义， 
			// 既使对象输出流没有调用close()方法，下边的操作也不会向对象输出流写入任何数据. 
			// 因此，要重新发送数据时需要重新创建连接、重新设参数、重新创建流对象、重新写数据、 
			// 重新发送数据(至于是否不用重新这些操作需要再研究) 
			//objOutputStrm.writeObject(new String("")); 
			//httpUrlConnection.getInputStream(); 
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	  /** 
     * 上传图片 
     * @param urlStr 
     * @param textMap 
     * @param fileMap 
     * @return 
     */  
    public static String formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) {  
        String res = "";  
        HttpURLConnection conn = null;  
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符    
        try {  
            URL url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setReadTimeout(30000);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
  
            OutputStream out = new DataOutputStream(conn.getOutputStream());  
            // text    
            if (textMap != null) {  
                StringBuffer strBuf = new StringBuffer();  
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, String> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");  
                    strBuf.append(inputValue);  
                }  
                out.write(strBuf.toString().getBytes());  
            }  
  
            // file    
            if (fileMap != null) {  
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, String> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    File file = new File(inputValue);  
                    String filename = file.getName();  
                    MagicMatch match = Magic.getMagicMatch(file, false, true);  
                    String contentType = match.getMimeType();  
  
                    StringBuffer strBuf = new StringBuffer();  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");  
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");  
  
                    out.write(strBuf.toString().getBytes());  
  
                    DataInputStream in = new DataInputStream(new FileInputStream(file));  
                    int bytes = 0;  
                    byte[] bufferOut = new byte[1024];  
                    while ((bytes = in.read(bufferOut)) != -1) {  
                        out.write(bufferOut, 0, bytes);  
                    }  
                    in.close();  
                }  
            }  
  
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();  
            
            // 读取返回数据    
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                strBuf.append(line).append("\n");  
            }  
            res = strBuf.toString();  
            reader.close();  
            reader = null;  
        } catch (Exception e) {  
            System.out.println("发送POST请求出错。" + urlStr);  
            e.printStackTrace();  
        } finally {  
            if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
        }  
        return res;  
    }  
}
