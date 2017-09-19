####ehcache十分钟教程
> 总结自：http://www.ehcache.org/documentation/
> 用例：2.10.4版本

####1.1 ehcache的基本信息
1.Ehcache的下载："http://ehcache.org/downloads".
2.Ehcache从1.7.1版本开始就使用slf4j作为其日志门面。
3.Java版本需要1.6以上
4.导入相应jar包，与配置文件

####1.2 API

CacheManager:
	1.Ehcache consists of a CacheManager, which manages logical data sets represented as Caches.
	2.A Cache object contains Elements, which are essentially name-value pairs.Creation of, access to, and removal of
		caches is controlled by a named CacheManager.
	3.CacheManager supports two creation modes: singleton and instance. 
	CacheManager 的创建
		1.CacheManager.newInstance(Configuration configuration) 
		2.CacheManager.create()
		3.CacheManager.create(Configuration configuration) 
		4.new CacheManager(Configuration configuration) 
	注意这四种方法的区别：
		
	
Cache: A Cache object contains Elements, which are essentially name-value pairs.
Element : A Element object while contains serious name-value pairs.





