package com.metacube.ipathshala.security.acl;

import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Class to manage ACLs
 * 
 * @author prateek
 * 
 */
@Component
public class AclManager {

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Searches through the configuration to find out the matching Acl and returns that.
	 * @param groupName
	 * @return
	 */
	public Acl getAcl(String groupName) {
		Acl aclForGroup = null;
		Map<String, Acl> aclConfigurations = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
				Acl.class);

		if (aclConfigurations != null && !aclConfigurations.isEmpty()) {
			for (Acl acl : aclConfigurations.values()) {
				if (acl.getGroupName().equalsIgnoreCase(groupName)) {
					aclForGroup = acl;
					break;
				}
			}
		}
		return aclForGroup;
	}

}
