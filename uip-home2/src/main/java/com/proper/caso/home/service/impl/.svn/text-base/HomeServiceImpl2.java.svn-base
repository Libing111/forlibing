/* <p>文件名称: HomeServiceImpl2.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-20</p>
 * <p>完成日期：2014-11-20</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-20下午2:59:41
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.caso.home.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.proper.caso.home.application.model.MenuNode;
import com.proper.caso.home.service.HomeService;
import com.proper.uip.api.desktop.entity.ApplicationAndGroup;
import com.proper.uip.api.desktop.entity.QuickApplicationEntity;
import com.proper.uip.api.desktop.wrapper.ModuleList;
import com.proper.uip.api.desktop.wrapper.QuickModuleList;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.wrapper.ResourceList;
import com.proper.uip.security.dao.ResourcesDao;

@Service("HomeServiceImpl-REST")
@Transactional(rollbackFor = RuntimeException.class)
public class HomeServiceImpl2 {
	@Autowired(required=false)
	private RestTemplate restTemplate;
	
	@Autowired
	@Qualifier("systemCategory")
	private String systemCategory;
	
	@Autowired
	private ResourcesDao resourcesDao;


	public List<MenuNode> getTopMenus(HttpServletRequest request) throws UnsupportedEncodingException {
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append("/desktop/rest/pagings?systemCategory=")
		         .append(systemCategory);
		
		String serviceUrl = this.getCasProxyUrl(request, targetUrl.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		ResponseEntity<ModuleList> response = restTemplate.exchange(serviceUrl, HttpMethod.GET, httpEntity, ModuleList.class);
		
		List<ApplicationAndGroup> pagingList = response.getBody().getModuleList();
		
		List<MenuNode> menuNodeList = new ArrayList<MenuNode>();
		
		MenuNode menuNode = null;
		for(ApplicationAndGroup entity : pagingList){
			menuNode = new MenuNode(entity, "./subpage/index?menuId=" + entity.getId());
			
			menuNodeList.add(menuNode);
		}
				
		return menuNodeList;
	}


	public List<MenuNode> getModules(HttpServletRequest request, String pagingId) throws UnsupportedEncodingException {
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append("/desktop/rest/modules?systemCategory=")
		         .append(systemCategory)
		         .append("&pagingId=")
		         .append(pagingId);
		
		String serviceUrl = this.getCasProxyUrl(request, targetUrl.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		ResponseEntity<ModuleList> response = restTemplate.exchange(serviceUrl, HttpMethod.GET, httpEntity, ModuleList.class, pagingId);
		List<ApplicationAndGroup> moduleList = response.getBody().getModuleList();
		
		List<MenuNode> menuNodeList = new ArrayList<MenuNode>();
		if(moduleList == null || moduleList.isEmpty()){
			return menuNodeList;
		}
		
		List<String> resourceIds = new ArrayList<String>();
		for (ApplicationAndGroup applicationAndGroup : moduleList) {
			resourceIds.add(applicationAndGroup.getResourceId());
		}
		Criteria criteria = this.resourcesDao.getSession().createCriteria(Resource.class);
		criteria.add(Restrictions.in("id", resourceIds));

		@SuppressWarnings("unchecked")
		List<Resource> resourceList = criteria.list();
		
		Map<String,String> urlMap = new HashMap<String, String>();
		for (Resource resource : resourceList) {
			urlMap.put(resource.getId(), resource.getUrl());
		}
		
		StringBuffer urlBuffer = new StringBuffer();
		urlBuffer.append(request.getContextPath())
		   		 .append("/")
		   		 .append("proxyUrl?resourceId=");
		
		MenuNode menuNode = null;
		String url = null;
		for(ApplicationAndGroup entity : moduleList){
			url = urlBuffer.toString() + entity.getResourceId();
			menuNode = new MenuNode(entity, url);
			
			if(urlMap.containsKey(entity.getResourceId()) == true){
				if(urlMap.get(entity.getResourceId()).indexOf("ptype=form") >=0){
					menuNode.setPtype("form");
				}
			}
			menuNodeList.add(menuNode);
		}
				
		return menuNodeList;
	}
	

	public List<QuickApplicationEntity> getQuickModules(
			HttpServletRequest request) throws UnsupportedEncodingException {
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append("/desktop/rest/quickModules?systemCategory=")
		         .append(systemCategory);
		
		String serviceUrl = this.getCasProxyUrl(request, targetUrl.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		ResponseEntity<QuickModuleList> response = restTemplate.exchange(serviceUrl, HttpMethod.GET, httpEntity, QuickModuleList.class);
		List<QuickApplicationEntity> moduleList = response.getBody().getModuleList();
		
		return moduleList;
	}
	

	public QuickApplicationEntity getQuickModuleById(
			HttpServletRequest request, String quickModuleId)
			throws UnsupportedEncodingException {
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append("/desktop/rest/quickModule/{id}?systemCategory=")
		         .append(systemCategory);
		
		String serviceUrl = this.getCasProxyUrl(request, targetUrl.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		ResponseEntity<QuickApplicationEntity> response = restTemplate.exchange(serviceUrl, HttpMethod.GET, httpEntity, QuickApplicationEntity.class, quickModuleId);
		QuickApplicationEntity module = response.getBody();
		
		return module;
	}
	

	public QuickApplicationEntity getQuickModuleByResourceId(
			HttpServletRequest request, String resourceId)
			throws UnsupportedEncodingException {
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append("/desktop/rest/quickModule/{resourceId}?systemCategory=")
		         .append(systemCategory);
		
		String serviceUrl = this.getCasProxyUrl(request, targetUrl.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		ResponseEntity<QuickApplicationEntity> response = restTemplate.exchange(serviceUrl, HttpMethod.GET, httpEntity, QuickApplicationEntity.class, resourceId);
		QuickApplicationEntity module = response.getBody();
		
		return module;
	}

	public List<Resource> getQuickModuleCandidates(HttpServletRequest request) throws UnsupportedEncodingException {
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append("/desktop/rest/quickModuleCandidates?systemCategory=")
		         .append(systemCategory);
		String serviceUrl = this.getCasProxyUrl(request, targetUrl.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		ResponseEntity<ResourceList> response = restTemplate.exchange(serviceUrl, HttpMethod.GET, httpEntity, ResourceList.class);
		List<Resource> moduleList = response.getBody().getResourceList();
		
		return moduleList;
	}
	

	public QuickApplicationEntity addQuickModule(HttpServletRequest request, QuickApplicationEntity quickModule) throws UnsupportedEncodingException {
		
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append("/desktop/rest/quickModule?systemCategory=")
		         .append(systemCategory);
		String serviceUrl = this.getCasProxyUrl(request, targetUrl.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<QuickApplicationEntity> httpEntity = new HttpEntity<QuickApplicationEntity>(quickModule);
		ResponseEntity<QuickApplicationEntity> response = restTemplate.postForEntity(serviceUrl, httpEntity, QuickApplicationEntity.class);
		QuickApplicationEntity module = response.getBody();
		
		return module;
	}


	public void deleteQuickModule(HttpServletRequest request,
			String quickModuleId) throws UnsupportedEncodingException {
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append("/desktop/rest/quickModule/").append(quickModuleId).append("?systemCategory=")
		         .append(systemCategory).append("&_method=delete");
		String serviceUrl = this.getCasProxyUrl(request, targetUrl.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(quickModuleId);
		restTemplate.postForEntity(serviceUrl, httpEntity, QuickApplicationEntity.class);
		
		//restTemplate.delete(serviceUrl, quickModuleId);
	}

	
	private String getCasProxyUrl(HttpServletRequest request, String targetUrl) throws UnsupportedEncodingException{
		if(targetUrl == null || targetUrl.trim().equals("") == true){
			return "";
		}
		
		if(targetUrl.startsWith("http") == false){
			targetUrl = request.getRequestURL().toString().replaceAll("/home[0-9]*/.*", targetUrl);
		}
		final CasAuthenticationToken token = (CasAuthenticationToken) request.getUserPrincipal();
		System.out.println("token.Credentials=" + token.getCredentials());
		System.out.println("token.Credentials=" + ((org.springframework.security.web.authentication.WebAuthenticationDetails)token.getDetails()).getSessionId());
		// proxyTicket could be reused to make calls to the CAS service even if the
		// target url differs
		final String proxyTicket = token.getAssertion().getPrincipal().getProxyTicketFor(targetUrl);
		// Make a remote call using the proxy ticket
		
		String serviceUrl = targetUrl+"?ticket=" + URLEncoder.encode(proxyTicket, "UTF-8");
		if(targetUrl.contains("?") == true){
			serviceUrl = targetUrl+"&ticket=" + URLEncoder.encode(proxyTicket, "UTF-8");
		}
		
		return serviceUrl;
	}
	
}

