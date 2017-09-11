package main.jsm.test.p2p;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * 功能：队列模式的消息演示
 * 生产者消费者模式
 * @user momo
 * 2017年8月14日
 */
public class AppProducer {
	
	private static final String url = "tcp://127.0.0.1:61616";
	private static final String queueName = "queue-test";
	
	public static void main(String[] args) throws JMSException {
		
		//1.创建ConnectionFactory
		ConnectionFactory factory = new ActiveMQConnectionFactory(url);// 说明文档中说了，Factory一般由消费中间件的服务商提供，故这里是ActiveMQ
		//2.创建连接
		Connection connection = factory.createConnection();
		//3.启动连接
		connection.start();
		//4.创建会话
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.创建一个目标
		Destination dest = session.createQueue(queueName);
		//6.创建一个生产者
		MessageProducer producer = session.createProducer(dest);
		
		for(int i = 0; i < 100; i++) {
			//7.创建消息
			TextMessage msg = session.createTextMessage("test" + i);
			producer.send(msg);
			System.out.println("发送消息" + msg.getText());
		}
		
		connection.close();
	}
}





