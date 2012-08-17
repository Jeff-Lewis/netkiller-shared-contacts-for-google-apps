package com.netkiller.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gdata.data.Link;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.UserDefinedField;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.ExtendedProperty;
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
import com.google.gdata.data.extensions.PostalAddress;
import com.google.gdata.data.extensions.StructuredPostalAddress;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.netkiller.search.FilterInfo;
import com.netkiller.search.GridRequest;
import com.netkiller.search.property.InputFilterOperatorType;
import com.netkiller.vo.Customer;
import com.netkiller.vo.StaticProperties;

public class SharedContactsUtil {

	protected final Logger logger = Logger.getLogger(getClass().getName());

	private static SharedContactsUtil instance;

	private SharedContactsUtil() {
	}

	public static SharedContactsUtil getInstance() {
		if (instance == null) {
			instance = new SharedContactsUtil();
		}
		return instance;
	}

	public List<ContactEntry> makeContactEntries(SpreadsheetEntry ssEntry)
			throws Exception {
		List<ContactEntry> contactEntries = null;
		return contactEntries;
	}

	public JSONArray convertCustomersToJsonArrayForList(List<Customer> entries,
			int total, int startIndex, String page, String rows, String sidx,
			String sord, GridRequest gridRequest) throws Exception {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = null;

		Customer entry = null;

		String id = null;
		String domain = null;
		String email = null;
		String totalContacts = null;
		String accountType = null;
		String registeredDate = null;
		String upgradedDate = null;
		String endDate = null;

		for (int i = 0; i < entries.size(); i++) {

			entry = (Customer) entries.get(i);
			id = entry.getId().toString();
			domain = entry.getDomain();
			email = entry.getAdminEmail();
			accountType = entry.getAccountType();
			totalContacts = entry.getTotalContacts().toString();
			if (entry.getRegisteredDate() != null) {
				registeredDate = DateFormat.getDateInstance(DateFormat.SHORT)
						.format(entry.getRegisteredDate());
			} else {
				registeredDate = "0/00/00";
			}

			if (entry.getUpgradedDate() != null) {
				upgradedDate = DateFormat.getDateInstance(DateFormat.SHORT)
						.format(entry.getUpgradedDate());
				Date endDateObject = entry.getUpgradedDate();
				endDateObject.setYear(entry.getUpgradedDate().getYear()+1);
				endDate = DateFormat.getDateInstance(DateFormat.SHORT)
				.format(endDateObject);
				
			} else {
				upgradedDate = "0/00/00";
				endDate = "0/00/00";
			}

			jsonObj = new JSONObject();
			jsonObj.put("id", id);
			jsonObj.put("domain", domain);
			jsonObj.put("email", email);
			jsonObj.put("registeredDate", registeredDate);
			jsonObj.put("upgradedDate", upgradedDate);
			jsonObj.put("endDate", endDate);
			jsonObj.put("accountType", accountType);
			jsonObj.put("totalContacts", totalContacts);
			jsonArray.add(jsonObj);
		}
		List<Map> list = makeListWithMap(jsonArray, sidx); // ÃƒÂ¬Ã‚Â Ã¢â‚¬Â¢ÃƒÂ«Ã‚Â Ã‚Â¬ÃƒÂ­Ã¢â‚¬Â¢Ã‚Â 
															// ÃƒÂ­Ã¢â‚¬Â¢Ã¢â‚¬Å¾ÃƒÂ«Ã¢â‚¬Å“Ã…â€œ(ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ«Ã‚Â¦Ã¢â‚¬Å¾,
															// ÃƒÂ¬Ã‚Â Ã¢â‚¬Å¾ÃƒÂ­Ã¢â€žÂ¢Ã¢â‚¬ï¿½ÃƒÂ«Ã‚Â²Ã‹â€ ÃƒÂ­Ã‹Å“Ã‚Â¸
															// ÃƒÂ«Ã¢â‚¬Å“Ã‚Â±),
															// JSONObject
															// ÃƒÂ¬Ã…â€™Ã¯Â¿Â½ÃƒÂ¬Ã¯Â¿Â½Ã¢â‚¬Å¾
															// ÃƒÂªÃ‚Â°Ã¢â‚¬â€œÃƒÂ«Ã…Â Ã¢â‚¬ï¿½
															// ListÃƒÂ«Ã‚Â¥Ã‚Â¼
															// ÃƒÂ«Ã‚Â§Ã…â€™ÃƒÂ«Ã¢â‚¬Å“Ã‚Â ÃƒÂ«Ã¢â‚¬Â¹Ã‚Â¤.
		jsonArray = sort(list, sidx, sord); // Name
		insertNo(jsonArray);
		return jsonArray;
	}

