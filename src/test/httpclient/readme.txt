下面这些转自：http://blog.sina.com.cn/s/blog_6fef491d0100v32h.html

一、应用程序这边开发时需要注意的地方：

1.  HttpURLConnection的connect()函数，实际上只是建立了一个与服务器的tcp连接，并没有实际发送http请求。
	无论是post还是get，http请求实际上直到HttpURLConnection的getInputStream()这个函数里面才正式发送出去。

2.  在用POST方式发送URL请求时，URL请求参数的设定顺序是重中之重，对connection对象的一切配置（那一堆set函数）
	都必须要在connect()函数执行之前完成。而对outputStream的写操作，又必须要在inputStream的读操作之前。
	这些顺序实际上是由http请求的格式决定的。 如果inputStream读操作在outputStream的写操作之前，会抛出例外：
    java.net.ProtocolException: Cannot write output after reading input.......
      
3.  http请求实际上由两部分组成，一个是http头，所有关于此次http请求的配置都在http头里面定义，一个是正文content。
    connect()函数会根据HttpURLConnection对象的配置值生成http头部信息，因此在调用connect函数之前，就必须把所有的配置准备好。

4.  在http头后面紧跟着的是http请求的正文，正文的内容是通过outputStream流写入的， 实际上outputStream不是一个网络流，
	充其量是个字符串流，往里面写入的东西不会立即发送到网络，而是存在于内存缓冲区中，待outputStream流关闭时，根据输入的内容生成http正文。
	至此，http请求的东西已经全部准备就绪。在getInputStream()函数调用的时候，就会把准备好的http请求正式发送到服务器了，然后返回一个输入流，
	用于读取服务器对于此次http请求的返回信息。由于http请求在getInputStream的时候已经发送出去了（包括http头和正文），因此在getInputStream()函数
	之后对connection对象进行设置（对http头的信息进行修改）或者写入outputStream（对正文进行修改） 都是没有意义的了，执行这些操作会导致异常的发生。
	
二、服务器端既Servlet端开发时需要注意的地方：

1.  对于客户端发送的POST类型的HTTP请求，Servlet必须实现doPost方法，而不能用doGet方法。

2.  用HttpServletRequest的getInputStream()方法取得InputStream的对象，比如：InputStream inStream = httpRequest.getInputStream();
	现在调用inStream.available()（该方法用于“返回此输入流下一个方法调用可以不受阻塞地从此输入流读取（或跳过）的估计字节数”）时，永远都反回0。试图使用此方法的返回值分配缓冲区，
	以保存此流所有数据的做法是不正确的。那么，现在的解决办法是
	Servlet这一端用如下实现：
	InputStream inStream = httpRequest.getInputStream();
	ObjectInputStream objInStream = new ObjectInputStream(inStream);
	Object obj = objInStream.readObject();
	// 做后续的处理
	// .....
	// .....
	而客户端，无论是否发送实际数据都要写入一个对象（那怕这个对象不用），如：
	ObjectOutputStream objOutputStrm = new ObjectOutputStream(outStrm);
	objOutputStrm.writeObject(new String("")); // 这里发送一个空数据
	// 甚至可以发一个null对象，服务端取到后再做判断处理。
	objOutputStrm.writeObject(null);
    objOutputStrm.flush();
    objOutputStrm.close();

	注意:上述在创建对象输出流ObjectOutputStream时,如果将从HttpServletRequest取得的输入流
	(即:new ObjectOutputStream(outStrm)中的outStrm)包装在BufferedOutputStream流里面,
	 则必须有objOutputStrm.flush();这一句,以便将流信息刷入缓冲输出流.如下:
	ObjectOutputStream objOutputStrm = new ObjectOutputStream(new BufferedOutputStream(outStrm));
	objOutputStrm.writeObject(null);
	objOutputStrm.flush(); // <======此处必须要有.
	objOutputStrm.close();

	HttpURLConnection是基于HTTP协议的，其底层通过socket通信实现。如果不设置超时（timeout），在网络异常的情况下，可能会导致程序僵死而不继续往下执行。
	可以通过以下两个语句来设置相应的超时：
	System.setProperty("sun.net.client.defaultConnectTimeout", 超时毫秒数字符串);
	System.setProperty("sun.net.client.defaultReadTimeout", 超时毫秒数字符串);
	其中： 
	sun.net.client.defaultConnectTimeout：连接主机的超时时间（单位：毫秒）
	sun.net.client.defaultReadTimeout：从主机读取数据的超时时间（单位：毫秒）	
	例如：
	System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
	System.setProperty("sun.net.client.defaultReadTime
	Java中可以使用HttpURLConnection来请求WEB资源。
	HttpURLConnection对象不能直接构造，需要通过URL.openConnection()来获得HttpURLConnection对象，示例代码如下：