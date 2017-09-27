####Maven碰到依赖jar包时找不到的情况，需要手动加入
@(maven)
在项目中有时会碰到这种情况，不管是代理服务器还是私服也好，总有一些jar包是依赖不进来的，这个时候就需要我们将本地jar包进行手动加入
在cmd中输入：

```
mvn install:install-file -Dfile={path../ojdbc.jar} -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.1.0 -Dpackaging=jar
```
其中Dfile是相对于cmd位置的所需要加入jar包的位置，也可以是绝对地址
DgroupId、DartifactId、Dversion、Dpackaging是将jar包加入Maven库的坐标位置

例如：前一段时间加入11.2.0.1.0版本的ojdbc6的jar包，但是依赖不进来，手动加入时就在cmd中输入：

```
C:\Users\administrator>mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc6
 -Dversion=11.2.0.1.0 -Dpackaging=jar -Dfile=E:\oracle11g\product\11.2.0\dbhome_
1\jdbc\lib\ojdbc6.jar
```

11.2.0.1.0 -Dpackaging=jar -Dfile=E:\oracle11g\product\11.2.0\dbhome_
1\jdbc\lib\ojdbc6.jar