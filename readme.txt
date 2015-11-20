网站页面静态化工具包
使用方法如下
1.添加jar包坐标依赖
        <dependency>
             <groupId>com.jd</groupId>
             <artifactId>html-generater</artifactId>
             <version>1.0-SNAPSHOT</version>
         </dependency>

2.spring文件进行配置
 <bean class="com.jd.generater.manager.GeneraterManager" destroy-method="destroy" lazy-init="false">
        <constructor-arg name="urlFileMap">
            <map>
                <entry key="http://try.jd.com" value="D:/export/Data/try.jd.local/try/index.html"/>
            </map>
        </constructor-arg> <!--访问url生成指定静态文件-->
        <constructor-arg name="ip" value="127.0.0.1"/><!--指定访问ip-->
        <constructor-arg name="encoding" value="UTF-8"/>
        <constructor-arg name="delay" value="10"/> <!--延迟启动时间，单位秒-->
        <constructor-arg name="period" value="120"/> <!--周期执行时间，单位秒-->
 </bean>
