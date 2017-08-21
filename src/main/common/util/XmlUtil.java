package main.common.util;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @author momo
 * @time 2017年8月17日上午8:55:11
 * Xml工具类
 */
public class XmlUtil {
	
	
	/**
     * 使用Dom4j 写入 XML  
	 * @param obj java对象
	 * @param list 数据list
	 * @param os 输出流
	 */
    public static void writeXmlDocument(Object obj,List<?> list,OutputStream os) {   
        try {   
        	XMLWriter writer = null;// 声明写XML的对象    
        	OutputFormat format = OutputFormat.createPrettyPrint();   
            // 新建student.xml文件并新增内容   
            Document document = DocumentHelper.createDocument();   
            Element root = document.addElement("MSG");//添加根节点   MSG
            Element secondRoot = root.addElement("Body");//添加二级节点 Body
            Field[] properties = obj.getClass().getDeclaredFields();//获得实体类的所有属性   
            Object val = null;
            String valStr = null;
            for (Object t : list) {                               
                Element itemEle = secondRoot.addElement("Item");            
                
                for (int i = 0; i < properties.length; i++) {       
                	String proName = properties[i].getName();
                	
                	if(proName.equals("serialVersionUID") || proName.startsWith("zxsj")){
                		continue;
                	}
                    //反射get方法       
                    Method meth = t.getClass().getMethod("get" + properties[i].getName().substring(0, 1).toUpperCase()   
                                    + properties[i].getName().substring(1));   
                    //为二级节点添加属性，属性值为对应属性的值   
                    val = meth.invoke(t);
                    if(val == null){
                    	valStr = null;
                    	itemEle.addElement(properties[i].getName());
                    }else{
                    	valStr = val.toString();
                    	itemEle.addElement(properties[i].getName()).setText(valStr);   
                    }
                   
                }   
            }   
            //生成XML文件
            writer = new XMLWriter(os,format); 
            writer.write(document);   
            writer.close();   
        } catch (Exception e) {   
        	e.printStackTrace();
            System.out.println("XML文件写入失败");   
        }   
    }
}
