package main.common.util;

import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * @author momo
 * @time 2017年8月17日上午8:53:54 连接池实现
 *       方法转自:http://www.cnblogs.com/xdp-gacl/p/4002804.html
 */
public class DataSourceUtil implements DataSource{
	
	private static LinkedList<Connection> listConnections = new LinkedList<Connection>();

	static {
		// 在静态代码块中加载db.properties数据库配置文件
		InputStream in = DataSourceUtil.class.getClassLoader().getResourceAsStream("db/mysqldb.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			// 数据库连接池的初始化连接数大小
			int jdbcPoolInitSize = Integer.parseInt(prop.getProperty("jdbcPoolInitSize"));
			// 加载数据库驱动
			Class.forName(driver);
			for (int i = 0; i < jdbcPoolInitSize; i++) {
				Connection conn = DriverManager.getConnection(url, username,password);
				System.out.println("获取到了链接" + conn);
				listConnections.add(conn);
			}
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	
	public Connection getConnection() throws SQLException {
		// 如果数据库连接池中的连接对象的个数大于0
		if (listConnections.size() > 0) {
			// 从listConnections集合中获取一个数据库连接
			final Connection conn = listConnections.removeFirst();
			System.out.println("listConnections数据库连接池大小是" + listConnections.size());
			// 返回Connection对象的代理对象
			return (Connection) Proxy.newProxyInstance(DataSourceUtil.class.getClassLoader(), conn.getClass().getInterfaces(),
					new InvocationHandler() {
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (!method.getName().equals("close")) {
								return method.invoke(conn, args);
							} else {
								// 如果调用的是Connection对象的close方法，就把conn还给数据库连接池
								listConnections.add(conn);
								System.out.println(conn + "被还给listConnections数据库连接池了！！");
								System.out.println("listConnections数据库连接池大小为" + listConnections.size());
								return null;
							}
						}
					});
		} else {
			throw new RuntimeException("对不起，数据库忙");
		}
	}
	
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		
	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return null;
	}
	
	

}