	public JSONArray convertContactsToJsonArrayForList(
			List<ContactEntry> entries, int total, int startIndex, String page,
			String rows, String sidx, String sord, GridRequest gridRequest)
			throws Exception {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = null;

		ContactEntry entry = null;

		String fullNameStr = null;
		String givenNameStr = null;
		String familyNameStr = null;

		String companyStr = null;
		String workEmailStr = null;
		String workPhoneNumberStr = null;
		String workPostalAddressStr = null;
		String notes = null;

		// String homeRel = "http://schemas.google.com/g/2005#home";
		// String workRel = "http://schemas.google.com/g/2005#work";
		// String otherRel = "http://schemas.google.com/g/2005#other";
		// String mobileRel = "http://schemas.google.com/g/2005#mobile";

		// int tmpCnt = entries.size();
		int tmpCnt = total - startIndex;

		for (int i = 0; i < entries.size(); i++) {

			// if(tmpCnt == 0){ break; }

			entry = (ContactEntry) entries.get(i);
			String id = entry.getId();
			// logger.info("id ==> " + id);

			Link link = entry.getEditLink();
			// logger.info("EditLink Href ==> " + link.getHref());

			fullNameStr = getFullName(entry);
			if (fullNameStr == null) {
				fullNameStr = "";
			}
			;

			givenNameStr = getGivenName(entry);
			if (givenNameStr == null) {
				givenNameStr = "";
			}
			;

			familyNameStr = getFamilyName(entry);
			if (familyNameStr == null) {
				familyNameStr = "";
			}
			;

			companyStr = getOrganization(entry, "work");
			workEmailStr = getEmail(entry, "work");
			workPhoneNumberStr = getPhoneNumber(entry, "work");
			// workPostalAddressStr = getAddress(entry, "work");
			// workPostalAddressStr = getExtendedProperty(entry, "workAddress");
			workPostalAddressStr = this.getFormattedAddress(entry,
					StaticProperties.WORK_REL);

			notes = getNotes(entry);

			String href = link.getHref() + "?dumy=1" + "&page=" + page
					+ "&rows=" + rows + "&sidx=" + sidx + "&sord=" + sord;

			jsonObj = new JSONObject();
			// jsonObj.put("id", link.getHref());
			jsonObj.put("id", href);
			jsonObj.put("editlink", href);
			jsonObj.put("name", fullNameStr);
			jsonObj.put("givenname", givenNameStr);
			jsonObj.put("familyname", familyNameStr);
			jsonObj.put("company", companyStr);
			jsonObj.put("email", workEmailStr);
			jsonObj.put("phone", workPhoneNumberStr);
			jsonObj.put("address", workPostalAddressStr);
			jsonObj.put("notes", notes);

			// jsonObj.put("comm-page", page); //new
			// jsonObj.put("comm-rows", rows); //new
			// jsonObj.put("comm-sidx", sidx); //new
			// jsonObj.put("comm-sord", sord); //new

			// jsonObj.put("id", Integer.toString(tmpCnt--));
			jsonObj.put("delete", "False");
			// jsonObj.put("name", "a");
			// jsonObj.put("company", "b");
			// jsonObj.put("email", "c");
			// jsonObj.put("phone", "111");
			// jsonObj.put("address", "ddd");
			// jsonObj.put("notes", "kkk");
			if (gridRequest != null && gridRequest.isSearch()) {
				if (!isValidSearchObject(jsonObj, gridRequest.getFilterInfo()))
					continue;
			}
			// jsonArray.put(jsonObj);
			jsonArray.add(jsonObj);
			// jsonArray.add(jsonObj);
		}// end for
			// jsonArray = sort(jsonArray, null);

		// String basisToSort = "name";
		// String sord = "desc";

		// logger.info("#CCC-1 ==> jsonArray.size(): " + jsonArray.size());
		if (!sidx.equals("")) {
			List<Map> list = makeListWithMap(jsonArray, sidx); // ÃƒÂ¬Ã‚Â Ã¢â‚¬Â¢ÃƒÂ«Ã‚Â Ã‚Â¬ÃƒÂ­Ã¢â‚¬Â¢Ã‚Â 
																// ÃƒÂ­Ã¢â‚¬Â¢Ã¢â‚¬Å¾ÃƒÂ«Ã¢â‚¬Å“Ã…â€œ(ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ«Ã‚Â¦Ã¢â‚¬Å¾,
																// ÃƒÂ¬Ã‚Â Ã¢â‚¬Å¾ÃƒÂ­Ã¢â€žÂ¢Ã¢â‚¬ï¿½ÃƒÂ«Ã‚Â²Ã‹â€ ÃƒÂ­Ã‹Å“Ã‚Â¸
																// ÃƒÂ«Ã¢â‚¬Å“Ã‚Â±),
																// JSONObject
																// ÃƒÂ¬Ã…â€™Ã¯Â¿Â½ÃƒÂ¬Ã¯Â¿Â½Ã¢â‚¬Å¾
																// ÃƒÂªÃ‚Â°Ã¢â‚¬â€œÃƒÂ«Ã…Â Ã¢â‚¬ï¿½
																// ListÃƒÂ«Ã‚Â¥Ã‚Â¼
																// ÃƒÂ«Ã‚Â§Ã…â€™ÃƒÂ«Ã¢â‚¬Å“Ã‚Â ÃƒÂ«Ã¢â‚¬Â¹Ã‚Â¤.
			System.out.println("sisx and sord are empty");
			System.out.println("sidx" + sidx);
			// logger.info("#CCC-2 ==> list.size(): " + list.size());
			// logger.info("\n\n\n\n");
			jsonArray = sort(list, sidx, sord); // Name
			insertNo(jsonArray);
		}
		// logger.info("#CCC-3 ==> jsonArray.size(): " + jsonArray.size());
		System.out.println("Json"+ jsonArray.toJSONString());
		return jsonArray;
	}

