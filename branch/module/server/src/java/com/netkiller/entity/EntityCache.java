package com.netkiller.entity;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.Persistent;
import javax.jdo.listener.DeleteCallback;
import javax.jdo.listener.InstanceLifecycleEvent;
import javax.jdo.listener.StoreLifecycleListener;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.DateTime;
import com.netkiller.cache.AppCache;
import com.netkiller.cache.EntityCacheValue;
import com.netkiller.cache.impl.KeyCache;
import com.netkiller.core.AppException;
import com.netkiller.util.KeyUtil;

public abstract class EntityCache implements DeleteCallback,
		StoreLifecycleListener {

	private AppCache entityCache;

	public EntityCache() {
		this.entityCache = getEntityCache();
	}

	protected abstract AppCache getEntityCache();

	protected abstract void setDefaultColumn();

	@Override
	public void preStore(InstanceLifecycleEvent event) {
		setDefaultColumn();
	}

	@Override
	public void jdoPreDelete() {
		KeyCache keyCache = new KeyCache("Key");
		keyCache.initializeCache();
		if (entityCache.isCacheIntialized() && keyCache.isCacheIntialized()) {
			EntityCacheValue keyCacheValue = keyCache.getCachedValue(this
					.getClass().getSimpleName());
			if (keyCacheValue != null) {
				List<Key> keyList = (List<Key>) keyCacheValue
						.getProperty("entityKeyList");
				if (entityCache != null) {
					Key key = null;
					try {
						key = KeyUtil.geyKeyFromObject(this);
					} catch (AppException e) {
						String message = "Key not found";
					}
					if (key != null) {
						keyList.remove(key);
						Map.Entry<String, List<Key>> mapEntry = new AbstractMap.SimpleEntry<String, List<Key>>(
								this.getClass().getSimpleName(), keyList);
						keyCache.addCacheValue(mapEntry);
						if (entityCache != null && entityCache.containsKey(key)) {
							entityCache.remove(key);
						}
					}
				}
			}
		}

	}

	@Override
	public void postStore(InstanceLifecycleEvent event) {
		KeyCache keyCache = new KeyCache("Key");
		keyCache.initializeCache();
		if (keyCache.isCacheIntialized() && entityCache.isCacheIntialized()) {

			EntityCacheValue keyCacheValue = keyCache.getCachedValue(this
					.getClass().getSimpleName());
			if (keyCacheValue != null) {
				List<Key> keyList = (List<Key>) keyCacheValue
						.getProperty("entityKeyList");
				Object object = event.getSource();
				if (entityCache != null && object != null) {
					Key key = null;
					try {
						key = KeyUtil.geyKeyFromObject(object);
						if (key != null && !keyList.contains(key)) {
							keyList.add(key);
							Map.Entry<String, List<Key>> mapEntry = new AbstractMap.SimpleEntry<String, List<Key>>(
									this.getClass().getSimpleName(), keyList);
							keyCache.addCacheValue(mapEntry);
						}
					} catch (AppException e) {
						String message = "Key not found";
					}
					if (key != null) {

						entityCache.addCacheValue(object);

					}
				}
			}
		}
	}

}
