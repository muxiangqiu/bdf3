# bdf3
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

>bdf3基于spring-boot研发的开发框架。包含用户、角色、菜单、权限（最小粒度为组件）、数据导入、字典、日志、实时通讯、公众号、微程序、云数据库、个人中心、云数据源、用户个性化和一个或多个数据库实例的独立数据库模式的多租户功能模块。功能模块化，自动化，参考spring boot项目结构构建，提供一些列预定义依赖项目快。基于bdf3快速开发企业管理系统。

## 演示地址
1. [传统风格多租户版](http://106.14.191.97:8081/bdf3.security.ui.view.Main.d) 公司ID/用户名/密码：master/admin/123456
2. [实时通讯+公众号+微程序风格非多租户版](http://106.14.191.97:8080/bdf3.notice.ui.view.Chat.d) 用户名/密码：admin/123456
3. [方块风格非多租户版](http://106.14.191.97:8080/bdf3.security.ui.view.Portal.d) 用户名/密码：admin/123456
4. [传统风格非多租户版](http://106.14.191.97:8080/bdf3.security.ui.view.Main.d) 用户名/密码：admin/123456

### 所有用户的密码都是123456

## 快速入门
>由于基于spring-boot，bdf3项目搭建和spring-boot几乎一样，不同的是依赖的jar包不一样。

1. 创建一个标准的Maven项目bdf3-sample，项目打包类型为jar，项目的父项目指向bdf3-starter-parent，最终生成项目的pom文件如下：
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <!-- 继承的父项目 -->
  <parent>
    <groupId>com.bstek.bdf3</groupId>
    <artifactId>bdf3-starter-parent</artifactId>
    <version>1.1.0-SNAPSHOT</version>
  </parent>
  <artifactId>bdf3-sample</artifactId>
  <dependencies>
    <!-- bdf3预定义依赖，简化依赖的复杂度 -->
    <dependency>
      <groupId>com.bstek.bdf3</groupId>
      <artifactId>bdf3-starter</artifactId>
    </dependency>
    <!-- 开发测试工具 -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
      <scope>provided</scope>
    </dependency>
    <!-- 数据库驱动 -->
    <dependency> 
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId> 
    </dependency>
  </dependencies>
  <!-- bdf3项目jar存放的maven私服 -->
  <repositories>
    <repository>
      <id>bsdn-maven-repository</id>
      <url>http://nexus.bsdn.org/content/groups/public/</url>
    </repository>
  </repositories>
</project>
```
2. 启动类
```java
package com.bstek.bdf3.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年12月10日
 */
@SpringBootApplication
@EnableCaching
public class SampleApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleApplication.class, args);
	}
}

```
>通过以上两个步骤，一个基本的bdf3项目就搭建好了。直接运行项目的主类（带main函数的类）[示例下载](http://onipkjzjl.bkt.clouddn.com/bdf3-sample.zip)

## 配置文件说明

1.application.properties

```
#服务器端口设置
server.port = 8080
#项目路径
server.context-path=/bdf
#是否打印sql语句
spring.jpa.showSql=true
#hibernate反向创建表设置，update启动时更新表结构，create 启动时重新创建表结构，none 启动时不检查
spring.jpa.hibernate.ddl-auto=update
#springboot热部署设置，添加文件改动不重启目录。
spring.devtools.restart.additional-exclude=com/**


#数据源配置，pom中需要引入对应的数据库jdbc依赖
spring.datasource.continue-on-error=true
spring.datasource.url=jdbc:mysql://localhost:3306/bdf3
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
#如果数据库为非嵌入式数据库，这个属性第一次启动的时候一定要设置为ALWAYS，用于初始化数据，初始化好后，可以关闭，也可以不关闭，有自己决定
spring.datasource.initialization-mode=ALWAYS
```

## BDF3文档教程
[BDF3文档教程](https://github.com/muxiangqiu/bdf3/wiki/01.bdf3-jpa)

## Spring-Boot文档教程

[spring-boot文档教程](https://projects.spring.io/spring-boot/#quick-start)

## 界面截图

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/bdf3.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/2.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/3.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/4.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/5.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/6.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/7.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/8.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/9.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/10.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/11.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/12.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/13.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/14.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/15.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/16.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/17.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/18.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/19.png)

![](https://raw.githubusercontent.com/muxiangqiu/bdf3/master/screenshot/20.png)


