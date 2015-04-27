package com.proper.caso.home.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.caso.home.service.AccountService;
import com.proper.uip.api.security.entity.User;
import com.proper.uip.security.dao.UsersDao;
import com.proper.uip.security.utils.PasswordEncodingUtil;


@Service
@Transactional(rollbackFor = RuntimeException.class)
public class AccountServiceImpl implements AccountService {
	@Autowired
	private UsersDao usersDao;
	
	@Override
	public void save( User currentUser, User userEntity) {
		User originUser = currentUser;
		if(userEntity.getId().equals(currentUser.getId()) == false){
			originUser = usersDao.findUniqueBy("id", userEntity.getId());
		}
		if(originUser == null){
			return;
		}
		
		originUser.setAccount(userEntity.getAccount());
		originUser.setName(userEntity.getName());
		usersDao.save(originUser);
	}

	@Override
	public void savePassWord(User currentUser, User userEntity) {
		User originUser = currentUser;
		if(userEntity.getId().equals(currentUser.getId()) == false){
			originUser = usersDao.findUniqueBy("id", userEntity.getId());
		}
		if(originUser == null){
			return;
		}
		
		PasswordEncodingUtil passwordEncodingUtil = new PasswordEncodingUtil(PasswordEncodingUtil.ENCODING_ALGORITHM_MD5);
		String newPassword = passwordEncodingUtil.encode(userEntity.getPassword());
		originUser.setPassword(newPassword);
		originUser.putExtendProperty("reset", "false");
		usersDao.save(originUser);
	}
	

}