	private boolean isValidSearchObject(JSONObject jsonObj,
			FilterInfo filterInfo) {
		// TODO Auto-generated method stub
		if (filterInfo != null) {
			String property = "";
			for (FilterInfo.Rule rule : filterInfo.getRules()) {
				if(rule.getField().equals("all"))	{
					for(Object value:jsonObj.values())
					property = property+value;
					if (InputFilterOperatorType.CONTAINS.equals(rule.getOp())) {
						if (property.toLowerCase().indexOf(rule.getData().toLowerCase()) >= 0) {
							return true;
						}
					}
				}
				else	{
				 property = (String) jsonObj.get(rule.getField());
				if (InputFilterOperatorType.CONTAINS.equals(rule.getOp())) {
					if (property.toLowerCase().indexOf(rule.getData().toLowerCase()) >= 0) {
						return true;
					}
				}
				if (InputFilterOperatorType.STARTS_WITH.equals(rule.getOp())) {
					if (property.toLowerCase().indexOf(rule.getData().toLowerCase()) == 0) {
						return true;
					}
				}
				if (InputFilterOperatorType.EQUAL.equals(rule.getOp())) {
					if (property.toLowerCase().equals(rule.getData().toLowerCase())) {
						return true;
					}
				}
				}
			}
		}
		return false;
	}

