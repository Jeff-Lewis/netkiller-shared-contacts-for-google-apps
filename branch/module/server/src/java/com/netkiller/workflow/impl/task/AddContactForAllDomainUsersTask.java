package com.netkiller.workflow.impl.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.GroupMembershipInfo;
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
import com.netkiller.core.AppException;
import com.netkiller.entity.Contact;
import com.netkiller.entity.DomainGroup;
import com.netkiller.entity.UserContact;
import com.netkiller.service.ContactsService;
import com.netkiller.service.DomainGroupService;
import com.netkiller.service.UserContactService;
import com.netkiller.util.AppLogger;
import com.netkiller.vo.StaticProperties;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.AddContactForAllDomainUsersContext;

public class AddContactForAllDomainUsersTask extends AbstractWorkflowTask {

	private static final AppLogger log = AppLogger
			.getLogger(AddContactForAllDomainUsersTask.class);

	@Autowired
	private ContactsService service;

	@Autowired
	private UserContactService userContactService;

	@Autowired
	private DomainGroupService domainGroupService;

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		AddContactForAllDomainUsersContext userContext = (AddContactForAllDomainUsersContext) context;
		String domain = userContext.getDomain();
		Contact contacts = userContext.getContactInfo();

		for (String userId : service
				.getAllDomainUsersWithReadAndWritePErmissionIncludingAdmin(domain)) {
			System.out.println("contacts creation  user id  = " + userId);
			ContactEntry newentry = makeContact(contacts);
			String userGroupId = getUserGroupId(userId + "@" + domain, domain); // added
			UserContact userContact = new UserContact();
			userContact.setContactKey(contacts.getKey());
			userContact.setUserEmail(userId+"@"+domain);
			// userContact.setContactId(newentry.getId());
			userContact.setContacts(contacts);
			userContact.setDomainName(domain);
			DomainGroup domainGroup = domainGroupService
					.getDomainGroupByDomainName(domain);
			userContact.setGroupName(domainGroup.getGroupName());

			GroupMembershipInfo userGmInfo = new GroupMembershipInfo(); // added
			userGmInfo.setHref(userGroupId); // added
			newentry.addGroupMembershipInfo(userGmInfo);
			try {
				ContactEntry contactEntry = service.createUserContact(newentry,
						userId + "@" + domain);
				System.out
						.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Contact ID or EditLINK = "
								+ contactEntry.getEditLink().getHref());
				userContact.setContactId(contactEntry.getEditLink().getHref());
				try {
					userContactService.createUserContact(userContact);
				} catch (AppException e1) {
					e1.printStackTrace();
				}
			} catch (AppException e) {
				log.error("Unable to Create user in workflow");
			}
		}

		return userContext;
	}

	private ContactEntry makeContact(Contact contactInfo) {
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

	private String getUserGroupId(String email, String domain) {

		String groupId = null;

		try {
			String sharedContactsGroupName = service.getGroupName(domain);

			groupId = service.getUserContactsGroupId(sharedContactsGroupName,
					email);
			log.info("ContactsGroupName ===> " + sharedContactsGroupName);
			log.info("groupId ===> " + groupId);
			while (groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(sharedContactsGroupName));
				group.setTitle(new PlainTextConstruct(sharedContactsGroupName));
				// sharedContactsService.createGroup(group,
				// getCurrentUser(request).getEmail());
				service.createGroup(group, email);

				groupId = service.getUserContactsGroupId(
						sharedContactsGroupName, email);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return groupId;
	}

}
