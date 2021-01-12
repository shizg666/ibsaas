# 系统ibsaas
  -- ibsaas-web web服务端
  -- ibsaas-common 公共服务端
  -- ibsaas-datasource 数据源提供
  -- ibsaas-rocketmq 消息组件
  -- ibsaas-client-parking-lifang-mq 立方停车数据客户端
  -- ibsaas-client-kngiht-lifang 立方门禁数据客户端
  
## 停车模块
   停车模块目前是采用的立方停车设备，暂只支持查询业务功能。
   与web 服务端数据交互基于tcp，web端为服务端（端口8888），ibsaas-client-pakring-lifang部署在内网服务器（192.168.10.10）作为tcp的lient端
   提供数据。
  
## 门禁模块
   门禁模块采用的是立方设备，功能基于文档（OCS HTTP说明文档1.4(1).docx）。
   与web服务端数据交互基于rocketmq,指定具体topic，在配置表中配置，ibsaas-client-knight-lifang部署在内网服务器（192.168.10.10）通过消费具体指令，操作指令后通过topic返回数据
   到rocket中间件，web服务端消费数据。
   
   
   自定义jar 
   
   mvn install:install-file -Dfile=E:\langlv\repository\modbus4j\modbus4j-3.0.5.jar -DgroupId=com.infiniteautomation -DartifactId=modbus4j -Dversion=3.0.5 -Dpackaging=jar
   
   最新  
    ```
        <repositories>
            <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
            </repository>
        </repositories>
       <dependency>
           <groupId>com.github.infiniteautomation</groupId>
           <artifactId>modbus4j</artifactId>
           <version>3.0.5</version>
       </dependency>
    ```
