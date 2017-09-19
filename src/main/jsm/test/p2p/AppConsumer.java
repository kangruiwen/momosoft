package main.jsm.test.p2p;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 功能：消息队列消费者
 * @user momo
 * 2017年8月14日
 */
public class AppConsumer {
	private static final String url = "tcp://127.0.0.1:61616";
	private static final String queueName = "queue-test";
	
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
		Destination dest = session.createQueue(queueName);
		//6.创建一个消费者
		MessageConsumer consumer = session.createConsumer(dest);
		
		//7.创建一个监听器
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage msg = (TextMessage) message;
				try {
					System.out.println("收到消息：" + msg.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
			
		//connection.close();
	}
}
