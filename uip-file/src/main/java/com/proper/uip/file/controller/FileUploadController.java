/* <p>文件名称: FileUploadController.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-8-28</p>
 * <p>完成日期：2013-8-28</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-8-28 上午9:09:57
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.file.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.proper.uip.api.file.entity.UploadEntity;
import com.proper.uip.api.file.entity.UploadFileEntity;
import com.proper.uip.api.file.model.FileJson;
import com.proper.uip.api.file.service.FileUploadService;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.api.security.service.SecurityService;
import com.proper.uip.api.security.service.UserService;
import com.proper.uip.common.utils.JsonUtil;
import com.proper.uip.web.BaseController;

@Controller
@RequestMapping(value = "/file")
public class FileUploadController extends BaseController {

	private Log _logger = LogFactory.getLog(this.getClass());

	/** 支持的图片扩展名  */
	private final String[] supportPicExtensions = { "jpg", "jpeg", "gif", "png", "bmp" };

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private UserService userServcie;
	
	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/generateCatalogId")
	@ResponseBody
	public Map<String, String> generateCatalogId(HttpServletRequest request, ModelMap model) {
		String businessInstanceId = request.getParameter("businessInstanceId");

		User currentUser = getCurrentUser(request);
		UploadEntity uploadEntity = fileUploadService.createFileUpload(currentUser, businessInstanceId);

		Map<String, String> catalogMap = new HashMap<String, String>();
		catalogMap.put("catalogId", uploadEntity.getId());

		return catalogMap;
	}

	@RequestMapping(value = "/uploadFile.json")
	@ResponseBody
	public String upLoadFileJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException{
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(request instanceof MultipartHttpServletRequest == false){
			result.put("success", false);
			result.put("msg", "上传失败，请联系管理员");
			
        	return JsonUtil.toJSON(result);
        }
        
        User currentUser = getCurrentUser(request);
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
        
        MultipartFile multipartFile = multipartRequest.getFile("fileData");  
        
        String catalogId = request.getParameter("catalogId");
		String fileKind = request.getParameter("kind");
		String resouceCode = request.getParameter("moduleId"); 
        
		try {
			String defaultRootPath = request.getSession().getServletContext().getRealPath("../file");
			if(defaultRootPath.endsWith(File.separator) == false){
				defaultRootPath = defaultRootPath + File.separator;
			}
			defaultRootPath = defaultRootPath + "upload";
			
			UploadFileEntity fileEntity = fileUploadService.saveFile(currentUser, defaultRootPath, resouceCode, catalogId, fileKind, multipartFile);
			
			result.put("fileId", fileEntity.getId());
		} catch (IllegalStateException e) {
			e.printStackTrace();
			
			result.put("success", false);
			result.put("msg", "上传失败，请联系管理员");
			
			return JsonUtil.toJSON(result);
		} catch (IOException e) {
			e.printStackTrace();
			
			result.put("success", false);
			result.put("msg", "上传失败，请联系管理员");
			
			return JsonUtil.toJSON(result);
		}

        result.put("success", true);
		result.put("msg", null);
        
    	return JsonUtil.toJSON(result); 
	}
	
	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public Map<String, Object> upLoadFile(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		Map<String, Object> result = new HashMap<String, Object>();

		if (request instanceof MultipartHttpServletRequest == false) {
			result.put("success", false);
			result.put("msg", "上传失败，请联系管理员");

			return result;
		}

		User currentUser = this.getCurrentUser(request);

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		MultipartFile multipartFile = multipartRequest.getFile("fileData");

		String catalogId = request.getParameter("catalogId");
		
		String synchronous = request.getParameter("synchronous");
		String businessInstanceId = request.getParameter("businessInstanceId");
		if(synchronous != null && Boolean.parseBoolean(synchronous) == true){
			UploadEntity uploadEntity = fileUploadService.createFileUpload(currentUser, businessInstanceId);
			catalogId = uploadEntity.getId();
		}
		
		String fileKind = request.getParameter("kind");
		String resouceCode = request.getParameter("moduleId");

		try {
			String defaultRootPath = request.getSession().getServletContext().getRealPath("../file");
			if(defaultRootPath.endsWith(File.separator) == false){
				defaultRootPath = defaultRootPath + File.separator;
			}
			defaultRootPath = defaultRootPath + "upload";

			UploadFileEntity fileEntity = fileUploadService.saveFile(currentUser, defaultRootPath, resouceCode,
					catalogId, fileKind, multipartFile);

			result.put("fileId", fileEntity.getId());
		} catch (IllegalStateException e) {
			e.printStackTrace();

			result.put("success", false);
			result.put("msg", "上传失败，请联系管理员");

			return result;
		} catch (IOException e) {
			e.printStackTrace();

			result.put("success", false);
			result.put("msg", "上传失败，请联系管理员");

			return result;
		}

		result.put("success", true);
		result.put("msg", null);

		return result;
	}

	@RequestMapping(value = "/listByKind")
	@ResponseBody
	public Map<String, List<FileJson>> listByKind(HttpServletRequest request, ModelMap model) {
		String catalogId = request.getParameter("catalogId");
		String kind = request.getParameter("kind");

		List<UploadFileEntity> files = fileUploadService.getUploadFiles(catalogId, kind);

		List<FileJson> attachmentList = new ArrayList<FileJson>();

		FileJson fileJson = null;
		for (UploadFileEntity file : files) {
			fileJson = new FileJson(file);
			attachmentList.add(fileJson);
		}

		Map<String, List<FileJson>> attachmentMap = new HashMap<String, List<FileJson>>();
		attachmentMap.put("attachmentList", attachmentList);

		return attachmentMap;
	}

	@RequestMapping(value = "/deleteFile")
	@ResponseBody
	public String deleteFile(HttpServletRequest request, ModelMap model) {
		User currentUser = this.getCurrentUser(request);
		String fileId = request.getParameter("fileId");

		fileUploadService.deleteFile(currentUser, fileId);

		return null;
	}

	@RequestMapping(value = "/download")
	public void download(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		String fileId = request.getParameter("fileId");
		if(fileId == null || fileId.trim().equals("")){
			fileId = request.getParameter("id");
		}

		UploadFileEntity fileEntity = fileUploadService.getUploadFile(fileId);

		if (fileEntity == null) {
			return;
		}

		File file = new File(fileEntity.getTargetFileName());
		if (file.exists() == false) {
			return;
		}

		InputStream inputStream = new FileInputStream(file);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

		OutputStream outputStream = response.getOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

		//这个就就是弹出下载对话框的关键代码
		String fileName = java.net.URLEncoder.encode(fileEntity.getFileName(), "UTF-8");
		
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		if(userAgent.contains("firefox")){
			fileName = new String(fileEntity.getFileName().getBytes("UTF-8"),"ISO-8859-1");
		}
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
		
		//response.setHeader("Content-type", "text/plain; charset=utf-8");

		int bytesRead = 0;
		//这个地方的同上传的一样。我就不多说了，都是用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流
		byte[] buffer = new byte[8192];
		while ((bytesRead = bufferedInputStream.read(buffer, 0, 8192)) != -1) {
			bufferedOutputStream.write(buffer, 0, bytesRead);
		}

		bufferedOutputStream.flush();

		inputStream.close();
		bufferedInputStream.close();

		outputStream.close();
		bufferedOutputStream.close();
	}

	@RequestMapping(value = "/deleteByKind")
	@ResponseBody
	public String deleteByKind(HttpServletRequest request, ModelMap model) {
		User currentUser = getCurrentUser(request);
		String catalogId = request.getParameter("catalogId");
		String kind = request.getParameter("kind");

		fileUploadService.deleteByKind(currentUser, catalogId, kind);

		return null;
	}
	
	private User getCurrentUser(HttpServletRequest request) {
		try{
			User user = securityService.getCurrentUser(request);
			return user;
		}catch(Exception e){
			//e.printStackTrace();
		}
		
		User user = userServcie.getUserByLoginName("admin");
		
		return user;
	}
	

	/**
	 * 根据文件ID，找到服务器图片文件，以字节流的方式输出到html
	 * @param response
	 * @param fileId
	 * @throws IOException
	 */
	@RequestMapping(value = "/draw")
	public void draw(HttpServletResponse response, String fileId) throws IOException {

		UploadFileEntity fileEntity = fileUploadService.getUploadFile(fileId);
		if (fileEntity == null) {
			_logger.error("找不到图片：fileId=" + fileId);
			return;
		}

		File file = new File(fileEntity.getTargetFileName());
		if (!file.exists()) {
			_logger.error("找不到图片：path=" + fileEntity.getTargetFileName());
			return;
		}

		if (!Arrays.asList(supportPicExtensions).contains(fileEntity.getFileType().toLowerCase())) {
			_logger.error("不支持的图片类型：" + fileEntity.getFileType());
			return;
		}

		response.setContentType("image/jpeg");

		InputStream inputStream = new FileInputStream(file);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

		OutputStream outputStream = response.getOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

		int bytesRead = 0;
		//这个地方的同上传的一样。我就不多说了，都是用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流
		byte[] buffer = new byte[8192];
		while ((bytesRead = bufferedInputStream.read(buffer, 0, 8192)) != -1) {
			bufferedOutputStream.write(buffer, 0, bytesRead);
		}

		bufferedOutputStream.flush();

		inputStream.close();
		bufferedInputStream.close();

		outputStream.close();
		bufferedOutputStream.close();

	}

}
