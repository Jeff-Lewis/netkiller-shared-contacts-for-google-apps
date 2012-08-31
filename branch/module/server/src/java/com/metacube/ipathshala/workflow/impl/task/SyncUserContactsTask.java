package com.metacube.ipathshala.workflow.impl.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.UserDefinedField;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FormattedAddress;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.OrgDepartment;
import com.google.gdata.data.extensions.OrgName;
import com.google.gdata.data.extensions.OrgTitle;
import com.google.gdata.data.extensions.Organization;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.StructuredPostalAddress;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.entity.Contacts;
import com.metacube.ipathshala.entity.UserContact;
import com.metacube.ipathshala.service.ContactsService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.CommonWebUtil;
import com.metacube.ipathshala.vo.StaticProperties;
import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowExecutionException;
import com.metacube.ipathshala.workflow.impl.context.SyncUserContactsContext;

public class SyncUserContactsTask extends AbstractWorkflowTask {

	private final static AppLogger log = AppLogger
			.getLogger(SyncUserContactsTask.class);

	@Autowired
	private ContactsService service;

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		SyncUserContactsContext syncUserContactsContext = (SyncUserContactsContext) context;
		String usermail = syncUserContactsContext.getUserEmail();
		List<ContactEntry> entries = new ArrayList<ContactEntry>();
		List<Key> contactKeyList = new ArrayList<Key>();
		List<Contacts> contactList = new ArrayList<Contacts>();
		List<UserContact> userContactList = service
				.getUserContactForDomain(CommonWebUtil.getDomain(usermail));
		for (UserContact userContact : userContactList) {
			if (userContact.getContactKey() != null) {
				contactKeyList.add(userContact.getContactKey());
			}
		}
		try {
			if (contactKeyList != null && !contactKeyList.isEmpty()) {
				contactList = (List<Contacts>) service
						.getByKeys(contactKeyList);
			}
			for (Contacts contacts : contactList) {
				ContactEntry newEntry = makeContact(contacts);
				entries.add(newEntry);
			}

			/*
			 * entries = service.getContacts(1,
			 * syncUserContactsContext.getTotalLimit(),
			 * syncUserContactsContext.getGroupId(), false, null);
			 */
		} catch (AppException e) {
			log.error("Error while fecthing Contact entries");
		}
		service.syncUserContacts(usermail, entries);
		return context;
	}

	private ContactEntry makeContact(Contacts contactInfo) {
		String fullname = contactInfo.getFullName();
		String lastName = contactInfo.getLastName();
		String companyname = contactInfo.getCmpnyName();
		String companydept = contactInfo.getCmpnyDepartment();
		String companytitle = contactInfo.getCmpnyTitle();
		String workemail = contactInfo.getWorkEmail();
		String homeemail = contactInfo.getHomeEmail();
		String otheremail = contactInfo.getOtherEmail();
		String workphone = contactInfo.getWorkPhone();
		String homephone = contactInfo.getHomePhone();
		String mobilephone = contactInfo.getMobileNumber();
		String workaddress = contactInfo.getWorkAddress();
		String homeaddress = contactInfo.getHomeAddress();
		String otheraddress = contactInfo.getOtherAddress();
		String notes = contactInfo.getNotes();

		ContactEntry contact = new ContactEntry();

		Name name = new Name();
		if (!fullname.equals("")) {
			name.setFullName(new FullName(fullname, null));
		}
		if (!lastName.equals("")) {
			name.setFamilyName(new FamilyName(lastName, null));
		}
		contact.setName(name);

		if (!companyname.equals("") || !companytitle.equals("")) {
			Organization org = new Organization();
			if (!companyname.equals("")) {
				org.setOrgName(new OrgName(companyname));
			}
			if (!companydept.equals("")) {
				org.setOrgDepartment(new OrgDepartment(companydept));
			}
			if (!companytitle.equals("")) {
				org.setOrgTitle(new OrgTitle(companytitle));
			}
			org.setRel(StaticProperties.WORK_REL);
			contact.addOrganization(org);
		}

		if (!workemail.equals("")) {
			Email workEmail = new Email();
			workEmail.setAddress(workemail);
			workEmail.setRel(StaticProperties.WORK_REL);
			contact.addEmailAddress(workEmail);
		}

		if (!homeemail.equals("")) {
			Email homeEmail = new Email();
			homeEmail.setAddress(homeemail);
			homeEmail.setRel(StaticProperties.HOME_REL);
			contact.addEmailAddress(homeEmail);
		}

		if (!otheremail.equals("")) {
			Email otherEmail = new Email();
			otherEmail.setAddress(otheremail);
			otherEmail.setRel(StaticProperties.OTHER_REL);
			contact.addEmailAddress(otherEmail);
		}

		if (!workphone.equals("")) {
			PhoneNumber workPhone = new PhoneNumber();
			workPhone.setPhoneNumber(workphone);
			workPhone.setRel(StaticProperties.WORK_REL);
			contact.addPhoneNumber(workPhone);
		}

		if (!homephone.equals("")) {
			PhoneNumber homePhone = new PhoneNumber();
			homePhone.setPhoneNumber(homephone);
			homePhone.setRel(StaticProperties.HOME_REL);
			contact.addPhoneNumber(homePhone);
		}

		if (!mobilephone.equals("")) {
			PhoneNumber mobilePhone = new PhoneNumber();
			mobilePhone.setPhoneNumber(mobilephone);
			mobilePhone.setRel(StaticProperties.MOBILE_REL);
			contact.addPhoneNumber(mobilePhone);
		}

		if (!workaddress.equals("")) {
			FormattedAddress formattedAddress = new FormattedAddress(
					workaddress);
			StructuredPostalAddress postalAddress = new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.WORK_REL);
			// contact.addExtension(postalAddress);
			contact.addRepeatingExtension(postalAddress);
		}

		if (!homeaddress.equals("")) {
			FormattedAddress formattedAddress = new FormattedAddress(
					homeaddress);
			StructuredPostalAddress postalAddress = new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.HOME_REL);
			// contact.addExtension(postalAddress);
			contact.addRepeatingExtension(postalAddress);
		}

		if (!otheraddress.equals("")) {
			FormattedAddress formattedAddress = new FormattedAddress(
					otheraddress);
			StructuredPostalAddress postalAddress = new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.OTHER_REL);
			// contact.addExtension(postalAddress);
			contact.addRepeatingExtension(postalAddress);
		}

		/*
		 * if(!workaddress.equals("")){ PostalAddress workAddress = new
		 * PostalAddress(); workAddress.setValue(workaddress);
		 * workAddress.setRel(workRel); contact.addPostalAddress(workAddress); }
		 * 
		 * if(!homeaddress.equals("")){ PostalAddress homeAddress = new
		 * PostalAddress(); homeAddress.setValue(homeaddress);
		 * homeAddress.setRel(homeRel); contact.addPostalAddress(homeAddress); }
		 * 
		 * if(!otheraddress.equals("")){ PostalAddress otherAddress = new
		 * PostalAddress(); otherAddress.setValue(otheraddress);
		 * otherAddress.setRel(otherRel);
		 * contact.addPostalAddress(otherAddress); }
		 */

		if (!notes.equals("")) {
			// contact.setContent(new PlainTextConstruct(notes));
			contact.getUserDefinedFields().add(
					new UserDefinedField("Notes", notes));
		}

		return contact;
	}

}
