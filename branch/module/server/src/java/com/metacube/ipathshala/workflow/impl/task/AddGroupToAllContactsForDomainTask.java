package com.metacube.ipathshala.workflow.impl.task;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.OrgDepartment;
import com.google.gdata.data.extensions.OrgName;
import com.google.gdata.data.extensions.OrgTitle;
import com.google.gdata.data.extensions.Organization;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.StructuredPostalAddress;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.service.ContactsService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.vo.StaticProperties;
import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowExecutionException;
import com.metacube.ipathshala.workflow.impl.context.AddGroupToAllContactsForDomainContext;

public class AddGroupToAllContactsForDomainTask extends AbstractWorkflowTask {

	private static final AppLogger log = AppLogger
			.getLogger(AddGroupToAllContactsForDomainTask.class);

	@Autowired
	private ContactsService service;

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		AddGroupToAllContactsForDomainContext userContext = (AddGroupToAllContactsForDomainContext) context;

		String domain = userContext.getDomainName();
		String group = userContext.getGroupName();
		String email = userContext.getEmail();

		for (String userId : service.getAllDomainUsersIncludingAdmin(domain)) {
			List<ContactEntry> contactEntries = new ArrayList<ContactEntry>();
			for (ContactEntry entry : makeInitialContacts()) {
				String userGroupId = getUserGroupId(userId + "@" + domain,
						group); // added
				GroupMembershipInfo userGmInfo = new GroupMembershipInfo(); // added
				userGmInfo.setHref(userGroupId); // added
				entry.addGroupMembershipInfo(userGmInfo);
				contactEntries.add(entry);
			}

			try {
				service.multipleCreateUserContacts(contactEntries, email);
			} catch (AppException e) {
				log.error("Error while creating multiple Contact entries");
			}

		}

		return userContext;
	}

	private List<ContactEntry> makeInitialContacts() {
		List<ContactEntry> contactEntries = new ArrayList<ContactEntry>();
		contactEntries.add(makeContact("Netkiller Support", "Netkiller",
				"Support", "Support", "support@netkiller.com",
				"1-424-785-0180",
				"2033 Gateway Place, Ste 500, San Jose, CA 95110"));
		contactEntries.add(makeContact("Netkiller Sales", "Netkiller", "Sales",
				"Sales", "sales@netkiller.com", "1-424-785-0180",
				"2033 Gateway Place, Ste 500, San Jose, CA 95110"));
		contactEntries
				.add(makeContact("Netkiller Korea", "Netkiller", "Korea",
						"Support", "support@netkiller.com", "82-2-2052-0453",
						"16F, Gangnam BLDG.,1321-1 Seocho-dong, Seocho-gu, Seoul 137070, South Korea"));
		return contactEntries;
	}

	public ContactEntry makeContact(String fullname, String givenname,
			String familyname, String companydept, String workemail,
			String workphone, String workaddress) {

		String companyname = "";
		String companytitle = "";
		String homeemail = "";
		String otheremail = "";
		String homephone = "";
		String mobilephone = "";
		String homeaddress = "";
		String otheraddress = "";
		String notes = "";

		ContactEntry contact = new ContactEntry();

		Name name = new Name();
		if (!fullname.equals("")) {
			name.setFullName(new FullName(fullname, null));
		}
		if (!givenname.equals("")) {
			name.setGivenName(new GivenName(givenname, null));
		}
		if (!familyname.equals("")) {
			name.setFamilyName(new FamilyName(familyname, null));
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

		/*
		 * if(!workaddress.equals("")){ ExtendedProperty extProp = new
		 * ExtendedProperty(); extProp.setName("workAddress");
		 * extProp.setValue(workaddress); contact.addExtendedProperty(extProp);
		 * } if(!homeaddress.equals("")){ ExtendedProperty extProp = new
		 * ExtendedProperty(); extProp.setName("homeAddress");
		 * extProp.setValue(homeaddress); contact.addExtendedProperty(extProp);
		 * } if(!otheraddress.equals("")){ ExtendedProperty extProp = new
		 * ExtendedProperty(); extProp.setName("otherAddress");
		 * extProp.setValue(otheraddress); contact.addExtendedProperty(extProp);
		 * }//
		 */

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

	private String getUserGroupId(String email, String groupName) {

		String groupId = null;

		try {
			String sharedContactsGroupName = groupName;

			groupId = service.getUserContactsGroupId(sharedContactsGroupName,
					email);
			log.info("sharedContactsGroupName ===> " + sharedContactsGroupName);
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
