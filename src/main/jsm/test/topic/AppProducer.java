package main.jsm.test.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * 功能：主题模式的消息演示
 * 发布订阅
 * @user krw
 * 2017年8月14日
 */
public class AppProducer {
	
	private static final String url = "tcp://127.0.0.1:61616";
	private static final String topicName = "topic-test";
	
	public static void main(String[] args) throws JMSException {
		
		//1.创建ConnectionFactory
		ConnectionFactory factory = new ActiveMQConnectionFactory(url);
		//2.创建连接
		Connection connection = factory.createConnection();
		//3.启动连接
		connection.start();
		//4.创建会话
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.创建一个目标
		Destination dest = session.createTopic(topicName);
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





