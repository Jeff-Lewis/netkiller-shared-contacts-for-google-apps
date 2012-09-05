package com.netkiller.service.googleservice.gdatatype;

public enum EntryContainerType {
	ANNOUNCEMENTS_PAGE, FILE_CABINET;

	@Override
	public String toString() {
		switch (this) {
		case ANNOUNCEMENTS_PAGE:
			return "announcementspage";
		case FILE_CABINET:
			return "filecabinet";
		default:
			return null;
		}
	}

}
