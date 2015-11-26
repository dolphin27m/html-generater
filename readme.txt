网站页面静态化工具包
两种使用方法如下
1.spring 配置
<!--静态页面生成配置-->
    <bean class="com.jd.generater.manager.GeneraterManager" destroy-method="destroy" lazy-init="false">
         <!--需要生成静态页面的配置列表-->
        <constructor-arg name="urlFileList">
               <list>
                 <!--需要生成静态页面的配置对象-->
                 <bean class="com.jd.generater.domain.UrlFileConfig">
                    <!--页面正确返回的校验关键字，一般取页面title中的文字即可，防止返回错误页面时覆盖原来的正确页面-->
                     <constructor-arg name="key" value="静态页面测试"/>
                      <!--静态页面生成路径-->
                     <constructor-arg name="path" value="/export/data/html/index.html"/>
                     <!--需要静态化的url-->
                     <constructor-arg name="url" value="http://localhost/index"/>
                 </bean>
               </list>
        </constructor-arg>
        <constructor-arg name="ip" value="127.0.0.1"/><!--指定访问ip-->
        <constructor-arg name="encoding" value="UTF-8"/>
        <constructor-arg name="delay" value="10"/> <!--延迟启动时间，单位秒-->
        <constructor-arg name="period" value="120"/> <!--周期执行时间，单位秒-->
    </bean>
2.直接创建对象，传入需要的参数即可