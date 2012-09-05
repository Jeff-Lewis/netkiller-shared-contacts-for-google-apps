package com.netkiller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.dao.AppUserDao;
import com.netkiller.entity.AppUserEntity;


@Component
public class AppUserEntityService {

	@Autowired
	private AppUserDao appUserDao;

	public AppUserEntity createAppUser(AppUserEntity appUserEntity) {
		return appUserDao.createAppUser(appUserEntity);
	}

	public AppUserEntity getAppUserByEmail(String email) {
		return appUserDao.getAppUserByEmail(email);
	}

}
