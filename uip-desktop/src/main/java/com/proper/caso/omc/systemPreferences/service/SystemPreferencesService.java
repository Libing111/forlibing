package com.proper.caso.omc.systemPreferences.service;

import java.util.List;

import com.proper.caso.omc.systemPreferences.entity.SystemPreferences;


public interface SystemPreferencesService {

	void saveSystemPreferences(String systemCategory, String id, String title, String picture,String msgCode);

	List<SystemPreferences> getSystemPreferences(String systemCategory);

	SystemPreferences getRc(String resouceCategoryList);

	SystemPreferences getSp(String systemCategory, String key);

}
