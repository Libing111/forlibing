版本发布说明

v1.0.0
一、安全公共包Spring Beans配置文件
   	1、位置：applicationContext-security.xml
   	2、业务工程目录位置（以bpm工程为例）：到工程目录/proper-caso-bpm/src/main/resources/security/applicationContext-security.xml
   	3、业务工程Spring Servlet配置文件（applicationContext.xml）中引入公共安全Beans配置文件
      	<!-- 引入公共安全配置文件 -->
      	<import resource="security/applicationContext-security.xml" />
      
      
二、Spring Security CAS客户端配置文件applicationContext-security-cas.xml
   	1、位置：applicationContext-security-cas.xml
   	2、业务工程目录位置（以bpm工程为例）：到工程目录/proper-caso-bpm/src/main/resources/applicationContext-security-cas.xml
   	3、业务工程web.xml中引入Spring Security CAS配置文件
    	<context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>
           classpath:applicationContext.xml,
           classpath:applicationContext-service.xml,
           classpath:applicationContext-security-cas.xml
        </param-value>
    	</context-param>

三、公共安全主要对外接口描述
    1、注册机构（增删改查）
       接口：com.proper.platform.security.service.RegistrationAuthorityService
       
    2、资源（增删改查）
       接口：com.proper.platform.security.service.ResourceService
       
    3、用户（增删改查）
       接口：com.proper.platform.security.service.UserService
       
    4、安全服务（判断权限、获取当前登录用户）
       接口：com.proper.platform.security.service.SecurityService
       