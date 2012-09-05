package com.netkiller.service.googleservice.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gdata.data.Link;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.OutOfLineContent;
import com.google.gdata.data.acl.AclEntry;
import com.google.gdata.data.sites.AnnouncementEntry;
import com.google.gdata.data.sites.AttachmentEntry;
import com.google.gdata.data.sites.BaseContentEntry;
import com.google.gdata.data.sites.SiteEntry;
import com.google.gdata.data.sites.WebAttachmentEntry;
import com.netkiller.sites.AclForSite;
import com.netkiller.sites.Announcement;
import com.netkiller.sites.Attachment;
import com.netkiller.sites.GoogleSiteRoleType;
import com.netkiller.sites.Scope;
import com.netkiller.sites.Site;
import com.netkiller.sites.SitePage;
import com.netkiller.sites.WebAttachment;

/**
 * This class copies the GoogleObjects for Sites to ipathshala Sites Object and vice versa
 * @author administrator
 *
 */
public class GoogleSitesUtil {
	

	/**
	 * This method copy the Google Object AttachnmentEntry to iPathsala Object Attachment
	 * @param attachmentEntry
	 * @return
	 */
	public static Attachment convertAttachmentEntryToAttachment(AttachmentEntry attachmentEntry) {
		if(attachmentEntry == null) {
			return null ;
		}
		Attachment attachment = new Attachment();
		attachment.setTitle(attachmentEntry.getTitle().getPlainText()) ;
		attachment.setSummary(attachmentEntry.getSummary().getPlainText());
		attachment.setParentLink(attachmentEntry.getLink("alternate", null).getHref());
		attachment.setUrl(((OutOfLineContent) attachmentEntry.getContent()).getUri()) ;
		attachment.setId(attachmentEntry.getId()) ;
		attachment.setLastUpdatedDate(new Date(attachmentEntry.getUpdated().getValue()));
		return attachment ;
	}
	
	/**
	 * 
	 * @param attachmentEntryList
	 * @return
	 */
	public static List<Attachment> convertAttachmentEntryListToAttachmentList(List<AttachmentEntry> attachmentEntryList) {
		List<Attachment> attachmentList = new ArrayList<Attachment>() ;
		for(AttachmentEntry attachmentEntry : attachmentEntryList) {
			attachmentList.add(convertAttachmentEntryToAttachment(attachmentEntry)) ;
		}
		return attachmentList ;
	}
	
	/**
	 * This method copy the Google Object AnnouncementEntry to iPathsala Object Announcement
	 * @param announcementEntry
	 * @param siteName 
	 * @return
	 */
	public static  Announcement convertAnnouncementEntryToAnnouncement(AnnouncementEntry announcementEntry, String siteName) {
		Announcement announcement = new Announcement();
		announcement.setLastUpdatedDate(new Date(announcementEntry.getUpdated().getValue()));
		announcement.setTitle(announcementEntry.getTitle().getPlainText());
		announcement.setLink(announcementEntry.getHtmlLink().getHref());
		announcement.setContent(announcementEntry.getTextContent().getContent().getPlainText());
		announcement.setParentLink(announcementEntry.getParentLink().getHref());
		announcement.setSiteName(siteName);
		// It is assumed that there is only one author.
		if(announcementEntry.getAuthors()!=null && !announcementEntry.getAuthors().isEmpty()) {
			announcement.setAuthor(announcementEntry.getAuthors().get(0).getName());
		}else{
			announcement.setAuthor(null);
		}
		return announcement;
	}
	
	/**
	 * This method copy the Google Object SiteEntry to iPathsala Object Site
	 * @param siteEntry
	 * @return
	 */
	public static  Site convertSiteEntryToSite(SiteEntry siteEntry) {
		Site site = new Site() ;
		site.setTitle(siteEntry.getTitle().getPlainText());
		site.setSummary(siteEntry.getSummary().getPlainText()) ;
		site.setSiteHref(siteEntry.getHtmlLink().getHref()) ;
		site.setTheme(siteEntry.getTheme().getValue()) ;
		site.setSiteName(siteEntry.getSiteName().getValue()) ;
		site.setId(siteEntry.getId());		
		return site ;
	}
	
	/**
	 * 
	 * @param webAttachmentEntry
	 * @return
	 */
	public static  WebAttachment convertWebAttachmentEntryToWebAttachment(WebAttachmentEntry webAttachmentEntry) {
		WebAttachment webAttachment = new WebAttachment() ;
		webAttachment.setTitle(webAttachmentEntry.getTitle().getPlainText());
		webAttachment.setSummary(webAttachmentEntry.getSummary().getPlainText());
		webAttachment.setParentLink(webAttachmentEntry.getParentLink().getHref()) ;
		webAttachment.setId(webAttachmentEntry.getId());
		webAttachment.setUrl(((MediaContent) webAttachmentEntry.getContent()).getUri());
		return webAttachment ;
	}
	
	/**
	 * 
	 * @param aclEntry
	 * @return
	 */
	public static AclForSite convertAclEntriesToAclForSites(AclEntry aclEntry) {
		AclForSite aclForSites = new AclForSite();
		
		GoogleSiteRoleType roleType = null ;
		String roleValue = aclEntry.getRole().getValue() ;
		if(roleValue.equalsIgnoreCase("none")) {
			roleType = GoogleSiteRoleType.NONE ;
		}else if(roleValue.equalsIgnoreCase("reader")) {
			roleType = GoogleSiteRoleType.READER ;
		}else if(roleValue.equalsIgnoreCase("writer")) {
			roleType = GoogleSiteRoleType.WRITER ;
		}else if(roleValue.equalsIgnoreCase("peeker")) {
			roleType = GoogleSiteRoleType.PEEKER ;
		}else if(roleValue.equalsIgnoreCase("owner")) {
			roleType = GoogleSiteRoleType.OWNER ;
		}
		
		
		aclForSites.setRoleType(roleType) ;
		Scope.Type type = null ;
		switch(aclEntry.getScope().getType()) {
		case USER :
			type = Scope.Type.USER ;
			break ;
		case DOMAIN :
			type = Scope.Type.DOMAIN ;
			break ;
		case GROUP :
			type = Scope.Type.GROUP ;
			break ;
		case DEFAULT :
			type = Scope.Type.DEFAULT ;
			break ;
		}
		aclForSites.setScope(new Scope(type,aclEntry.getScope().getValue())) ;
		aclForSites.setId(aclEntry.getId()) ;
		return aclForSites ;
		
	}
	
	/**
	 * 
	 * @param baseContentEntryPage
	 * @return
	 */
	public static SitePage convertBaseContentEntryPageToSitePage(BaseContentEntry<?> baseContentEntryPage) {
		SitePage sitePage = new SitePage() ;
		
		if(baseContentEntryPage == null) {
			return null ;
		}
		sitePage.setId(baseContentEntryPage.getId()) ;
		sitePage.setTitle(baseContentEntryPage.getTitle().getPlainText()) ;
		if(baseContentEntryPage.getSummary()!=null) {
			sitePage.setSummary(baseContentEntryPage.getSummary().getPlainText()) ;
		}
		sitePage.setKind(baseContentEntryPage.getKind()) ;
		sitePage.setCanEdit(baseContentEntryPage.getCanEdit()) ;
		sitePage.setSelfLink(baseContentEntryPage.getSelfLink().getHref()) ;
		sitePage.setEditLink(baseContentEntryPage.getEditLink().getHref()) ;
		
		return sitePage ;
	}
	
}
