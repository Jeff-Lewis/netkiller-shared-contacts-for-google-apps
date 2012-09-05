package com.netkiller.dao;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.entity.AppUserEntity;

;

@Component
public class AppUserDao extends AbstractDao<AppUserEntity> {

	@Autowired
	public AppUserDao(PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	public AppUserEntity createAppUser(AppUserEntity appUser) {
		return super.create(appUser);

	}

	public AppUserEntity getAppUserByEmail(String email) {
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm.newQuery(AppUserEntity.class, "email == email1");
			query.declareParameters("String email1");
			Collection<AppUserEntity> users =(Collection<AppUserEntity>) query.execute(email);
			AppUserEntity appUser = null;
			if(users!=null && users.size()>0){
				List<AppUserEntity> appUsers = (List<AppUserEntity>)pm.detachCopyAll(users);
				appUser = appUsers.get(0);
			}
			return appUser;
		} finally {
			releasePersistenceManager(pm);
		}
	}
}
