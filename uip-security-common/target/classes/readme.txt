�汾����˵��

v1.0.0
һ����ȫ������Spring Beans�����ļ�
   	1��λ�ã�applicationContext-security.xml
   	2��ҵ�񹤳�Ŀ¼λ�ã���bpm����Ϊ������������Ŀ¼/proper-caso-bpm/src/main/resources/security/applicationContext-security.xml
   	3��ҵ�񹤳�Spring Servlet�����ļ���applicationContext.xml�������빫����ȫBeans�����ļ�
      	<!-- ���빫����ȫ�����ļ� -->
      	<import resource="security/applicationContext-security.xml" />
      
      
����Spring Security CAS�ͻ��������ļ�applicationContext-security-cas.xml
   	1��λ�ã�applicationContext-security-cas.xml
   	2��ҵ�񹤳�Ŀ¼λ�ã���bpm����Ϊ������������Ŀ¼/proper-caso-bpm/src/main/resources/applicationContext-security-cas.xml
   	3��ҵ�񹤳�web.xml������Spring Security CAS�����ļ�
    	<context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>
           classpath:applicationContext.xml,
           classpath:applicationContext-service.xml,
           classpath:applicationContext-security-cas.xml
        </param-value>
    	</context-param>

����������ȫ��Ҫ����ӿ�����
    1��ע���������ɾ�Ĳ飩
       �ӿڣ�com.proper.platform.security.service.RegistrationAuthorityService
       
    2����Դ����ɾ�Ĳ飩
       �ӿڣ�com.proper.platform.security.service.ResourceService
       
    3���û�����ɾ�Ĳ飩
       �ӿڣ�com.proper.platform.security.service.UserService
       
    4����ȫ�����ж�Ȩ�ޡ���ȡ��ǰ��¼�û���
       �ӿڣ�com.proper.platform.security.service.SecurityService
       