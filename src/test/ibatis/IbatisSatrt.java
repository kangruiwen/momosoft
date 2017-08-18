package test.ibatis;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * @company 浙江鸿程计算机系统有限公司
 * @author kangrw
 * @time 2017年8月17日下午2:02:39
 */
public class IbatisSatrt {
	
	private String resource = "ibatis/SqlMapCommonConfig.xml"; 
	private Reader reader = null;
	
	public void init() throws IOException {
		
		//1. 设置日志信息
		Properties prop = new Properties(); 
		prop.load(Resources.getResourceAsReader("Log4j.properties"));
		PropertyConfigurator.configure(prop);
		
		//2. 获取数据库连接
		reader = Resources.getResourceAsReader(resource);
		SqlMapClient sqlMap =  SqlMapClientBuilder.buildSqlMapClient(reader);
	}
	
}
