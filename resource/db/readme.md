####数据库连接池的创建

#####一般创建连接步骤

```java
String connUrl = "jdbc:mysql://your.database.domain/yourDBname";
Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection (connUrl);
```
**说明:** 在JDBC规范中要求Driver类必须向DriverManager注册自己，在第二步Class.forName中，虽然没有newInstance()，但由源码可知，它把注册的这个部分放在了静态块中，所以，当JVM启动的时候，就已经将这段进行了加载。

```java
public class Driver extends NonRegisteringDriver implements java.sql.Driver {
	...
    static {
        try {
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }
    ...
}
```

