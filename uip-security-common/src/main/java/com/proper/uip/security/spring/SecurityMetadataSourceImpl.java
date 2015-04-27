/* <p>文件名称: SecurityMetadataSourceImpl.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-9</p>
 * <p>完成日期：2013-5-9</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-9 下午1:19:37
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.security.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.security.dao.ResourcesDao;

@Service("mySecurityMetadataSource")
public class SecurityMetadataSourceImpl  implements FilterInvocationSecurityMetadataSource {
	@Autowired(required=true)
    private ResourcesDao resourcesDao;  
    
    public ResourcesDao getResourcesDao() {
		return resourcesDao;
	}

	public void setResourcesDao(ResourcesDao resourcesDao) {
		this.resourcesDao = resourcesDao;
	}
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null; 
    
	//由spring调用  
    public SecurityMetadataSourceImpl() { 
    }   
  
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;  
    }  
  
    public boolean supports(Class<?> clazz) {  
        return true;  
    }  
    
    //返回所请求资源所需要的权限  
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {  
        String requestUrl = ((FilterInvocation) object).getRequestUrl();  
        
        System.out.println("requestUrl is " + requestUrl); 
        
        if(resourceMap == null) {  
            loadResourceDefine();  
        }  
        
        return resourceMap.get(requestUrl);  
    }  
  
    //加载所有资源与权限的关系  
    private void loadResourceDefine() {  
        if(resourceMap == null) {  
            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();  
        }  
        
        List<Resource> resources = resourcesDao.getAll();  
        for (Resource resource : resources) {  
            Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();  
            
            //以权限名封装为Spring的security Object  
            ConfigAttribute configAttribute = new SecurityConfig(resource.getId());  
            configAttributes.add(configAttribute);  
            resourceMap.put(resource.getUrl(), configAttributes);  
        }  
 
    }  
}  