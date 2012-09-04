package com.metacube.ipathshala.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.metacube.ipathshala.FilterInfo;
import com.metacube.ipathshala.GridRequest;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.entity.ConnectContact;
import com.metacube.ipathshala.entity.Contact;
import com.metacube.ipathshala.entity.Workflow;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.service.ConnectContactService;
import com.metacube.ipathshala.service.KeyListService;
import com.metacube.ipathshala.service.WorkflowService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.EntityUtil;
import com.metacube.ipathshala.workflow.WorkflowInfo;
import com.metacube.ipathshala.workflow.impl.context.ConnectContactContext;
import com.metacube.ipathshala.workflow.impl.processor.WorkflowStatusType;

@Component
public class ConnectContactManager extends AbstractManager implements
		EntityManager {

	private static final AppLogger log = AppLogger
			.getLogger(ConnectContactManager.class);

	@Autowired
	ConnectContactService service;
	
	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private SearchManager searchManager;
	
	@Autowired private KeyListService keyListService;

	@Override
	public EntityMetaData getEntityMetaData() {
		// TODO Auto-generated method stub
		return service.getEntityMetaData();
	}

	@Override
	public SearchResult doSearch(FilterInfo filterInfo, DataContext dataContext)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getById(Key key) throws AppException {
		// TODO Auto-generated method stub
		return service.getByKey(key);
	}

	@Override
	public Object restoreObject(Object object) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public ConnectContact create(ConnectContact object) {
		return service.create(object);
	}

	public void remove(Key key) {
		service.remove(key);

	}

	public List<ConnectContact> getByUrl(String url) {
		return service.getByUrl(url);
	}

	public void createAndExecuteConnectContactWorkflow(String keyList,String toName , String toEmail) throws AppException {
		ConnectContactContext connectContactContext = new ConnectContactContext();
		connectContactContext.setContactKeysCSV(keyList);
		connectContactContext.setOwnerEmail(UserServiceFactory.getUserService()
				.getCurrentUser().getEmail());
		connectContactContext.setToEmail(toEmail);
		connectContactContext.setToName(toName);
		WorkflowInfo info = new WorkflowInfo("connectContactWorkflowProcessor");
		info.setIsNewWorkflow(true);
		connectContactContext.setWorkflowInfo(info);
		Workflow workflow = new Workflow();
		workflow.setWorkflowName("Connect Contacts");
		workflow.setWorkflowInstanceId(info.getWorkflowInstance());
		workflow.setContext(connectContactContext);
		workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
		workflow = workflowService.createWorkflow(workflow);
		System.out.println(workflow.getWorkflowInstanceId());
		if (workflow != null) {
			workflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS.toString());
			workflowService.updateWorkflow(workflow);
			workflowService.triggerWorkflow(workflow);
		}
	}

	public SearchResult doSearch(GridRequest gridRequest) throws AppException {
		SearchResult result = searchManager.doSearch(ConnectContact.class, service.getEntityMetaData(), gridRequest, null);
		List<Object> objects = result.getResultObjects();
		if(objects!=null && !objects.isEmpty()){
			List<ConnectContact>	connectContacts = EntityUtil.getEntityList(objects);
			List<Key> keys = new ArrayList<Key>();
			for(ConnectContact c : connectContacts){
				keys.add(c.getContactKey());
			}
			Collection<Contact> contacts = keyListService.getByKeys(keys, Contact.class);
			List<Object> contactObjectList = EntityUtil.getObjectList(contacts);
			result.setResultObjects(contactObjectList);
		
		}
		
		
		return result;
	}

	public String getDomainName(String randomUrl) throws AppException {
		// TODO Auto-generated method stub
		return service.getDomainName( randomUrl);
	}
}
