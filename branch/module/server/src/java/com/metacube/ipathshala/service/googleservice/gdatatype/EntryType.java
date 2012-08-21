package com.metacube.ipathshala.service.googleservice.gdatatype;

public enum EntryType {
	ANNOUNCEMENT, ATTACHMENT, COMMENT, LIST_ITEM, LIST_PAGE, WEB_PAGE, WEB_ATTACHMENT;

	@Override
	public String toString() {
		switch (this) {
		case ANNOUNCEMENT:
			return "announcement";
		case ATTACHMENT:
			return "attachment";
		case COMMENT:
			return "comment";
		case LIST_ITEM:
			return "listitem";
		case LIST_PAGE:
			return "listpage";
		case WEB_PAGE:
			return "webpage";
		case WEB_ATTACHMENT:
			return "webattachment";
		default:
			return null;
		}
	}

}
