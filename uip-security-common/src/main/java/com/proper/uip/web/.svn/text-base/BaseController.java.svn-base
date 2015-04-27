/* <p>文件名称: BaseController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-6-20</p>
 * <p>完成日期：2013-6-20</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-6-20 上午10:53:39
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.UserService;
import com.proper.uip.common.utils.PageConfig;

public class BaseController {
	
	private Log _logger = LogFactory.getLog(this.getClass());
	
	protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	
	protected final static String SUCCESS = "SYS_SUCCESS";
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	protected User getCurrentUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof org.springframework.security.core.userdetails.User == false){
			return null;
		}
		
		org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) principal;   
		String userName = springUser.getUsername();   
		
		//User user = userService.getUserByLoginName(userName);
		User user = userService.getUserByAccount(userName);
		
		return user;
	}
	
	protected void setError(ModelMap model, Exception e){
		setError(model, e.getMessage());
	}
	
	protected void setError(ModelMap model, String error){
		if(error == null){
			model.put("SYS_ERROR", "提交出错");
		}else{
			model.put("SYS_ERROR", error);
		}
		_logger.error(error);
	}

	protected PageConfig createPageConfig(HttpServletRequest request){
		String pageSizeStr=request.getParameter("rows");	
		String pageNoStr=request.getParameter("page");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		
		if(pageSizeStr == null || pageSizeStr.trim().equals("")){
			pageSizeStr = request.getParameter("pager.pageSize");
		}
		if(pageNoStr == null || pageNoStr.trim().equals("")){
			pageNoStr = request.getParameter("pager.pageNo");
		}
		if(order == null || order.trim().equals("")){
			order = request.getParameter("direction");
		}
		
		int pageSize=Integer.parseInt(pageSizeStr==null?"20":pageSizeStr);
		int pageNo=Integer.parseInt(pageNoStr==null?"1":pageNoStr);
		PageConfig pageConfig = PageConfig.newInstance(pageNo, pageSize);
		
		if(sort == null || sort.trim().equals("") == true){
			return pageConfig;
		}
		
		if(order == null || order.trim().equals("") == true){
			return pageConfig;
		}
		
		pageConfig.setOrderBy(sort);
		pageConfig.setOrder(order);
		
		return pageConfig;
	}
	
	@InitBinder 
	public void initBinder(WebDataBinder binder) { 
		dateFormat.setLenient(true); 
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); 
	}
	
	/**  
     * 异常处理器 
     * @return  
     */  
    @ExceptionHandler({Throwable.class})   
    public void exception(Throwable e) {   
        System.out.println(e.getMessage());   
        e.printStackTrace();  
    }
	
}
