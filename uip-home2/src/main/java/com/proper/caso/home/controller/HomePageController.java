/* <p>文件名称: HomePageController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-11-4</p>
 * <p>完成日期：2014-11-4</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-11-4下午3:29:06
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.caso.home.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proper.caso.home.application.model.MenuNode;
import com.proper.caso.home.service.HomeService;
import com.proper.hr.personnel.entity.Person;
import com.proper.hr.personnel.entity.UserPositionInfo;
import com.proper.hr.personnel.service.IPersonService;
import com.proper.hr.personnel.service.IUserPositionService;
import com.proper.uip.api.desktop.entity.HomeMessageEntity;
import com.proper.uip.api.desktop.entity.MessageReportEntity;
import com.proper.uip.api.desktop.entity.QuickApplicationEntity;
import com.proper.uip.api.desktop.extension.HomeMessageExtension;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.ResourceService;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/home")
public class HomePageController extends BaseController{
	@Autowired
	@Qualifier("HomeServiceImpl-API")
	private HomeService homeService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IUserPositionService userPositionService;
	
	
	@Autowired
	List<HomeMessageExtension> homeMessageExtensionList;
	
	@Autowired
	@Qualifier("personalIssue")
	private String personalIssue;
	
	/**
	 * 首页
	 * 
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/index")
	public String home(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		//公文list
		List<HomeMessageEntity> documentmanagementList = new ArrayList<HomeMessageEntity>();
		//代办工作list
		List<HomeMessageEntity> bpmList = new ArrayList<HomeMessageEntity>();
		//公告通知list
		List<HomeMessageEntity> notifiList = new ArrayList<HomeMessageEntity>();
		//内部邮件
		List<HomeMessageEntity> mailList = new ArrayList<HomeMessageEntity>();
		
		List<HomeMessageEntity> todayList = new ArrayList<HomeMessageEntity>();
		
		
		//首页我的事务快捷菜单
		List<MenuNode> menuNodeList = homeService.getPersonalIssueModules(request, personalIssue);
		
		User user = this.getCurrentUser();
		String extendId = user.getExtendId();
		Person person = personService.getByPersonId(extendId);
		if(person != null){
			List<UserPositionInfo> userPositionInfoList = userPositionService.getUserPositionInfoByPersonId(user,person.getPersonId());
			modelMap.put("userPositionInfo", userPositionInfoList.get(0));
			
		}
		if(person == null){

			modelMap.put("notifiList", notifiList);
			modelMap.put("menuNodeList", menuNodeList);
			modelMap.put("bpmList", bpmList);
			modelMap.put("documentmanagementList", documentmanagementList);
			modelMap.put("mailList", mailList);
			modelMap.put("person", person);
			
			modelMap.put("user", user);
			return "home4";
		}
		//查询公文通知
		String messageCode ="home.message.documentmanagement";
		HomeMessageExtension  messageExtension = null;
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getMessageCode().equals(messageCode) == true){
				messageExtension = extension;
				break;
			}
		}
		if(messageExtension != null){
			documentmanagementList = messageExtension.getMessageList(request,user, messageExtension.getDefaultCount());
		}
		//代办工作
		messageCode ="home.message.bpm";
		HomeMessageExtension  messageBpmExtension = null;
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getMessageCode().equals(messageCode) == true){
				messageBpmExtension = extension;
				break;
			}
		}
		
		bpmList = messageBpmExtension.getMessageList(request,user, messageBpmExtension.getDefaultCount());
		
		//公告通知
		messageCode ="home.message.notification";
		HomeMessageExtension  messageNotifiExtension = null;
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getMessageCode().equals(messageCode) == true){
				messageNotifiExtension = extension;
				break;
			}
		}
		try{
		notifiList = messageNotifiExtension.getMessageList(request,user, messageNotifiExtension.getDefaultCount());
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
		//内部邮件
		messageCode ="home.message.mail";
		HomeMessageExtension  messageMailExtension = null;
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getMessageCode().equals(messageCode) == true){
				messageMailExtension = extension;
				break;
			}
		}
		
		mailList = messageMailExtension.getMessageList(request,user, messageMailExtension.getDefaultCount());

		//获取今日提醒
		todayList = homeService.getTodayMaessageList(user);
		
		
		modelMap.put("notifiList", notifiList);
		modelMap.put("menuNodeList", menuNodeList);
		modelMap.put("bpmList", bpmList);
		modelMap.put("documentmanagementList", documentmanagementList);
		modelMap.put("mailList", mailList);
		modelMap.put("todayList", todayList);
		
		modelMap.put("person", person);
		
		modelMap.put("user", user);
		return "home4";
	}
	
	
	@RequestMapping(value = "/quickMenu/getList")
	@ResponseBody
	public List<QuickApplicationEntity> quickMenus(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		List<QuickApplicationEntity> menuNodeList = new ArrayList<QuickApplicationEntity>();
		
		User user = super.getCurrentUser();
		if(user == null){
			return menuNodeList;
		}
		
		menuNodeList = homeService.getQuickModules(request);
		
        return menuNodeList;
	}
	
	@RequestMapping(value = "/quickMenu/add/index")
	public String addIndex(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		List<Resource> resourceList = homeService.getQuickModuleCandidates(request);
	
		modelMap.put("resourceList", resourceList);
		return "quickMenu/add";
	}
	
	@RequestMapping(value = "/quickMenu/add")
	@ResponseBody
	public QuickApplicationEntity add(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		User user = super.getCurrentUser();
		String resourceId = request.getParameter("resourceId");
		Resource resource = resourceService.getResourceById(resourceId);
		QuickApplicationEntity quickApplicationEntity = new QuickApplicationEntity();
		quickApplicationEntity.setId(null);
		quickApplicationEntity.setResourceId(resourceId);
		quickApplicationEntity.setTitle(resource.getName());
		quickApplicationEntity.setIcon("demo");
		quickApplicationEntity.setUrl(resource.getUrl());
		quickApplicationEntity.setUserId(user.getId());
		quickApplicationEntity.setCloseable(true);
		
		QuickApplicationEntity quickApplication = homeService.addQuickModule(request, quickApplicationEntity);
		
		return quickApplication;
	}
	
	@RequestMapping(value = "/quickMenu/getByResourceId")
	@ResponseBody
	public QuickApplicationEntity getByResourceId(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		User user = super.getCurrentUser();
		if(user == null){
			throw new IllegalArgumentException();
		}
		
		String resourceId = request.getParameter("resourceId");
		if(resourceId == null || resourceId.trim().equals("")){
			throw new IllegalArgumentException();
		}
		QuickApplicationEntity quickApplication = homeService.getQuickModuleByResourceId(request, resourceId);
		if(quickApplication == null){
			throw new IllegalArgumentException();
		}
		
        return quickApplication;
	}
	
	@RequestMapping(value = "/quickMenu/delete")
	@ResponseBody
	public void deleteModel(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		String modelId = request.getParameter("id");
		if(modelId == null){
			throw new IllegalArgumentException();
		}
		
		User user = super.getCurrentUser();
		if(user == null){
			throw new IllegalArgumentException();
		}
		
		homeService.deleteQuickModule(request, modelId);
	}
	
	/**
	 * 获取首页显示数据
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/messageModuleList")
	@ResponseBody
	public List<HomeMessageExtension> getMessageModuleList(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		Collections.sort(homeMessageExtensionList);
		
		return homeMessageExtensionList;
	}
	
	/**
	 * 获取首页显示数据
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/messageList")
	@ResponseBody
	public List<HomeMessageEntity> getMessageList(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		List<HomeMessageEntity> msgList = new ArrayList<HomeMessageEntity>();
		String messageCode = request.getParameter("messageCode");
		if(messageCode == null){
			return msgList;
		}
		
		HomeMessageExtension  messageExtension = null;
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode().equals(messageCode) == true){
				messageExtension = extension;
				break;
			}
		}
		
		if(messageExtension == null){
			return msgList;
		}
		
		User user = super.getCurrentUser();
		
		msgList = messageExtension.getMessageList(request,user, messageExtension.getDefaultCount());
		
        return msgList;
	}
	
	
	/**
	 * 获取首页公告数据显示数据
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/messageReportList")
	@ResponseBody
	public List<MessageReportEntity> messageReportList(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		User user = this.getCurrentUser();
		String extendId = user.getExtendId();
		Person person = personService.getByPersonId(extendId);
		List<MessageReportEntity>  messageReportEntityList = new ArrayList<MessageReportEntity>();
		if(person == null){
			return messageReportEntityList;
		}
		
		String messageCode = "home.message.notification";
		HomeMessageExtension  messageExtension = null;
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getMessageCode().equals(messageCode)){
				messageExtension = extension;
				break;
			}
		}
		if(messageExtension == null){
			return messageReportEntityList;
		}
		
		messageReportEntityList = messageExtension.getMessageReportList(request, user);
	
        return messageReportEntityList;
	}
	
	/**
	 * 获取首页公告数据显示数据
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/messageBpmList")
	@ResponseBody
	public List<Integer> messageBpmList(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		User user = this.getCurrentUser();
		String extendId = user.getExtendId();
		Person person = personService.getByPersonId(extendId);
		List<Integer> bpmdateList = new ArrayList<Integer>();
		if(person == null){
			return bpmdateList;
		}
		
		
		String messageCode ="home.message.bpm";
		HomeMessageExtension  messageBpmExtension = null;
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getMessageCode().equals(messageCode) == true){
				messageBpmExtension = extension;
				break;
			}
		}
		
		Calendar onedateStart = new GregorianCalendar(); 
		onedateStart.add(Calendar.DAY_OF_MONTH, -1);
		  
//		onedateStart.set(Calendar.HOUR_OF_DAY, 0);  
//		onedateStart.set(Calendar.MINUTE, 0);  
//		onedateStart.set(Calendar.SECOND, 0);  
//		Calendar onedateStop = new GregorianCalendar();   
//		  
//		onedateStop.set(Calendar.HOUR_OF_DAY, 23);  
//		onedateStop.set(Calendar.MINUTE, 59);  
//		onedateStop.set(Calendar.SECOND, 59);  
		
		int onedate = messageBpmExtension.getMessageCount(user, (Date)onedateStart.getTime().clone(), null);
		bpmdateList.add(onedate);
		
		Calendar threedateStart = new GregorianCalendar(); 
		threedateStart.add(Calendar.DAY_OF_MONTH, -3);
//		Calendar threedateStart = new GregorianCalendar();   
//		  
//		onedateStart.set(Calendar.HOUR_OF_DAY, 0);  
//		onedateStart.set(Calendar.MINUTE, 0);  
//		onedateStart.set(Calendar.SECOND, 0);  
//		Calendar threedateStop = new GregorianCalendar();   
//		  
//		onedateStop.set(Calendar.HOUR_OF_DAY, 23);  
//		onedateStop.set(Calendar.MINUTE, 59);  
//		onedateStop.set(Calendar.SECOND, 59); 
		
		int threedate = messageBpmExtension.getMessageCount(user, (Date)threedateStart.getTime().clone(), null);
		bpmdateList.add(threedate);
		
		
		Calendar sevenDateStart = new GregorianCalendar();   
		sevenDateStart.add(Calendar.DAY_OF_MONTH, -7);
//		sevenDateStart.setFirstDayOfWeek(Calendar.MONDAY);  
//		          
//		sevenDateStart.set(Calendar.HOUR_OF_DAY, 0);  
//		sevenDateStart.set(Calendar.MINUTE, 0);  
//		sevenDateStart.set(Calendar.SECOND, 0);  
//		sevenDateStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
//		
//		Calendar sevenDateStop = new GregorianCalendar();   
//		sevenDateStop.setFirstDayOfWeek(Calendar.MONDAY);  
//		sevenDateStop.set(Calendar.HOUR_OF_DAY, 23);  
//		sevenDateStop.set(Calendar.MINUTE, 59);  
//		sevenDateStop.set(Calendar.SECOND, 59);  
//		sevenDateStop.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); 
		
		int sevendate = messageBpmExtension.getMessageCount(user, (Date)sevenDateStart.getTime().clone(),null);
		bpmdateList.add(sevendate);
		
		
		Calendar cal = Calendar.getInstance();   
		cal.add(Calendar.DAY_OF_MONTH, -30);
//		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);   
        Date beginTime=cal.getTime(); 
        
		int monthdate = messageBpmExtension.getMessageCount(user, beginTime, null);
		bpmdateList.add(monthdate);
		
		//获取1日内的代办工作
        return bpmdateList;
	}
	/**
	 * 更多
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getList")
	@ResponseBody
	public HomeMessageEntity getList(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		HomeMessageEntity homeMessageEntity = new HomeMessageEntity();
		String resourceCode = request.getParameter("id");
		if(resourceCode == null){
			return homeMessageEntity;
		}
		
		HomeMessageExtension  messageExtension = null;
		for(HomeMessageExtension extension: homeMessageExtensionList){
			if(extension.getMessageCode() == null){
				continue;
			}
			if(extension.getResourceCode().equals(resourceCode) == true){
				messageExtension = extension;
				break;
			}
		}
		
		if(messageExtension == null){
			return homeMessageEntity;
		}
		
		
		resourceCode = messageExtension.getResourceCode();
		
		Resource resource = resourceService.getResourceByCode(resourceCode);
		homeMessageEntity.setId(resource.getId());
		homeMessageEntity.setMsgText(resource.getName());
		homeMessageEntity.setTitle(resource.getName());
		homeMessageEntity.setUrl(resource.getUrl());
		
        return homeMessageEntity;
	}
}

