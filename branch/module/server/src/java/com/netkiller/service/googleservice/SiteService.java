package com.netkiller.service.googleservice;

import java.util.List;

import com.google.gdata.data.sites.AnnouncementEntry;
import com.netkiller.core.AppException;
import com.netkiller.core.UserRoleType;
import com.netkiller.service.googleservice.gdatatype.EntryContainerType;
import com.netkiller.service.googleservice.gdatatype.EntryType;
import com.netkiller.sites.AclForSite;
import com.netkiller.sites.Announcement;
import com.netkiller.sites.Attachment;
import com.netkiller.sites.GoogleSiteRoleType;
import com.netkiller.sites.Site;
import com.netkiller.sites.SitePage;
import com.netkiller.sites.WebAttachment;

/**
 * 
 * @author administrator
 * 
 */
public interface SiteService {

	public Site createSite(String title, String summary, String theme)
			throws AppException;

	public Site copySite(String title, String summary, String sourceHref)
			throws AppException;

	public List<Site> getSiteEntries() throws AppException;

	public List<Attachment> getAttachments(String siteName) throws AppException;

	public List<WebAttachment> getWebAttachments(String siteName)
			throws AppException;

	public List<Announcement> getAnnouncements(String siteName)
			throws AppException;

	public void downloadAttachment(String siteName, String entryId,
			String directory) throws AppException;

	public void downloadAllAttachments(String directory, String siteName)
			throws AppException;

	public Attachment uploadAttachment(String siteName, byte[] fileByteArray,
			String parentLink, String title, String description)
			throws AppException;

	public WebAttachment uploadWebAttachment(String siteName,
			String contentUrl, String filecabinetLink, String title,
			String description) throws AppException;

	public Attachment updateAttachment(String attachmentId, String siteName,
			byte[] fileByteArray, String newTitle, String newDescription)
			throws AppException;

	public Attachment retrieveAttachmentById(String id)
			throws AppException;

	public List<Attachment> retrieveAllAttachments(String siteName)
			throws AppException;

	public void deleteAttachment(String attachmentId, String siteName)
			throws AppException;

	public Announcement postAnnouncement(String title, String content,
			String parentLink, String siteName) throws AppException;
	
	public String updateAnnouncement(String title, String content,
			String parentLink, String siteName,String announcementEntryId) throws AppException;
	
	public void deleteAnnouncement(String siteName,String announcementEntryId) throws AppException;
	
	public String getAnnouncementLink(String siteName,String announcementEntryId) throws AppException;
	
	public String getAnnouncementParentLink(String siteName,String announcementEntryId) throws AppException;
	
	public String postAnnouncementOnClassSite(String title, String content,
			String parentLink, String siteName) throws AppException;
	
	public SitePage createSitePage(EntryType entryType,
			EntryContainerType entryContainerType, String title, String parent,
			String siteName) throws AppException;

	public Site getSiteFromSiteName(String siteName) throws AppException;

	public void deletePage(String toBeDeletedPageId, String siteName)
			throws AppException;

	public SitePage retrievePageById(String id, String siteName,
			EntryType entryType, EntryContainerType entryContainerType)
			throws AppException;

	public void delete(String resourceId, String siteName) throws AppException;

	public List<AclForSite> getAclEntries(String siteName) throws AppException;

	public AclForSite addUserRole(GoogleSiteRoleType roleType, String username,
			String siteName) throws AppException;

	public AclForSite addGroupRole(GoogleSiteRoleType roleType,
			UserRoleType group, String siteName) throws AppException;
	
	public AclForSite addDomainRole(GoogleSiteRoleType roleType, String siteName) throws AppException;
	
	public SitePage getFileCabinetByName(String siteName,
			String fileCabinetTitle) throws AppException;

	public Site getSiteFromClassName(String name) throws AppException;
	
}