	private JSONArray sort(List<Map> list, String sidx, String sord)
			throws Exception {
		String name = null;
		Map map = null;
		JSONObject jsonObj = null;
		JSONArray sortedJsonObjs = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			map = (Map) list.get(i);
			Set keys = map.keySet();
			Iterator itr = keys.iterator();
			String key = (String) itr.next(); // real name, real email
			// logger.info("##BBB3 key(" + i + "): " + key);
			jsonObj = (JSONObject) map.get(key); // name, email...
			// logger.info("##BBB3 jsonObj(" + i + "): " + jsonObj);
			insert(jsonObj, sidx, sord, sortedJsonObjs);
			// logger.info("\n\n");
		}
		return sortedJsonObjs;
	}

	private void insertNo(JSONArray jsonArray) {
		JSONObject jsonObject = null;
		int size = jsonArray.size();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = (JSONObject) jsonArray.get(i);
			jsonObject.put("no", Integer.toString(size--));
		}
	}

	private void insert(JSONObject jsonObj, String sidx, String sord,
			JSONArray sortedJsonArray) throws Exception {

		JSONObject existingObj1 = null;
		JSONObject existingObj2 = null;
		String valueTmp1 = null;
		String valueTmp2 = null;
		
		int valueNTmp0 = 0;
		int valueNTmp1 = 0;
		int valueNTmp2 = 0;
		
		String value = (String) jsonObj.get(sidx);
		
		boolean isDateField = false;
		boolean isNumberField = false;
		//totalContacts
		if(sidx.equalsIgnoreCase("registeredDate") || sidx.equalsIgnoreCase("upgradedDate") || sidx.equalsIgnoreCase("endDate")){
			isDateField = true;
		}else if(sidx.equalsIgnoreCase("totalContacts")){
			isNumberField = true;
		}
	
		
		if (value == null || value.equals("") || sortedJsonArray.size() == 0) {
			sortedJsonArray.add(jsonObj);
			return;
		}

		if (sortedJsonArray.size() == 1) {

			existingObj1 = (JSONObject) sortedJsonArray.get(0);
			valueTmp1 = (String) existingObj1.get(sidx);
			
			value = value.toLowerCase();
			valueTmp1 = valueTmp1.toLowerCase();

			if (sord.equals("asc")) {
				if(isDateField) {
					if(getDateWithFormat(value).compareTo(getDateWithFormat(valueTmp1))<0){
						sortedJsonArray.add(0, jsonObj);
					}else{
						sortedJsonArray.add(jsonObj);
					}
				}else if(isNumberField) {
					valueNTmp0 = 0;
					valueNTmp1 = 0;
					
					try{ valueNTmp0 = Integer.parseInt(value); }catch(Exception e){}
					try{ valueNTmp1 = Integer.parseInt(valueTmp1); }catch(Exception e){}
					
					if( valueNTmp0 <= valueNTmp1 ){
						sortedJsonArray.add(0, jsonObj);
					}else{
						sortedJsonArray.add(jsonObj);
					}
					
				}else{
					if (value.compareTo(valueTmp1) < 0) {
						sortedJsonArray.add(0, jsonObj);
					} else {
						sortedJsonArray.add(jsonObj);
					}
				}
			} else {
				if(isDateField) {
					if(getDateWithFormat(value).compareTo(getDateWithFormat(valueTmp1))>0){
						sortedJsonArray.add(0, jsonObj);
					}else{
						sortedJsonArray.add(jsonObj);
					}
				}else if(isNumberField) {
					valueNTmp0 = 0;
					valueNTmp1 = 0;
					
					try{ valueNTmp0 = Integer.parseInt(value); }catch(Exception e){}
					try{ valueNTmp1 = Integer.parseInt(valueTmp1); }catch(Exception e){}
					
					if( valueNTmp0 >= valueNTmp1 ){
						sortedJsonArray.add(0, jsonObj);
					}else{
						sortedJsonArray.add(jsonObj);
					}
					
				}else{
					if (value.compareTo(valueTmp1) > 0) {
						sortedJsonArray.add(0, jsonObj);
					} else {
						sortedJsonArray.add(jsonObj);
					}
				}
			}
			return;
		}

		for (int i = 0; i < sortedJsonArray.size(); i++) {

			if ((i + 1) == sortedJsonArray.size()) {

				existingObj1 = (JSONObject) sortedJsonArray.get(i);
				valueTmp1 = (String) existingObj1.get(sidx);

				value = value.toLowerCase();
				valueTmp1 = valueTmp1.toLowerCase();

				if (sord.equals("asc")) {
					if(isDateField) {
						if(getDateWithFormat(value).compareTo(getDateWithFormat(valueTmp1))<0){
							sortedJsonArray.add(i, jsonObj);
						}else{
							sortedJsonArray.add(jsonObj);
						}
					}else if(isNumberField) {
						valueNTmp0 = 0;
						valueNTmp1 = 0;
						
						try{ valueNTmp0 = Integer.parseInt(value); }catch(Exception e){}
						try{ valueNTmp1 = Integer.parseInt(valueTmp1); }catch(Exception e){}
						
						if( valueNTmp0 <= valueNTmp1 ){
							sortedJsonArray.add(i, jsonObj);
						}else{
							sortedJsonArray.add(jsonObj);
						}
						
					}else{
						if (value.compareTo(valueTmp1) < 0) {
							sortedJsonArray.add(i, jsonObj);
						} else {
							sortedJsonArray.add(jsonObj);
						}
					}
				} else {
					if(isDateField) {
						if(getDateWithFormat(value).compareTo(getDateWithFormat(valueTmp1))<0){
							sortedJsonArray.add(i, jsonObj);
						}else{
							sortedJsonArray.add(jsonObj);
						}
					}else if(isNumberField) {
						valueNTmp0 = 0;
						valueNTmp1 = 0;
						
						try{ valueNTmp0 = Integer.parseInt(value); }catch(Exception e){}
						try{ valueNTmp1 = Integer.parseInt(valueTmp1); }catch(Exception e){}
						
						if( valueNTmp0 >= valueNTmp1 ){
							sortedJsonArray.add(i, jsonObj);
						}else{
							sortedJsonArray.add(jsonObj);
						}
						
					}else{
						if (value.compareTo(valueTmp1)> 0) {
							sortedJsonArray.add(i, jsonObj);
						} else {
							sortedJsonArray.add(jsonObj);
						}
					}
				}
				return;
			} else {

				existingObj1 = (JSONObject) sortedJsonArray.get(i);
				valueTmp1 = (String) existingObj1.get(sidx);

				existingObj2 = (JSONObject) sortedJsonArray.get(i + 1);
				valueTmp2 = (String) existingObj2.get(sidx);

				value = value.toLowerCase();
				valueTmp1 = valueTmp1.toLowerCase();
				valueTmp2 = valueTmp2.toLowerCase();

				if (sord.equals("asc")) {
					if(isDateField) {
						if(getDateWithFormat(value).compareTo(getDateWithFormat(valueTmp1))<0){
							sortedJsonArray.add(i, jsonObj);
							return;
						}else if (getDateWithFormat(value).compareTo(getDateWithFormat(valueTmp1)) > 0) {
							if (getDateWithFormat(value).compareTo(getDateWithFormat(valueTmp2)) < 0) {
								sortedJsonArray.add(i + 1, jsonObj);
								return;
							}
						} else {
							sortedJsonArray.add(i, jsonObj);
							return;
						}
					}else if(isNumberField) {
						valueNTmp0 = 0;
						valueNTmp1 = 0;
						valueNTmp2 = 0;
						
						try{ valueNTmp0 = Integer.parseInt(value); }catch(Exception e){}
						try{ valueNTmp1 = Integer.parseInt(valueTmp1); }catch(Exception e){}
						try{ valueNTmp2 = Integer.parseInt(valueTmp2); }catch(Exception e){}
						
						if( valueNTmp0 <= valueNTmp1 ){
							sortedJsonArray.add(i, jsonObj);
							return;
						}else if( valueNTmp1 < valueNTmp0){
							if ( valueNTmp0 <= valueNTmp2 ) {
								sortedJsonArray.add(i + 1, jsonObj);
								return;
							}
						}else {
							sortedJsonArray.add(i + 2, jsonObj);
							return;
						}
						
					}else{
					if (value.compareTo(valueTmp1) < 0) {
						sortedJsonArray.add(i, jsonObj);
						return;
					} else if (value.compareTo(valueTmp1) > 0) {
						if (value.compareTo(valueTmp2) < 0) {
							sortedJsonArray.add(i + 1, jsonObj);
							return;
						}
					} else {
						sortedJsonArray.add(i, jsonObj);
						return;
					}
				}
				} else {
					if(isDateField) {
						if(getDateWithFormat(value).compareTo(getDateWithFormat(valueTmp1))>0){
							sortedJsonArray.add(i, jsonObj);
							return;
						}else if (getDateWithFormat(value).compareTo(getDateWithFormat(valueTmp1)) < 0) {
							if (getDateWithFormat(value).compareTo(getDateWithFormat(valueTmp2)) > 0) {
								sortedJsonArray.add(i + 1, jsonObj);
								return;
							}
						} else {
							sortedJsonArray.add(i, jsonObj);
							return;
						}
					}else if(isNumberField) {
						valueNTmp0 = 0;
						valueNTmp1 = 0;
						valueNTmp2 = 0;
						
						try{ valueNTmp0 = Integer.parseInt(value); }catch(Exception e){}
						try{ valueNTmp1 = Integer.parseInt(valueTmp1); }catch(Exception e){}
						try{ valueNTmp2 = Integer.parseInt(valueTmp2); }catch(Exception e){}
						
						if( valueNTmp0 >= valueNTmp1 ){
							sortedJsonArray.add(i, jsonObj);
							return;
						}else if( valueNTmp1 > valueNTmp0){
							if(valueNTmp0 >= valueNTmp2 ){
								sortedJsonArray.add(i + 1, jsonObj);
								return;
							}
						}else {
							sortedJsonArray.add(i + 2, jsonObj);
							return;
						}
						
					}else{
					if (value.compareTo(valueTmp1) > 0) {
						sortedJsonArray.add(i, jsonObj);
						return;
					} else if (value.compareTo(valueTmp1) < 0) {
						if (value.compareTo(valueTmp2) > 0) {
							sortedJsonArray.add(i + 1, jsonObj);
							return;
						}
					} else {
						sortedJsonArray.add(i, jsonObj);
						return;
					}
				}
				}
			}
		}// end for
	}

	
	
	/*
	 * private void insert(JSONObject jsonObj, String basisToSort, JSONArray
	 * sortedJsonArray) throws Exception{
	 * 
	 * JSONObject existingObj1 = null; JSONObject existingObj2 = null; String
	 * valueTmp1 = null; String valueTmp2 = null; String value =
	 * (String)jsonObj.get(basisToSort); //logger.info("basisToSort: " +
	 * basisToSort); //logger.info("value: " + value);
	 * 
	 * if(value == null || value.equals("")){ sortedJsonArray.add(jsonObj);
	 * logger.info("****** 1. inserted !!!! ******");
	 * logger.info("sortedJsonArray.size(): " + sortedJsonArray.size()); return;
	 * }
	 * 
	 * if(sortedJsonArray.size() == 0){ sortedJsonArray.add(jsonObj);
	 * logger.info("****** 2. inserted !!!! ******");
	 * logger.info("sortedJsonArray.size(): " + sortedJsonArray.size()); return;
	 * }
	 * 
	 * if( sortedJsonArray.size() == 1 ){
	 * 
	 * existingObj1 = (JSONObject)sortedJsonArray.get(0); valueTmp1 =
	 * (String)existingObj1.get(basisToSort);
	 * 
	 * value = value.toLowerCase(); valueTmp1 = valueTmp1.toLowerCase();
	 * 
	 * if(value.compareTo(valueTmp1) < 0){ sortedJsonArray.add(0, jsonObj);
	 * logger.info("****** 3. inserted !!!! ******");
	 * logger.info("sortedJsonArray.size(): " + sortedJsonArray.size()); return;
	 * } else{ sortedJsonArray.add(jsonObj);
	 * logger.info("****** 4. inserted !!!! ******");
	 * logger.info("sortedJsonArray.size(): " + sortedJsonArray.size()); return;
	 * } }
	 * 
	 * for(int i=0; i<sortedJsonArray.size(); i++){
	 * 
	 * if( (sortedJsonArray.size() == 1) || ((i+1) == sortedJsonArray.size()) ){
	 * 
	 * existingObj1 = (JSONObject)sortedJsonArray.get(i); valueTmp1 =
	 * (String)existingObj1.get(basisToSort);
	 * 
	 * value = value.toLowerCase(); valueTmp1 = valueTmp1.toLowerCase();
	 * 
	 * if(value.compareTo(valueTmp1) < 0){ sortedJsonArray.add(i, jsonObj);
	 * logger.info("****** 5. inserted !!!! ******");
	 * logger.info("sortedJsonArray.size(): " + sortedJsonArray.size()); return;
	 * } else{ sortedJsonArray.add(jsonObj);
	 * logger.info("****** 6. inserted !!!! ******");
	 * logger.info("sortedJsonArray.size(): " + sortedJsonArray.size()); return;
	 * } } else{
	 * 
	 * existingObj1 = (JSONObject)sortedJsonArray.get(i); valueTmp1 =
	 * (String)existingObj1.get(basisToSort);
	 * 
	 * existingObj2 = (JSONObject)sortedJsonArray.get(i+1); valueTmp2 =
	 * (String)existingObj2.get(basisToSort);
	 * 
	 * value = value.toLowerCase(); valueTmp1 = valueTmp1.toLowerCase();
	 * valueTmp2 = valueTmp2.toLowerCase();
	 * 
	 * if( value.compareTo(valueTmp1) < 0 ){ sortedJsonArray.add(i, jsonObj);
	 * logger.info("****** 7. inserted !!!! ******");
	 * logger.info("sortedJsonArray.size(): " + sortedJsonArray.size()); return;
	 * } else if( value.compareTo(valueTmp1) > 0 ){ if(
	 * value.compareTo(valueTmp2) < 0 ){ sortedJsonArray.add(i+1, jsonObj);
	 * logger.info("****** 8. inserted !!!! ******");
	 * logger.info("sortedJsonArray.size(): " + sortedJsonArray.size()); return;
	 * } } else{ sortedJsonArray.add(i, jsonObj);
	 * logger.info("****** 9. inserted !!!! ******");
	 * logger.info("sortedJsonArray.size(): " + sortedJsonArray.size()); return;
	 * } } }//end for logger.info("\n"); } //
	 */
	private List makeListWithMap(JSONArray orgJsonArray, String element)
			throws Exception {
		List<Map> list = new ArrayList<Map>();
		Map map = null;
		JSONObject jObj = null;
		for (int i = 0; i < orgJsonArray.size(); i++) {
			jObj = (JSONObject) orgJsonArray.get(i);
			map = new HashMap();
			map.put(jObj.get(element), jObj);
			list.add(map);
		}
		return list;
	}

	// Sorted Set ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ«Ã¯Â¿Â½Ã‚Â¼ÃƒÂ¬Ã¢â‚¬Å¾Ã…â€œ ÃƒÂ«Ã‚Â¬Ã‚Â¸ÃƒÂ¬Ã‚Â Ã…â€œÃƒÂªÃ‚Â°Ã¢â€šÂ¬ ÃƒÂ«Ã¯Â¿Â½Ã‚Â¨
	/*
	 * private JSONArray sort(JSONArray orgJsonArray, String elementName){
	 * 
	 * logger.info("##AAA5 orgJsonArray.length(): " + orgJsonArray.length());
	 * 
	 * JSONArray newJsonArray = new JSONArray(); SortedMap sm = new TreeMap();
	 * JSONObject jObj = null; String name = null; try{ for(int i=0;
	 * i<orgJsonArray.length(); i++){ jObj = (JSONObject)orgJsonArray.get(i);
	 * name = (String)jObj.get("name"); sm.put(name, jObj); } Set set =
	 * sm.entrySet(); logger.info("##AAA6 set.size(): " + set.size()); //<==
	 * ÃƒÂ¬Ã¢â‚¬â€�Ã‚Â¬ÃƒÂªÃ‚Â¸Ã‚Â°ÃƒÂ¬Ã¢â‚¬Å¾Ã…â€œ ÃƒÂªÃ‚Â°Ã…â€œÃƒÂ¬Ã‹â€ Ã‹Å“ÃƒÂªÃ‚Â°Ã¢â€šÂ¬ ÃƒÂ¬Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ¬Ã¢â‚¬â€œÃ‚Â´ÃƒÂ«Ã¢â‚¬Å“Ã‚Â ÃƒÂ«Ã¢â‚¬Â¹Ã‚Â¤
	 * Iterator itr = set.iterator(); int tmpCnt = orgJsonArray.length();
	 * while(itr.hasNext()){ Map.Entry m = (Map.Entry)itr.next(); jObj =
	 * (JSONObject)m.getValue(); jObj.put("no", Integer.toString(tmpCnt--));
	 * newJsonArray.put(jObj); } } catch(Exception e){ e.printStackTrace();
	 * logger.severe(e.getMessage()); } return newJsonArray; } //
	 */

	private Date getDateWithFormat( String value){
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date dt = null;
		try {
			dt = formatter.parse(value);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;
	}
	
	public JSONArray getListForPaging(JSONArray orgJsonArray, int beginIdx,
			int size) {

		logger.info("============> orgJsonArray total size: "
				+ orgJsonArray.size() + ", beginIdx: " + beginIdx + ", size: "
				+ size);
		JSONArray newJsonArray = new JSONArray();
		try {
			for (int i = 0; i < orgJsonArray.size(); i++) {
				if (i >= beginIdx) {
					JSONObject o = (JSONObject) orgJsonArray.get(i);
					for(Object key : o.keySet()){
						Object thisO = o.get(key);
						if(thisO instanceof String){
							String str = (String)thisO;
							if(str.equals("-")){
								o.put(key, "");
								System.out.println("new O " + o.get(key));
							}
						}
					}
							
					
					
					newJsonArray.add(o);
					if (newJsonArray.size() == size) {
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
		return newJsonArray;
	}

	public String getFullName(ContactEntry entry) {
		String fullNameStr = "";
		Name name = entry.getName();
		if (name != null) {
			FullName fullName = name.getFullName();
			if (fullName != null) {
				fullNameStr = fullName.getValue();
			}
		}
		return fullNameStr;
	}

	public String getFamilyName(ContactEntry entry) {
		String familyNameStr = "";
		Name name = entry.getName();
		if (name != null) {
			FamilyName familyName = name.getFamilyName();
			if (familyName != null) {
				familyNameStr = familyName.getValue();
			}
		}
		return familyNameStr;
	}

	public String getGivenName(ContactEntry entry) {
		String givenNameStr = "";
		Name name = entry.getName();
		if (name != null) {
			GivenName givenName = name.getGivenName();
			if (givenName != null) {
				givenNameStr = givenName.getValue();
			}
		}
		return givenNameStr;
	}

	public boolean hasPrimaryEmail(ContactEntry entry) {
		List<Email> emails = entry.getEmailAddresses();
		Email email = null;
		boolean result = false;
		if (emails != null) {
			for (int i = 0; i < emails.size(); i++) {
				email = (Email) emails.get(i);
				if (email != null) {
					result = email.getPrimary();
				}
			}
		}
		return result;
	}

	public String getEmail(ContactEntry entry, String rel) {
		List<Email> emails = entry.getEmailAddresses();
		Email email = null;
		String result = "";
		if (emails != null) {
			String relStr = null;
			for (int i = 0; i < emails.size(); i++) {
				email = (Email) emails.get(i);
				relStr = email.getRel();
				if (relStr.indexOf(rel) != -1) {
					result = email.getAddress();
					break;
				}
			}
		}
		return result;
	}

	public Email getEmailObj(ContactEntry entry, String rel) {
		List<Email> emails = entry.getEmailAddresses();
		Email email = null;
		Email emailTmp = null;
		if (emails != null) {
			String relStr = null;
			for (int i = 0; i < emails.size(); i++) {
				emailTmp = (Email) emails.get(i);
				if (emailTmp != null) {
					relStr = emailTmp.getRel();
					if (relStr.indexOf(rel) != -1) {
						email = emailTmp;
						break;
					}
				}
			}
		}
		return email;
	}

	public void removeEmailObj(ContactEntry entry, String rel) {
		List<Email> emails = entry.getEmailAddresses();
		Email email = null;
		if (emails != null) {
			String relStr = null;
			for (int i = 0; i < emails.size(); i++) {
				email = (Email) emails.get(i);
				if (email != null) {
					relStr = email.getRel();
					if (relStr.indexOf(rel) != -1) {
						emails.remove(i);
						break;
					}
				}
			}
		}
	}

	public void removePhoneNumberObj(ContactEntry entry, String rel) {
		List<PhoneNumber> phoneNumbers = entry.getPhoneNumbers();
		PhoneNumber phoneNumber = null;
		if (phoneNumbers != null) {
			String relStr = null;
			for (int i = 0; i < phoneNumbers.size(); i++) {
				phoneNumber = (PhoneNumber) phoneNumbers.get(i);
				if (phoneNumber != null) {
					relStr = phoneNumber.getRel();
					if (relStr.indexOf(rel) != -1) {
						phoneNumbers.remove(i);
						break;
					}
				}
			}
		}
	}

	public PhoneNumber getPhoneNumberObj(ContactEntry entry, String rel) {
		List<PhoneNumber> phoneNumbers = entry.getPhoneNumbers();
		PhoneNumber phoneNumber = null;
		PhoneNumber phoneNumberTmp = null;
		if (phoneNumbers != null) {
			String relStr = null;
			for (int i = 0; i < phoneNumbers.size(); i++) {
				phoneNumberTmp = (PhoneNumber) phoneNumbers.get(i);
				if (phoneNumberTmp != null) {
					relStr = phoneNumberTmp.getRel();
					if (relStr.indexOf(rel) != -1) {
						phoneNumber = phoneNumberTmp;
						break;
					}
				}
			}
		}
		return phoneNumber;
	}

	public String getPhoneNumber(ContactEntry entry, String rel) {
		List<PhoneNumber> phoneNumbers = entry.getPhoneNumbers();
		PhoneNumber phoneNumber = null;
		String result = "";
		if (phoneNumbers != null) {
			String relStr = null;
			for (int i = 0; i < phoneNumbers.size(); i++) {
				phoneNumber = (PhoneNumber) phoneNumbers.get(i);
				relStr = phoneNumber.getRel();
				if (relStr.indexOf(rel) != -1) {
					result = phoneNumber.getPhoneNumber();
					break;
				}
			}
		}
		return result;
	}

	public String getFormattedAddress(ContactEntry entry, String rel) {
		String result = "";
		List<StructuredPostalAddress> list = entry
				.getRepeatingExtension(StructuredPostalAddress.class);
		StructuredPostalAddress postalAddress = null;
		String relTmp = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				postalAddress = (StructuredPostalAddress) list.get(i);
				relTmp = postalAddress.getRel();
				if (relTmp != null && relTmp.equals(rel)) {
					FormattedAddress formattedAddress = postalAddress
							.getFormattedAddress();
					result = formattedAddress.getValue();
					break;
				}
			}
		}
		return result;
	}

	public StructuredPostalAddress getStructuredPostalAddress(
			ContactEntry entry, String rel) {
		List<StructuredPostalAddress> list = entry
				.getRepeatingExtension(StructuredPostalAddress.class);
		StructuredPostalAddress postalAddressTmp = null;
		StructuredPostalAddress result = null;
		String relTmp = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				postalAddressTmp = (StructuredPostalAddress) list.get(i);
				relTmp = postalAddressTmp.getRel();
				if (relTmp != null && relTmp.equals(rel)) {
					result = postalAddressTmp;
					break;
				}
			}
		}
		return result;
	}

	public void removeStructuredPostalAddress(ContactEntry entry, String rel) {
		List<StructuredPostalAddress> list = entry
				.getRepeatingExtension(StructuredPostalAddress.class);
		StructuredPostalAddress postalAddressTmp = null;
		String relTmp = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				postalAddressTmp = (StructuredPostalAddress) list.get(i);
				relTmp = postalAddressTmp.getRel();
				if (relTmp != null && relTmp.equals(rel)) {
					entry.removeRepeatingExtension(postalAddressTmp);
					break;
				}
			}
		}
	}

	public String getAddress(ContactEntry entry, String rel) {
		List<PostalAddress> addresses = entry.getPostalAddresses();
		PostalAddress postalAddress = null;
		String result = "";
		logger.info("********** addresses is null ? " + (addresses == null));
		if (addresses != null) {
			String relStr = null;
			logger.info("addresses.size(): " + addresses.size());
			for (int i = 0; i < addresses.size(); i++) {
				postalAddress = (PostalAddress) addresses.get(i);
				relStr = postalAddress.getRel();
				logger.info("relStr ====> " + relStr);
				if (relStr.indexOf(rel) != -1) {
					result = postalAddress.getValue();
					break;
				}
			}
		}
		return result;
	}

	public String getOrganization(ContactEntry entry, String rel) {
		List<Organization> orgs = entry.getOrganizations();
		Organization org = null;
		String result = "";
		if (orgs != null) {
			String relStr = null;
			for (int i = 0; i < orgs.size(); i++) {
				org = (Organization) orgs.get(i);
				if (org != null) {
					relStr = org.getRel();
					if (relStr.indexOf(rel) != -1) {
						OrgName orgName = org.getOrgName();
						if (orgName != null) {
							result = orgName.getValue();
							break;
						}
					}
				}
			}
		}
		return result;
	}

	public Organization getOrganizationObj(ContactEntry entry, String rel) {
		List<Organization> orgs = entry.getOrganizations();
		Organization org = null;
		Organization orgTmp = null;
		if (orgs != null) {
			String relStr = null;
			for (int i = 0; i < orgs.size(); i++) {
				orgTmp = (Organization) orgs.get(i);
				if (orgTmp != null) {
					relStr = orgTmp.getRel();
					if (relStr.indexOf(rel) != -1) {
						org = orgTmp;
						break;
					}
				}
			}
		}
		return org;
	}

	public String getOrganizationDept(ContactEntry entry, String rel) {
		List<Organization> orgs = entry.getOrganizations();
		Organization org = null;
		String result = "";
		if (orgs != null) {
			String relStr = null;
			for (int i = 0; i < orgs.size(); i++) {
				org = (Organization) orgs.get(i);
				relStr = org.getRel();
				if (relStr.indexOf(rel) != -1) {
					OrgDepartment orgDept = org.getOrgDepartment();
					if (orgDept != null) {
						result = orgDept.getValue();
						break;
					}
				}
			}
		}
		return result;
	}

	public String getOrganizationTitle(ContactEntry entry, String rel) {
		List<Organization> orgs = entry.getOrganizations();
		Organization org = null;
		String result = "";
		if (orgs != null) {
			String relStr = null;
			for (int i = 0; i < orgs.size(); i++) {
				org = (Organization) orgs.get(i);
				relStr = org.getRel();
				if (relStr.indexOf(rel) != -1) {
					OrgTitle orgTitle = org.getOrgTitle();
					if (orgTitle != null) {
						result = orgTitle.getValue();
						break;
					}
				}
			}
		}
		return result;
	}

	public String getExtendedProperty(ContactEntry entry, String propName) {

		List<ExtendedProperty> extProps = entry.getExtendedProperties();

		ExtendedProperty extProp = null;
		String result = "";
		String name = null;
		if (extProps != null) {
			for (int i = 0; i < extProps.size(); i++) {
				extProp = (ExtendedProperty) extProps.get(i);
				name = extProp.getName();
				if (name.equals(propName)) {
					result = extProp.getValue();
					break;
				}
			}// end for
		}// end if
		return result;
	}

	public ExtendedProperty getExtendedPropertyObj(ContactEntry entry,
			String propName) {

		List<ExtendedProperty> extProps = entry.getExtendedProperties();

		ExtendedProperty extProp = null;
		ExtendedProperty extPropTmp = null;
		String result = "";
		String name = null;
		if (extProps != null) {
			for (int i = 0; i < extProps.size(); i++) {
				extPropTmp = (ExtendedProperty) extProps.get(i);
				name = extPropTmp.getName();
				if (name.equals(propName)) {
					extProp = extPropTmp;
					break;
				}
			}// end for
		}// end if
		return extProp;
	}

	public void removePropertyObj(ContactEntry entry, String propName) {

		List<ExtendedProperty> extProps = entry.getExtendedProperties();

		ExtendedProperty extPropTmp = null;
		String result = "";
		String name = null;
		if (extProps != null) {
			for (int i = 0; i < extProps.size(); i++) {
				extPropTmp = (ExtendedProperty) extProps.get(i);
				name = extPropTmp.getName();
				if (name.equals(propName)) {
					extProps.remove(i);
					break;
				}
			}// end for
		}// end if
	}

	/*
	 * public String getNotes(ContactEntry entry){ String result = ""; try{
	 * result = entry.getPlainTextContent(); } catch(Exception e){
	 * //e.printStackTrace(); logger.info("ERROR: " + e.getMessage()); } return
	 * result; } //
	 */
	public String getNotes(ContactEntry entry) {
		String result = "";
		List list = null;
		try {
			list = (List) entry.getUserDefinedFields();
			UserDefinedField userDefinedField = null;
			String key = null;
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					userDefinedField = (UserDefinedField) list.get(i);
					key = userDefinedField.getKey();
					if (key.equals("Notes")) {
						result = userDefinedField.getValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.info("ERROR: " + e.getMessage());
		}
		return result;
	}

	public void removeUserDefinedField(ContactEntry entry, String key) {
		List list = null;
		try {
			list = (List) entry.getUserDefinedFields();
			UserDefinedField userDefinedField = null;
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					userDefinedField = (UserDefinedField) list.get(i);
					key = userDefinedField.getKey();
					if (key.equals(key)) {
						userDefinedField
								.removeExtension(UserDefinedField.class);
						break;
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.info("ERROR: " + e.getMessage());
		}
	}

	public void updateUserDefinedField(ContactEntry entry, String key,
			String value) {
		List list = null;
		try {
			list = (List) entry.getUserDefinedFields();
			UserDefinedField userDefinedField = null;
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					userDefinedField = (UserDefinedField) list.get(i);
					key = userDefinedField.getKey();
					if (key.equals(key)) {
						userDefinedField.setValue(value);
						break;
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.info("ERROR: " + e.getMessage());
		}
	}

}
