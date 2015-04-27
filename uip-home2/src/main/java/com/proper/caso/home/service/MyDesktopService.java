package com.proper.caso.home.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.proper.caso.home.application.model.MyDesktopEntity;
import com.proper.uip.api.desktop.entity.ApplicationAndGroup;
import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.api.security.entity.ResourceChildNode;
import com.proper.uip.api.security.entity.User;

public interface MyDesktopService {

	List<MyDesktopEntity> getMyDesktopModules(HttpServletRequest request);

	List<Resource> getMyDesktopModuleCandidates(HttpServletRequest request);

	List<MyDesktopEntity> getMyDesktopApplications(HttpServletRequest request,String userId);

	List<ResourceChildNode> getMyDesktopModuleCandidates(HttpServletRequest request,User user,
			String parentChildCode);

	Map<String, String> clickParentResource(User user, String parentChildCode);


	List<MyDesktopEntity> addMyDesktopModule(List<String> resourceList,
			HttpServletRequest request);

	void deleteQuickModule(HttpServletRequest request, String modelId);

	List<ApplicationAndGroup> getApplicationsByResourceId(Set<String> resourceIdSet);

}
