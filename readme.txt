网站页面静态化工具包
使用方法如下
1.添加jar包坐标依赖
        <dependency>
             <groupId>com.jd</groupId>
             <artifactId>html-generater</artifactId>
             <version>1.0-SNAPSHOT</version>
         </dependency>

2.spring文件进行配置
  <!--静态页面生成配置-->
    <bean class="com.jd.generater.manager.GeneraterManager" destroy-method="destroy" lazy-init="false">
         <!--需要生成静态页面的配置列表-->
        <constructor-arg name="urlFileList">
               <list>
                 <!--需要生成静态页面的配置对象-->
                 <bean class="com.jd.generater.domain.UrlFileConfig">
                    <!--页面正确返回的检验关键字-->
                     <constructor-arg name="key" value="京东试用中心-专业的综合网上免费试用平台"/>
                      <!--静态页面生成路径-->
                     <constructor-arg name="path" value="/export/App/try.home.jd.com/index.html"/>
                     <!--需要静态化的url-->
                     <constructor-arg name="url" value="http://try.home.jd.com/index"/>
                 </bean>
                 <bean class="com.jd.generater.domain.UrlFileConfig">
                       <constructor-arg name="key" value="京东试用中心-专业的综合网上免费试用平台"/>
                       <constructor-arg name="path" value="/export/App/try.home.jd.com/home.html"/>
                       <constructor-arg name="url" value="http://try.home.jd.com/home/home"/>
                 </bean>
               </list>
        </constructor-arg>
        <constructor-arg name="ip" value="127.0.0.1"/><!--指定访问ip-->
        <constructor-arg name="encoding" value="UTF-8"/>
        <constructor-arg name="delay" value="10"/> <!--延迟启动时间，单位秒-->
        <constructor-arg name="period" value="120"/> <!--周期执行时间，单位秒-->
    </bean>
