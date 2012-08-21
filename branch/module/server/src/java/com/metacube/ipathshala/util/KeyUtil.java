package com.metacube.ipathshala.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.metacube.ipathshala.core.AppException;

public class KeyUtil {

	public static Key geyKeyFromObject(Object object) throws AppException {
		Key key = null;
		try {
			String id = BeanUtils.getProperty(object, "key");
			if (id != null) {
				key = KeyFactory.createKey(object.getClass().getSimpleName(),
						Long.parseLong(BeanUtils.getProperty(object, "key.id")));
			}
		} catch (NumberFormatException e) {
			throw new AppException("Cannot parse string to Long while creating Key");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return key;
	}

}

