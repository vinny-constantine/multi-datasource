# Multi-DataSource项目
* Overview
  * 用于在多数据源之间切换
  * 依赖SpringBoot环境
* Usage
  1. 使用maven依赖该项目
  ```xml
  <dependency>
      <groupId>com.spring</groupId>
      <artifactId>multi-datasource</artifactId>
      <version>1.0.0</version>
  </dependency>
  ```
  2. 在application.properties/yml中配置多个数据源，以及其数据源name
  ```yml
  #datasource-datasource config
  spring.datasource.multi.data-source-properties-list[0].data-source-name=datasource1
  spring.datasource.multi.data-source-properties-list[0].driver-class-name=com.mysql.jdbc.Driver
  spring.datasource.multi.data-source-properties-list[0].url=jdbc:mysql://localhost:3306/demo
  spring.datasource.multi.data-source-properties-list[0].username=xxx
  spring.datasource.multi.data-source-properties-list[0].password=yyy
  spring.datasource.multi.data-source-properties-list[0].initial-size=5
  spring.datasource.multi.data-source-properties-list[0].max-active=10
  spring.datasource.multi.data-source-properties-list[0].min-idle=1
  spring.datasource.multi.data-source-properties-list[1].data-source-name=datasource2
  spring.datasource.multi.data-source-properties-list[1].driver-class-name=com.mysql.jdbc.Driver
  spring.datasource.multi.data-source-properties-list[1].url=jdbc:mysql://127.0.0.1:3306/test
  spring.datasource.multi.data-source-properties-list[1].username=xxx
  spring.datasource.multi.data-source-properties-list[1].password=yyy
  spring.datasource.multi.data-source-properties-list[1].initial-size=5
  spring.datasource.multi.data-source-properties-list[1].max-active=10
  spring.datasource.multi.data-source-properties-list[1].min-idle=1
  ```
  3. 在 Bean **(必须实现了接口)** 上使用 `@SwitchDataSource("datasourceName")` 来切换到已配置的某个数据源
  ```java
  @SwitchDataSource("datasource1")
  public class UserServiceImpl implements UserService { 
  }
  ```