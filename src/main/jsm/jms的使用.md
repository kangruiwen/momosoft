#### 1.activeMQ的下载与安装
	基本环境
		activeMQ 5.15.x 
> he minimum Java version has been upgraded to Java 8.
> 即至少需要Jdk 1.8以上

		activeMQ 4.x
		
> Java Runtime Environment (JRE)  JRE 1.7 (1.6 for version <=5.10.0)
		maven环境最好是 maven 3以上
		
##### 1.1 activeMQ的下载
		登陆activeMQ的官网http://activemq.apache.org/
		下载相应版本，注意windows与linux版本的不同
		
##### 1.2 windows下的安装
		就是傻瓜是安装，下一步即可
		
		安装成功之后，进入文件夹，进入bin目录，根据自己机器配置选择win32或win64，然后点击activeMQ启动服务。但是这种方式有一点不好，就是服务窗口不能够关闭，所以他还提供了以服务的方式启动activemq。
		
		我们在相同目录下打开，InstallService.bat文件，他就会将activemq服务进行安装，我们进入服务界面以后，就可以以服务的方式对他进行管理了。
		管理界面的打开与linux相同

##### 1.3 linux下的安装
		1.3.1 下载安装
			在官网上下载好activeMQ.tar.gz 压缩文件之后，通过xftp移到linux文件目录下通过命令
			tar -zxvf activeMQ.tar.gz 解压文件
			到此为止，安装完毕，是不是很简单
		1.3.2 使用
			在解压目录下可以看到被解压的activeMQ文件夹，进入之后可以看到文件中有bin目录，进入bin目录，启动activemq
			./activemq start
			这样activemq服务就启动了
			
			我们可以登陆验证
			12.0.0.1:8161  其中8161是activemq的默认端口号
			不出错的情况下我们就登上了activemq
			点击Manage ActiveMQ Broker可以对服务中的消息进行管理，默认登陆名和密码是admin
				
			停止服务：
			./activemq stop

####2.使用JMS接口连接消息中间件（activemq）
#####1.JMS 规范
		1.1 Java消息服务定义
			Java消息服务（Java Message Service）即JMS，是一个Java平台中面向消息中间件的API，用于在两个应用程序之间或分布式系统中发送/接收消息，进行异步通信
	
#####2.JMS编码接口之间的关系：
	
															sendTo
											MessageProducer -----> Destination
												  |
												  |
	ConnectionFactory -----> Connection -----> Session ----->Message
												  |
												  |       receiveFrom
											MessageConsumer -----> Destination
											
#####3.JMS 相关概念
		提供者：实现JMS规范的消息中间件服务器
		客户端：发送或接收消息的应用程序
		生产者/发布者：创建并发送消息的客户端
		消费者/订阅者：接收并处理消息的客户端
		消息：应用程序之间传递的数据内容
		消息模式：在客户端之间传递消息的方式，JMS中定义了主题和队列两种模式
#####4.JMS消息模式
		4.1 队列模式
			4.1.1 客户端：生产者，消费者
			4.1.2 队列中的消息只能被一个消费者消费
			4.1.3 消费者可以随时消费队列中的消息
			4.1.4 丢列中的消息只能被一个消费者消费，不能够被重复消费
		4.2 主题模式
			4.1.1 客户端：发布者，订阅者
			4.1.2 主题中的消息被所有订阅者消费
			4.1.3 消费者不能消费订阅之前就发送到主题中的消息
			
#####5.JMS编码接口
		ConnectionFactory 用于创建连接到消息中间件的链接工厂，一般就是消息中间键的应用提供商提供
		Connection 代表了应用程序和消息服务器之间的通信链路
		Destination 指消息发布和接收的地点，包括队列和主题，这个目的地一般就是url+name url一般就是我们的消息服务器，url是队列或者主题的名称
		Session 表示一个单线程的上下文，用于发送和接收消息
		MessageConsumer 由会话创建，用于接收发送到目标的消息
		MessageProducer 由会话创建，用于发送消息到目标
		Message 是在消费者和生产者之间传送的对象，消息头，一组消息属性，一个消息体
		
		另外说明；链接可以创建多个会话，每个会话是在一个线程上下文的，是单线程的。而链接可以共多个线程使用
