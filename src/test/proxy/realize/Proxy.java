package test.proxy.realize;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;

/**
 * @author momo
 * @time 2017年8月21日上午9:07:51
 * 
 * 代理类的生成
 * 
 */
public class Proxy {
	
	public static Object newProxyInstance(Class<?>[] interfaces,InvocationHandler h) throws Exception {
		
		// 1.通过反射构建源码，在这一步需要注意对被代理对象接口中的方法的回调
		String nextLine = "\r\n";
		String interfaceStr = "";
		String methodStr = "";
		String methodParam = "";
		String methodVal = "";
		for(int i = 0 ; i < interfaces.length ; i ++ ) {
			Method[] methods = interfaces[i].getMethods();
			for(int j = 0; j < methods.length; j++ ) {
				methodStr += "public " + methods[j].getReturnType().toString() +" " + methods[j].getName() + "() {" + nextLine + 
						"h.invoke(" + methods[j].getName() + "); " + nextLine + 
						"}" + nextLine ;
				methodParam += "private Method " + methods[j].getName() + " ;" + nextLine ;
				methodVal += methods[j].getName() + " = " + interfaces[i].getName() + ".class.getMethod(\"" + methods[j].getName() + "\");"  + nextLine ;

			}
			
			interfaceStr += interfaces[i].getName() ;
			if(i != interfaces.length-1){
				interfaceStr += ",";
			}
		}
		
		String sourceCode ="package main;"  + nextLine + 
			"import test.proxy.realize.InvocationHandler;" + nextLine + 
			"import java.lang.reflect.Method;" + nextLine + 
			"public class $Proxy0 implements " + interfaceStr +"{ " + nextLine + 
			methodParam + nextLine + 
			"InvocationHandler h; " + nextLine + 
			"public  $Proxy0(InvocationHandler h){ " + nextLine + 
			"try {"  + nextLine + 
			methodVal + nextLine + 
			"} catch (Exception e) {" + nextLine + 
			"e.printStackTrace();"  + nextLine + 
			"}" + nextLine + 
			"	this.h = h; " + nextLine + 
			"} " + nextLine + 
			methodStr + nextLine + 	
		"}";
		
		// 2.将源码写入文件系统
		String fileName = System.getProperty("user.dir") +   "\\src\\main\\$Proxy0";
		System.out.println(fileName);
		File file = new File(fileName + ".java");
		FileUtils.writeStringToFile(file, sourceCode);
		
		// 3.对文件系统中的Java源码进行编译
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> units = fileMgr.getJavaFileObjects(fileName + ".java");
		CompilationTask task = compiler.getTask(null, fileMgr, null, null, null, units);
		task.call(); // 这一步就是编译的那一步，效果同javac
		fileMgr.close();
		
		// 4. 对字节码进行加载
		ClassLoader cl = ClassLoader.getSystemClassLoader();//
		Class<?> c = cl.loadClass( "main.$Proxy0");
		
		//5. 加载完毕之后通过反射，构建对象并返回
		Constructor<?> constructor = c.getConstructor(new Class<?>[]{InvocationHandler.class});
		return constructor.newInstance(new Object[]{h});
	}
	
}
