package main.common.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader {

	/**
	 * 读取resources下的.properties文件
	 * @param filename
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map read(String filename){
		Map map = new HashMap();
		try {
			Properties pros=new Properties();
			pros.load(PropertiesReader.class.getClassLoader().getResourceAsStream(filename));
			 Enumeration propertyNames = pros.propertyNames();
			   while(propertyNames.hasMoreElements()){
			    String key=(String)propertyNames.nextElement();
			    String key2=new String(key.getBytes("ISO-8859-1"),"UTF-8");
			    String value=pros.getProperty(key);
			    map.put(key2,value);
			    
			   }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}



	
}
