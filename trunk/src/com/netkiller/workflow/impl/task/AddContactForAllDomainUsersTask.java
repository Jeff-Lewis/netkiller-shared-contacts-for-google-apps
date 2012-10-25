package com.netkiller.workflow.impl.task;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
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
import com.netkiller.exception.AppException;
import com.netkiller.googleUtil.ContactInfo;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.vo.StaticProperties;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.AddContactForAllDomainUsersContext;

public class AddContactForAllDomainUsersTask extends AbstractWorkflowTask {

	@Autowired
	private SharedContactsService sharedContactsService;

	protected final Logger logger = Logger.getLogger(getClass().getName());

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		AddContactForAllDomainUsersContext userContext = (AddContactForAllDomainUsersContext) context;
		String domain = userContext.getDomain();
		ContactInfo contactInfo = userContext.getContactInfo();

		for (String userId : sharedContactsService
				.getAllDomainUsersWithReadAndWritePErmissionIncludingAdmin(domain)) {
			System.out.println("contacts creation  uiser id  = " + userId);
			ContactEntry newentry = makeContact(contactInfo);
			String email = userId + "@" + domain;
			String userGroupId = getUserGroupId(email, domain); // added
			// String userGroupId =
			// sharedContactsService.getMyContactsGroupId(userId + "@" +
			// domain);
			if (!StringUtils.isBlank(userGroupId)) {
				GroupMembershipInfo userGmInfo = new GroupMembershipInfo(); // added
				userGmInfo.setHref(userGroupId); // added
				newentry.addGroupMembershipInfo(userGmInfo);
				String myContactsGroupId = sharedContactsService
						.getMyContactsGroupId(email);
				GroupMembershipInfo myContactsGmInfo = new GroupMembershipInfo(); // added
				myContactsGmInfo.setHref(myContactsGroupId); // added
				newentry.addGroupMembershipInfo(myContactsGmInfo);
				try {
					sharedContactsService.createUserContact(newentry, email);
				} catch (AppException e) {
					logger.log(Level.SEVERE,
							"Unable to Create user in workflow");
				}
			}
		}

		return userContext;
	}

	private ContactEntry makeContact(ContactInfo contactInfo) {
		String fullname = contactInfo.getFullname();
		String givenname = contactInfo.getGivenname();
		String familyname = contactInfo.getFamilyname();
		String companyname = contactInfo.getCompanyname();
		String companydept = contactInfo.getCompanydept();
		String companytitle = contactInfo.getCompanytitle();
		String workemail = contactInfo.getWorkemail();
		String homeemail = contactInfo.getHomeemail();
		String otheremail = contactInfo.getOtheremail();
		String workphone = contactInfo.getWorkphone();
		String homephone = contactInfo.getHomephone();
		String mobilephone = contactInfo.getMobilephone();
		String workaddress = contactInfo.getWorkaddress();
		String homeaddress = contactInfo.getHomeaddress();
		String otheraddress = contactInfo.getOtheraddress();
		String notes = contactInfo.getNotes();

		logger.info("fullname: " + fullname);
		logger.info("givenname: " + givenname);
		logger.info("familyname: " + familyname);
		logger.info("companyname: " + companyname);
		logger.info("companydept: " + companydept);
		logger.info("companytitle: " + companytitle);
		logger.info("workemail: " + workemail);
		logger.info("homeemail: " + homeemail);
		logger.info("otheremail: " + otheremail);
		logger.info("workphone: " + workphone);
		logger.info("homephone: " + homephone);
		logger.info("mobilephone: " + mobilephone);
		logger.info("workaddress: " + workaddress);
		logger.info("homeaddress: " + homeaddress);
		logger.info("otheraddress: " + otheraddress);
		logger.info("notes: " + notes);

		// String homeRel = "http://schemas.google.com/g/2005#home";
		// String workRel = "http://schemas.google.com/g/2005#work";
		// String otherRel = "http://schemas.google.com/g/2005#other";
		// String mobileRel = "http://schemas.google.com/g/2005#mobile";

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

	private String getUserGroupId(String email, String domain) {

		String groupId = null;

		try {
			String sharedContactsGroupName = sharedContactsService
					.getGroupName(domain);

			groupId = sharedContactsService.getUserContactsGroupId(
					sharedContactsGroupName, email);
			logger.info("sharedContactsGroupName ===> "
					+ sharedContactsGroupName);
			logger.info("groupId ===> " + groupId);
			while (groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(sharedContactsGroupName));
				group.setTitle(new PlainTextConstruct(sharedContactsGroupName));
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					// TODO: handle exception
				}

				// sharedContactsService.createGroup(group,
				// getCurrentUser(request).getEmail());
				sharedContactsService.createGroup(group, email);

				groupId = sharedContactsService.getUserContactsGroupId(
						sharedContactsGroupName, email);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
		return groupId;
	}

}
