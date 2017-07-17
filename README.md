# bdf3
>bdf3基于spring-boot研发的开发框架。包含用户、角色、菜单、权限（最小粒度为组件）、数据导入、字典、日志、Web版数据库管理器、个人中心和SAAS功能模块。基于bdf3快速开发企业管理系统。

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
    <version>0.0.1-SNAPSHOT</version>
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
>通过以上两个步骤，一个基本的bdf3项目就搭建好了。[示例下载](http://onipkjzjl.bkt.clouddn.com/bdf3-sample.zip)

## 配置文件说明

1.application.properties 系统设置

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

#velocity模版后缀
#spring.velocity.suffix=.html
#velocity模版前缀
#spring.velocity.prefix=templates/

#数据源配置，pom中需要引入对应的数据库jdbc依赖
spring.datasource.continue-on-error=true
spring.datasource.url=jdbc:mysql://localhost:3306/bdf3
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

2.configure.properties 变量设置

```
#应用名称
bdf3.appName=上海锐道信息技术有限公司
#登录界面背景图片
bdf3.loginImageBg=dorado/res/static/images/login-bg.jpg
#首次启动时，用户表为空的情况下是否自动创建一个默认的用户：admin/123456
bdf3.autoCreateIfUserIsEmpty=true
#登陆页面地址
bdf3.loginPath=bdf3.security.ui.view.Login.d
#登陆页面地址
bdf3.logoutPath=logout
#系统需要匿名访问的RL
bdf3.systemAnonymous=/static/**,/**/*.dpkg,/dorado/client/**,/dorado/res/**,/**/*.js, /**/*.css
#用户自定义需要匿名访问的URL
bdf3.customAnonymous=
#欢迎页面标题
bdf3.welcomeTitle=欢迎
#欢迎页面的图标
bdf3.welcomeIcon=fa fa-university blue-text
#欢迎页面的地址
bdf3.welcomePath=
#个人中心页面的地址
bdf3.personCenter=bdf3.security.ui.view.PersonalCenter.d
#重置密码后的密码，当该属性为空时，随机生成六位数密码
bdf3.resetPassword=123456
#组件权限配置扁平化
bdf3.componentPermissionFlat=false
#组件权限支持的组件类型，默认支持所有，多个组件用逗号隔开
bdf3.componentPermissionSupportType=
```

## Spring-Boot文档教程

[spring-boot文档教程](https://projects.spring.io/spring-boot/#quick-start)

## 界面截图

![](http://onipkjzjl.bkt.clouddn.com/login-page.png)

![](http://onipkjzjl.bkt.clouddn.com/main-page.png)

![](http://onipkjzjl.bkt.clouddn.com/search-page.png)

![](http://onipkjzjl.bkt.clouddn.com/portal-page.png)

![](http://onipkjzjl.bkt.clouddn.com/user-page.png)

![](http://onipkjzjl.bkt.clouddn.com/menu-page.png)

![](http://onipkjzjl.bkt.clouddn.com/role-assign-page.png)

![](http://onipkjzjl.bkt.clouddn.com/log-page.png)

![](http://onipkjzjl.bkt.clouddn.com/dictionary-page.png)

![](http://onipkjzjl.bkt.clouddn.com/importer-page.png)

![](http://onipkjzjl.bkt.clouddn.com/database-page.png)

![](http://onipkjzjl.bkt.clouddn.com/personal-center-page.png)

![](http://onipkjzjl.bkt.clouddn.com/login-saas-page.png)

![](http://onipkjzjl.bkt.clouddn.com/register-page.png)

![](http://onipkjzjl.bkt.clouddn.com/company-page.png)


