package com.proper.caso.omc.systemPreferences.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.caso.omc.systemPreferences.dao.SystemPreferencesDao;
import com.proper.caso.omc.systemPreferences.entity.SystemPreferences;
import com.proper.caso.omc.systemPreferences.service.SystemPreferencesService;
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SystemPreferencesServiceImpl implements SystemPreferencesService {

	@Autowired
	private SystemPreferencesDao systemPreferencesDao;

	@Override
	public void saveSystemPreferences(
			String systemCategory, String id, String title, String picture,String msgCode) {
			List<SystemPreferences>  systemPreferencesList = new ArrayList<SystemPreferences>();
			SystemPreferences systemPreferences = null;
			for(int i=0;i<3;i++){
				if(title.equals("") == false && i==0){
					systemPreferences = new SystemPreferences();
					systemPreferences.setSystemCategory(systemCategory);
					systemPreferences.setId(null);
					systemPreferences.setKey("desktopTitle");
					systemPreferences.setValue(title);
					systemPreferencesList.add(systemPreferences);
					continue;
				}
				if(picture.equals("") == false && i==1){
					systemPreferences = new SystemPreferences();
					systemPreferences.setSystemCategory(systemCategory);
					systemPreferences.setId(null);
					systemPreferences.setKey("backgroudpicture");
					systemPreferences.setValue(picture);
					systemPreferencesList.add(systemPreferences);
					continue;
				}
				if(msgCode.equals("") == false && i==2){
					systemPreferences = new SystemPreferences();
					systemPreferences.setSystemCategory(systemCategory);
					systemPreferences.setId(null);
					systemPreferences.setKey("resouceCategoryList");
					systemPreferences.setValue(msgCode);
					systemPreferencesList.add(systemPreferences);
					continue;
				}
			}
			List<SystemPreferences>  SystemPreferencesLists = systemPreferencesDao.getAll();
			systemPreferencesDao.delete(SystemPreferencesLists);
			systemPreferencesDao.save(systemPreferencesList);
		}

	@Override
	public List<SystemPreferences> getSystemPreferences(String systemCategory) {
		String hql = "select c from SystemPreferences c where c.systemCategory = ?";
		List<SystemPreferences> systemPreferencesList = systemPreferencesDao.find(hql, systemCategory);
		
		return systemPreferencesList;
	}

	@Override
	public SystemPreferences getRc(String key) {
		SystemPreferences SystemPreferences = systemPreferencesDao.findUniqueBy("key", key);
		return SystemPreferences;
	}

	@Override
	public SystemPreferences getSp(String systemCategory, String key) {
		Criteria criteria = this.systemPreferencesDao.getSession().createCriteria(SystemPreferences.class);
		criteria.add(Restrictions.eq("systemCategory", systemCategory))
		        .add(Restrictions.eq("key", key));
		
		SystemPreferences systemPreferences = (SystemPreferences) criteria.uniqueResult();
		
		return systemPreferences;
	}	

}
