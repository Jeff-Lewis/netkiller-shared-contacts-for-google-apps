package com.netkiller.service.googleservice;

/* Copyright (c) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gdata.client.sites.ContentQuery;
import com.google.gdata.client.sites.SitesService;
import com.google.gdata.data.Link;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.OutOfLineContent;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.XhtmlTextConstruct;
import com.google.gdata.data.acl.AclEntry;
import com.google.gdata.data.acl.AclFeed;
import com.google.gdata.data.acl.AclRole;
import com.google.gdata.data.acl.AclScope;
import com.google.gdata.data.media.MediaByteArraySource;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.data.sites.ActivityFeed;
import com.google.gdata.data.sites.AnnouncementEntry;
import com.google.gdata.data.sites.AnnouncementsPageEntry;
import com.google.gdata.data.sites.AttachmentEntry;
import com.google.gdata.data.sites.BaseContentEntry;
import com.google.gdata.data.sites.CommentEntry;
import com.google.gdata.data.sites.ContentFeed;
import com.google.gdata.data.sites.FileCabinetPageEntry;
import com.google.gdata.data.sites.ListItemEntry;
import com.google.gdata.data.sites.ListPageEntry;
import com.google.gdata.data.sites.SiteEntry;
import com.google.gdata.data.sites.SiteFeed;
import com.google.gdata.data.sites.SitesAclFeedLink;
import com.google.gdata.data.sites.SitesLink;
import com.google.gdata.data.sites.Theme;
import com.google.gdata.data.sites.WebAttachmentEntry;
import com.google.gdata.data.sites.WebPageEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.InvalidEntryException;
import com.google.gdata.util.ResourceNotFoundException;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.VersionConflictException;
import com.google.gdata.util.XmlBlob;
import com.google.gdata.util.common.base.PercentEscaper;
import com.netkiller.core.AppException;
import com.netkiller.core.EmptyFileException;
import com.netkiller.core.UniqueValidationException;
import com.netkiller.core.UserRoleType;
import com.netkiller.entity.MyClass;
import com.netkiller.security.DomainConfig;
import com.netkiller.service.googleservice.gdatatype.EntryContainerType;
import com.netkiller.service.googleservice.gdatatype.EntryType;
import com.netkiller.service.googleservice.util.GoogleSitesUtil;
import com.netkiller.sites.AclForSite;
import com.netkiller.sites.Announcement;
import com.netkiller.sites.Attachment;
import com.netkiller.sites.GoogleSiteRoleType;
import com.netkiller.sites.Site;
import com.netkiller.sites.SitePage;
import com.netkiller.sites.WebAttachment;
import com.netkiller.util.AppLogger;

/**
 * Wrapper class for lower level Sites API calls.
 * 
 * 
 */
/**
 * @author sparakh
 * 
 */
@Component("GoogleSitesService")
public class GoogleSitesService implements Serializable, SiteService {

	private String domain;
	private String scheme = "http://";
	private String secureScheme = "https://";
	public static final String APP_NAME = "metacube-iPathshala-1.1";
	public SitesService service;
	public MimetypesFileTypeMap mediaTypes;
	private static final AppLogger log = AppLogger
			.getLogger(GoogleSitesService.class);

	/**
	 * Constructor
	 * 
	 * @param domain
	 *            The Site's Google Apps domain or "site".
	 * @param siteName
	 *            The webspace name of the Site.
	 * @throws AuthenticationException
	 * @throws AppException
	 */
	@Autowired
	public GoogleSitesService(DomainConfig domainConfig) throws AppException {

		this.domain = domainConfig.getDomainName();
		this.service = new SitesService(APP_NAME);
		registerMediaTypes();
		login(domainConfig.getDomainAdminEmail(),
				domainConfig.getDomainAdminPassword());
	}

	private void registerMediaTypes() {
		// Common MIME types used for uploading attachments.
		mediaTypes = new MimetypesFileTypeMap();
		mediaTypes.addMimeTypes("application/msword doc");
		mediaTypes.addMimeTypes("application/vnd.ms-excel xls");
		mediaTypes.addMimeTypes("application/pdf pdf");
		mediaTypes.addMimeTypes("text/richtext rtx");
		mediaTypes.addMimeTypes("text/csv csv");
		mediaTypes.addMimeTypes("text/tab-separated-values tsv tab");
		mediaTypes
				.addMimeTypes("application/x-vnd.oasis.opendocument.spreadsheet ods");
		mediaTypes.addMimeTypes("application/vnd.oasis.opendocument.text odt");
		mediaTypes.addMimeTypes("application/vnd.ms-powerpoint ppt pps pot");
		mediaTypes
				.addMimeTypes("application/vnd.openxmlformats-officedocument."
						+ "wordprocessingml.document docx");
		mediaTypes
				.addMimeTypes("application/vnd.openxmlformats-officedocument."
						+ "spreadsheetml.sheet xlsx");
		mediaTypes.addMimeTypes("audio/mpeg mp3 mpeg3");
		mediaTypes.addMimeTypes("image/png png");
		mediaTypes.addMimeTypes("application/zip zip");
		mediaTypes.addMimeTypes("application/x-tar tar");
		mediaTypes.addMimeTypes("video/quicktime qt mov moov");
		mediaTypes.addMimeTypes("video/mpeg mpeg mpg mpe mpv vbs mpegv");
		mediaTypes.addMimeTypes("video/msvideo avi");
	}

	/**
	 * Authenticates the user using ClientLogin
	 * 
	 * @param username
	 *            User's email address.
	 * @param password
	 *            User's password.
	 */
	private void login(String username, String password) throws AppException {
		try {
			service.setUserCredentials(username, password);
		} catch (AuthenticationException ae) {
			String msg = "Cannot login the user";
			throw new AppException(msg, ae);
		}
	}

	/**
	 * Authenticates the user using AuthSub
	 * 
	 * @param authSubToken
	 *            A valid AuthSub session token.
	 */
	private void login(String authSubToken) {
		service.setAuthSubToken(authSubToken);
	}

	/**
	 * Returns an entry's numeric ID.
	 */
	private String getEntryId(BaseContentEntry<?> entry) {
		String selfLink = entry.getSelfLink().getHref();
		return selfLink.substring(selfLink.lastIndexOf("/") + 1);
	}

	/**
	 * Returns an entry's numeric ID.
	 */
	private String getEntryId(String selfLink) {
		return selfLink.substring(selfLink.lastIndexOf("/") + 1);
	}

	/**
	 * returns the ContentFeedUrl
	 * 
	 * @param siteName
	 * @return
	 */
	private String getContentFeedUrl(String siteName) {
		return this.secureScheme + "sites.google.com/feeds/content/" + domain
				+ "/" + siteName + "/";
	}

	/**
	 * Returns the revision feed Url
	 * 
	 * @param siteName
	 * @return
	 */
	private String getRevisionFeedUrl(String siteName) {
		return this.scheme + "sites.google.com/feeds/revision/" + domain + "/"
				+ siteName + "/";
	}

	/**
	 * Returns activity feed url
	 * 
	 * @param siteName
	 * @return
	 */
	private String getActivityFeedUrl(String siteName) {
		return this.scheme + "sites.google.com/feeds/activity/" + domain + "/"
				+ siteName + "/";
	}

	/**
	 * Returns SiteFeedUrl
	 * 
	 * @return
	 */
	private String getSiteFeedUrl() {
		return this.secureScheme + "sites.google.com/feeds/site/" + domain
				+ "/";
	}

	/**
	 * Returns SiteFeedUrl
	 * 
	 * @return
	 */
	private String getSiteFeedUrl(String siteName) {
		return this.secureScheme + "sites.google.com/feeds/site/" + domain
				+ "/" + siteName;

	}

	/**
	 * Returns Acl Feed url
	 * 
	 * @param siteName
	 * @return
	 */
	private String getAclFeedUrl(String siteName) {
		return this.secureScheme + "sites.google.com/feeds/acl/site/" + domain
				+ "/" + siteName + "/";
	}

	/**
	 * Returns Site url
	 * 
	 * @param siteName
	 * @return
	 */
	private String getSiteUrl(String siteName) {
		return this.scheme + "sites.google.com/a/" + domain + "/" + siteName
				+ "/";
	}

	/**
	 * Creates a new Google Sites.
	 * 
	 * @param title
	 *            A name for the site.
	 * @param summary
	 *            A description for the site.
	 * @param theme
	 *            A theme to create the site with.
	 * @return The created site entry.
	 * @throws AppException
	 * @throws ServiceException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public Site createSite(String title, String summary, String theme)
			throws AppException {
		SiteEntry entry = new SiteEntry();
		entry.setTitle(new PlainTextConstruct(title));
		entry.setSummary(new PlainTextConstruct(summary));

		// Set theme if user specified it.
		if (theme != null) {
			Theme tempTheme = new Theme();
			tempTheme.setValue(theme);
			entry.setTheme(tempTheme);
		}

		SiteEntry newEntry = null;

		try {
			newEntry = service.insert(new URL(getSiteFeedUrl()), entry);
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve Site feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		}

		// create the Site object using SiteEntry object of Google
		Site site = GoogleSitesUtil.convertSiteEntryToSite(newEntry);
		return site;
	}

	/**
	 * Copies a Google Site from an existing site.
	 * 
	 * @param title
	 *            A title heading for the new site.
	 * @param summary
	 *            A description for the site.
	 * @param theme
	 *            A theme to create the site with.
	 * @param sourceHref
	 *            The self link of the site entry to copy this site from. If
	 *            null, an empty site is created.
	 * @return The created site entry.
	 * @throws AppException
	 * @throws ServiceException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public Site copySite(String title, String summary, String siteName)
			throws AppException {
		SiteEntry entry = new SiteEntry();
		entry.setTitle(new PlainTextConstruct(title));
		entry.setSummary(new PlainTextConstruct(summary));
		entry.addLink(SitesLink.Rel.SOURCE, Link.Type.ATOM,
				getSiteFeedUrl(siteName));
	//	log.warn("Site Feed url "+getSiteFeedUrl(siteName));

		SiteEntry siteEntry;
		try {
			siteEntry = service.insert(new URL(getSiteFeedUrl()), entry);
		//	log.warn("Successfully Copied site ");
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve Site feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to retrieve Site feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve Site feed";
			log.error(message, e);
			throw new AppException(message, e);
		}

		// create the Site object using SiteEntry object of Google
		Site site = GoogleSitesUtil.convertSiteEntryToSite(siteEntry);

		return site;

	}

	/**
	 * Fetches and displays the user's site feed.
	 * 
	 * @return
	 * 
	 * @throws AppException
	 */
	private SiteFeed getSiteFeed() throws AppException {
		SiteFeed siteFeed;
		try {
			siteFeed = service.getFeed(new URL(getSiteFeedUrl()),
					SiteFeed.class);

		} catch (MalformedURLException e) {
			String message = "Unable to retrieve Site feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to retrieve Site feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve Site feed";
			log.error(message, e);
			throw new AppException(message, e);
		}

		return siteFeed;
	}

	/**
	 * This method returns all the Sites in the domain
	 * 
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws AppException
	 */
	public List<Site> getSiteEntries() throws AppException {
		SiteFeed feed = getSiteFeed();
		List<SiteEntry> siteEntries = new ArrayList<SiteEntry>();

		// feed.getEntries gives the 25 records, so to fetch the other records,
		// we use nextLink which
		// provides the link to the next page.. That is pagination is done by
		// GAE..

		SiteFeed contentFeed = null;
		List<SiteEntry> subList = null;

		// get siteEntries in the batches of 100
		int count = 0;
		do {
			int startIndex = 1 + count;
			ContentQuery query = null;
			try {
				query = new ContentQuery(new URL(getSiteFeedUrl()));
				query.setMaxResults(100);
				query.setStartIndex(startIndex);
			} catch (MalformedURLException e) {
				String message = "Url not properly set";
				log.error(message, e);
				throw new AppException(message, e);
			}

			try {
				contentFeed = service.getFeed(query, SiteFeed.class);
			} catch (IOException e) {
				String message = "Unable to retrieve Site feed";
				log.error(message, e);
				throw new AppException(message, e);
			} catch (ServiceException e) {
				String message = "Unable to retrieve Site feed";
				log.error(message, e);
				throw new AppException(message, e);
			}
			subList = contentFeed.getEntries();
			siteEntries.addAll(subList);
			count += 100;
		} while (subList.size() == 100);
		List<Site> siteList = new ArrayList<Site>();
		for (SiteEntry siteEntry : siteEntries) {
			Site site = GoogleSitesUtil.convertSiteEntryToSite(siteEntry);
			siteList.add(site);
		}
		return siteList;
	}

	/**
	 * @return
	 * @throws AppException
	 */
	public List<String> getSitesURLs() throws AppException {

		SiteFeed feed = getSiteFeed();
		List<String> siteUrls = new ArrayList<String>();

		for (SiteEntry entry : feed.getEntries()) {
			siteUrls.add(entry.getHtmlLink().getHref());
		}

		return siteUrls;

	}

	/**
	 * Fetches and displays the Site's activity feed.
	 * 
	 * @throws AppException
	 */
	private ActivityFeed getActivityFeed(String siteName) throws AppException {
		ActivityFeed activityFeed;
		try {
			activityFeed = service.getFeed(
					new URL(getActivityFeedUrl(siteName)), ActivityFeed.class);
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve Activity feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to retrieve Activity feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve Activity feed";
			log.error(message, e);
			throw new AppException(message, e);
		}

		return activityFeed;
	}

	/**
	 * This method retrieves the list of Announcement pages for a given siteName
	 * 
	 * @param site
	 * @return
	 * @throws AppException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public List<AnnouncementsPageEntry> getAnnouncementPages(String siteName)
			throws AppException {

		String url = getContentFeedUrl(siteName) + "?kind="
				+ EntryContainerType.ANNOUNCEMENTS_PAGE;
		ContentFeed contentFeed;
		try {
			contentFeed = service.getFeed(new URL(url), ContentFeed.class);
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		}

		return contentFeed.getEntries(AnnouncementsPageEntry.class);
	}

	/**
	 * This method retrieves the list of WebPagesEntry
	 * 
	 * @param site
	 * @return
	 * @throws IOException
	 * @throws ServiceException
	 */
	private List<WebPageEntry> getWebPages(String siteName) throws AppException {
		String url = getContentFeedUrl(siteName) + "?kind="
				+ EntryType.WEB_PAGE;
		ContentFeed contentFeed;
		try {
			contentFeed = service.getFeed(new URL(url), ContentFeed.class);
		} catch (IOException io) {
			String message = "Cannot obtain contentFeed";
			throw new AppException(message, io);
		} catch (ServiceException se) {
			String message = "Cannot obtain contentFeed";
			throw new AppException(message, se);
		}
		return contentFeed.getEntries(WebPageEntry.class);
	}

	/**
	 * This method returns the list of attachments from the SiteName
	 * 
	 * @param siteName
	 * @param site
	 * @return
	 * @throws AppException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public List<Attachment> getAttachments(String siteName) throws AppException {
		String url = getContentFeedUrl(siteName) + "?kind="
				+ EntryType.ATTACHMENT + "," + EntryType.WEB_PAGE;
		ContentFeed contentFeed;
		try {
			contentFeed = service.getFeed(new URL(url), ContentFeed.class);
		} catch (IOException ioe) {
			String msg = "Unable to retrieve content feed";
			throw new AppException(msg, ioe);
		} catch (ServiceException mue) {
			String msg = "Unable to retrieve content feed";
			throw new AppException(msg, mue);
		}

		List<AttachmentEntry> attachmentEntryList = contentFeed
				.getEntries(AttachmentEntry.class);
		List<Attachment> attachmentList = new ArrayList<Attachment>();
		for (AttachmentEntry attachmentEntry : attachmentEntryList) {
			Attachment attachment = GoogleSitesUtil
					.convertAttachmentEntryToAttachment(attachmentEntry);
			attachmentList.add(attachment);
		}

		return attachmentList;
	}

	public SitePage getFileCabinetByName(String siteName,
			String fileCabinetTitle) throws AppException {
		SitePage fileCabinet = null;
		List<SitePage> fileCabinetList = this.retrieveAllSitePages(siteName,
				null, EntryContainerType.FILE_CABINET);
		for (SitePage fileCab : fileCabinetList) {
			if (fileCab.getTitle().equalsIgnoreCase(fileCabinetTitle)) {
				fileCabinet = fileCab;
			}
		}
		return fileCabinet;
	}

	public List<Attachment> getAttachmentsByFileCabinetId(String siteName,
			String filecabinetId) throws AppException {
		ContentQuery query = null;
		try {
			query = new ContentQuery(new URL(getContentFeedUrl(siteName)));
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		}
		query.setParent(this.getEntryId(filecabinetId));
		ContentFeed contentFeed = null;
		try {
			contentFeed = service.getFeed(query, ContentFeed.class);
		} catch (IOException e) {
			String msg = "Unable to retrieve content feed";
			throw new AppException(msg, e);
		} catch (ServiceException e) {
			String msg = "Unable to retrieve content feed";
			throw new AppException(msg, e);
		}

		List<AttachmentEntry> attachmentEntryList = contentFeed
				.getEntries(AttachmentEntry.class);
		List<Attachment> attachmentList = new ArrayList<Attachment>();
		for (AttachmentEntry attachmentEntry : attachmentEntryList) {
			Attachment attachment = GoogleSitesUtil
					.convertAttachmentEntryToAttachment(attachmentEntry);
			attachmentList.add(attachment);
		}

		return attachmentList;
	}

	/**
	 * This method returns the list of WebAttachments for a given SiteName
	 * 
	 * @param site
	 * @return
	 * @throws AppException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public List<WebAttachment> getWebAttachments(String siteName)
			throws AppException {
		String url = getContentFeedUrl(siteName) + "?kind="
				+ EntryType.WEB_ATTACHMENT + "," + EntryType.WEB_PAGE;
		ContentFeed contentFeed;
		try {
			contentFeed = service.getFeed(new URL(url), ContentFeed.class);
		} catch (IOException ioe) {
			String message = "Unable to retrieve content feed";
			throw new AppException(message, ioe);
		} catch (ServiceException se) {
			String message = "Unable to retrieve content feed";
			throw new AppException(message, se);
		}

		List<WebAttachment> webAttachmentList = new ArrayList<WebAttachment>();
		for (WebAttachmentEntry entry : contentFeed
				.getEntries(WebAttachmentEntry.class)) {
			WebAttachment webAttachment = GoogleSitesUtil
					.convertWebAttachmentEntryToWebAttachment(entry);
			webAttachmentList.add(webAttachment);
		}
		return webAttachmentList;
	}

	/**
	 * @param site
	 * @return
	 * @throws IOException
	 * @throws ServiceException
	 */
	private List<BaseContentEntry> getEntries(String siteName)
			throws AppException {

		String url = getContentFeedUrl(siteName);
		ContentFeed contentFeed = null;
		try {
			contentFeed = service.getFeed(new URL(url), ContentFeed.class);
		} catch (MalformedURLException mue) {
			String message = "Unable to retrieve content feed";
			// log.error(message, mue);
			throw new AppException(message, mue);
		} catch (IOException mue) {
			String message = "Unable to retrieve content feed";
			// log.error(message, mue);
			throw new AppException(message, mue);
		} catch (ServiceException mue) {
			String message = "Unable to retrieve content feed";
			// log.error(message, mue);
			throw new AppException(message, mue);
		}

		return contentFeed.getEntries();
	}

	/**
	 * This method returns the list of Annoucenment for a given sitename
	 * 
	 * @param site
	 * @return
	 * @throws AppException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public List<Announcement> getAnnouncements(String siteName)
			throws AppException {

		String url = getContentFeedUrl(siteName);
		ContentFeed contentFeed = null;
		List<AnnouncementEntry> announcementEntryList = new ArrayList<AnnouncementEntry>();
		List<Announcement> announcementList = new ArrayList<Announcement>();
		try {
			List<AnnouncementEntry> subList = null;
			// get siteEntries in the batches of 100
			int count = 0;
			do {
				int startIndex = 1 + count;
				ContentQuery query = null;
				try {
					query = new ContentQuery(new URL(url));
					query.setMaxResults(100);
					query.setStartIndex(startIndex);
				} catch (MalformedURLException e) {
					String message = "Url not properly set";
					log.error(message, e);
					throw new AppException(message, e);
				}

				contentFeed = service.getFeed(query, ContentFeed.class);

				subList = contentFeed.getEntries(AnnouncementEntry.class);
				announcementEntryList.addAll(subList);
				count += 100;
			} while (subList.size() == 100);

			for (AnnouncementEntry announcementEntry : announcementEntryList) {
				Announcement announcement = GoogleSitesUtil
						.convertAnnouncementEntryToAnnouncement(
								announcementEntry, siteName);
				announcementList.add(announcement);
			}
		} catch (ResourceNotFoundException rnf) {
			String message = "Unable to retrieve the resource";
			log.error(message);
			// throw new AppException(message, rnf);
		} catch (IOException e) {
			String message = "Unable to retrieve Site feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve Site feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch(RuntimeException e)	{
			String message = "Unable to retrieve Site feed.";
			log.error(message, e);
		//	throw new AppException(message, e);
		}
		return announcementList;
	}

	private String getContentBlob(BaseContentEntry<?> entry) {
		return ((XhtmlTextConstruct) entry.getTextContent().getContent())
				.getXhtml().getBlob();
	}

	private void setContentBlob(BaseContentEntry<?> entry, String pageContent) {
		XmlBlob xml = new XmlBlob();
		xml.setBlob(pageContent);
		entry.setContent(new XhtmlTextConstruct(xml));
	}

	private void setContentBlob(BaseContentEntry<?> entry) {
		XmlBlob xml = new XmlBlob();
		xml.setBlob(String.format("content for %s", entry.getCategories()
				.iterator().next().getLabel()));
		entry.setContent(new XhtmlTextConstruct(xml));
	}

	/**
	 * This method creates a new site with the given title and summary
	 * 
	 * @param title
	 * @param summary
	 * @return
	 * @throws AppException
	 */
	public Site createSite(String title, String summary) throws AppException {
		return createSite(title, summary, null);
	}

	/**
	 * Creates the site
	 * 
	 * @param entryType
	 * @param entryContainerType
	 * @param title
	 * @param parent
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	public SitePage createSitePage(EntryType entryType,
			EntryContainerType entryContainerType, String title, String parent,
			String siteName) throws AppException {

		BaseContentEntry<?> googlePage = createPage(entryType,
				entryContainerType, title, parent, siteName);
		SitePage sitePage = GoogleSitesUtil
				.convertBaseContentEntryPageToSitePage(googlePage);
		sitePage.setEntryContainerType(entryContainerType);
		sitePage.setEntryType(entryType);

		return sitePage;
	}

	/**
	 * This method delets the page
	 * 
	 * @param toBeDeletedPageId
	 * @param siteName
	 * @throws AppException
	 */
	public void deletePage(String toBeDeletedPageId, String siteName)
			throws AppException {
		this.delete(getEntryId(toBeDeletedPageId), siteName);
	}

	public SitePage retrievePageById(String id, String siteName,
			EntryType entryType, EntryContainerType entryContainerType)
			throws AppException {

		List<BaseContentEntry> entryList = retrieveAllPages(siteName,
				entryType, entryContainerType);

		for (BaseContentEntry<?> entryPage : entryList) {
			if (entryPage.getId().equals(id)) {
				SitePage sitePage = GoogleSitesUtil
						.convertBaseContentEntryPageToSitePage(entryPage);
				sitePage.setEntryContainerType(entryContainerType);
				sitePage.setEntryType(entryType);
				return sitePage;
			}
		}

		return null;
	}

	/**
	 * Retrieves All the site Pages for a particular entry type or entry
	 * container type
	 * 
	 * @param siteName
	 * @param entryType
	 * @param entryContainerType
	 * @return List of site pages if found else returns null
	 * @throws AppException
	 */
	public List<SitePage> retrieveAllSitePages(String siteName,
			EntryType entryType, EntryContainerType entryContainerType)
			throws AppException {
		List<BaseContentEntry> entryList = retrieveAllPages(siteName,
				entryType, entryContainerType);
		List<SitePage> sitePageList = new ArrayList<SitePage>();
		for (BaseContentEntry<?> entryPage : entryList) {
			SitePage sitePage = GoogleSitesUtil
					.convertBaseContentEntryPageToSitePage(entryPage);
			sitePage.setEntryContainerType(entryContainerType);
			sitePage.setEntryType(entryType);
			sitePageList.add(sitePage);
		}

		return sitePageList;
	}

	private List<BaseContentEntry> retrieveAllPages(String siteName,
			EntryType entryType, EntryContainerType entryContainerType)
			throws AppException {

		String kind = null;
		if (entryType != null) {
			if (entryType.equals(EntryType.ANNOUNCEMENT)) {
				kind = EntryType.ANNOUNCEMENT.toString();

			} else if (entryType.equals(EntryType.COMMENT)) {
				kind = EntryType.COMMENT.toString();
			} else if (entryType.equals(EntryType.LIST_ITEM)) {
				kind = EntryType.LIST_ITEM.toString();
			} else if (entryType.equals(EntryType.LIST_PAGE)) {
				kind = EntryType.LIST_PAGE.toString();
			} else if (entryType.equals(EntryType.WEB_PAGE)) {
				kind = EntryType.WEB_PAGE.toString();
			}
		}
		if (entryContainerType != null) {
			if (entryContainerType
					.equals(EntryContainerType.ANNOUNCEMENTS_PAGE)) {
				kind = EntryContainerType.ANNOUNCEMENTS_PAGE.toString();
			} else if (entryContainerType
					.equals(EntryContainerType.FILE_CABINET)) {
				kind = EntryContainerType.FILE_CABINET.toString();
			}
		}

		ContentFeed contentFeed;
		try {
			contentFeed = service.getFeed(new URL(getContentFeedUrl(siteName)
					+ "?kind=" + kind), ContentFeed.class);
		} catch (MalformedURLException mue) {
			String msg = "Cannot retrieve feed for the given url";
			throw new AppException(msg, mue);
		} catch (ServiceException se) {
			String msg = "Cannot retrieve feed for the given url";
			throw new AppException(msg, se);
		} catch (IOException io) {
			String msg = "Cannot retrieve feed for the given url";
			throw new AppException(msg, io);
		}

		return contentFeed.getEntries();
	}

	/**
	 * Creates a new (sub)page.
	 * 
	 * @param entryType
	 * @param entryContainerType
	 * @param title
	 * @param summary
	 * @param parent
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	private BaseContentEntry<?> createPage(EntryType entryType,
			EntryContainerType entryContainerType, String title, String parent,
			String siteName) throws AppException {

		BaseContentEntry<?> entry = null;
		if (entryType.equals(EntryType.ANNOUNCEMENT)) {
			entry = new AnnouncementEntry();
		} else if (entryContainerType
				.equals(EntryContainerType.ANNOUNCEMENTS_PAGE)) {
			entry = new AnnouncementsPageEntry();
		} else if (entryType.equals(EntryType.COMMENT)) {
			entry = new CommentEntry();
		} else if (entryContainerType.equals(EntryContainerType.FILE_CABINET)) {
			entry = new FileCabinetPageEntry();
		} else if (entryType.equals(EntryType.LIST_ITEM)) {
			entry = new ListItemEntry();
		} else if (entryType.equals(EntryType.LIST_PAGE)) {
			entry = new ListPageEntry();
		} else if (entryType.equals(EntryType.WEB_PAGE)) {
			entry = new WebPageEntry();
		} else {
			if (entryType.equals(EntryType.ATTACHMENT)
					|| entryType.equals(EntryType.WEB_ATTACHMENT)) {
				throw new AppException("Trying to create " + entryType
						+ ". Please use upload command instead.");
			} else {
				throw new AppException("Unknown kind '" + entryType + "'");
			}
		}

		entry.setTitle(new PlainTextConstruct(title));

		setContentBlob(entry);

		// Upload to a parent page?
		if (parent != null) {
			if (parent.lastIndexOf("/") == -1) {
				parent = getContentFeedUrl(siteName) + parent;
			}
			entry.addLink(SitesLink.Rel.PARENT, Link.Type.ATOM, parent);
		}

		BaseContentEntry<?> newEntry;
		try {
			newEntry = service.insert(new URL(getContentFeedUrl(siteName)),
					entry);
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		}

		return newEntry;
	}

	/**
	 * Downloads a file from the specified URL to disk.
	 * 
	 * @param downloadUrl
	 *            The full URL to download the file from.
	 * @param fullFilePath
	 *            The local path to save the file to on disk.
	 * @throws AppException
	 * @throws ServiceException
	 * @throws IOException
	 */
	private void downloadFile(String downloadUrl, String fullFilePath)
			throws AppException {

		MediaContent mc = new MediaContent();
		mc.setUri(downloadUrl);
		MediaSource ms;
		try {
			ms = service.getMedia(mc);
		} catch (IOException e) {
			String message = "Unable to retrieve Media Source";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve Media Source";
			log.error(message, e);
			throw new AppException(message, e);
		}

		InputStream inStream = null;
		FileOutputStream outStream = null;

		try {
			try {
				inStream = ms.getInputStream();
			} catch (IOException e) {
				String message = "Unable to retrieve Input Stream";
				log.error(message, e);
				throw new AppException(message, e);
			}
			try {
				outStream = new FileOutputStream(fullFilePath);
			} catch (IOException e) {
				String message = "Unable to retrieve Input Stream";
				log.error(message, e);
				throw new AppException(message, e);
			}

			int c;
			try {
				while ((c = inStream.read()) != -1) {
					outStream.write(c);
				}
			} catch (IOException e) {
				String message = "Unable to read Input Stream";
				log.error(message, e);
				throw new AppException(message, e);
			}
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					String message = "Unable to close Input Stream";
					log.error(message, e);
					throw new AppException(message, e);
				}
			}
			if (outStream != null) {
				try {
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
					String message = "Unable to retrieve Input Stream";
					log.error(message, e);
					throw new AppException(message, e);
				}
			}
		}
	}

	/**
	 * Downloads an attachment.
	 * 
	 * @param entry
	 *            The attachment (entry) to download.
	 * @param directory
	 *            Path to a local directory to download the attach to.
	 * @throws AppException
	 * @throws ServiceException
	 * @throws IOException
	 */
	public void downloadAttachment(Attachment entry, String directory)
			throws AppException {

		String url = entry.getUrl();
		downloadFile(url, directory + entry.getTitle());
	}

	/**
	 * Downloads an attachment.
	 * 
	 * @param siteName
	 * 
	 * @param entry
	 *            The attachment (entry) to download.
	 * @param entryId
	 *            The content entry id of the attachment (e.g. 1234567890)
	 * @param directory
	 * @throws ServiceException
	 * @throws IOException
	 * @throws AppException
	 */
	public void downloadAttachment(String siteName, String entryId,
			String directory) throws AppException {

		AttachmentEntry attachment;
		try {
			attachment = service.getEntry(new URL(getContentFeedUrl(siteName)
					+ entryId), AttachmentEntry.class);
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve attachments from class";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to retrieve attachments from class";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve attachments from class";
			log.error(message, e);
			throw new AppException(message, e);
		}
		String url = ((OutOfLineContent) attachment.getContent()).getUri();
		downloadFile(url, directory + attachment.getTitle().getPlainText());
	}

	/**
	 * Downloads all attachments from a Site and assumes they all have different
	 * names.
	 * 
	 * @param entry
	 *            The attachment (entry) to download.
	 * @param directory
	 *            Path to a local directory to download the attach to.
	 * @throws AppException
	 * @throws ServiceException
	 */
	public void downloadAllAttachments(String directory, String siteName)
			throws AppException {
		URL contentFeedUrl;
		try {
			contentFeedUrl = new URL(getContentFeedUrl(siteName) + "?kind="
					+ EntryType.ATTACHMENT);
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve aContent Feed Url";
			log.error(message, e);
			throw new AppException(message, e);
		}
		ContentFeed contentFeed = null;
		try {
			contentFeed = service.getFeed(contentFeedUrl, ContentFeed.class);
		} catch (IOException e) {
			String message = "Unable to retrieve aContent Feed Url";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve aContent Feed Url";
			log.error(message, e);
			throw new AppException(message, e);
		}

		for (AttachmentEntry entry : contentFeed
				.getEntries(AttachmentEntry.class)) {
			Attachment attachment = GoogleSitesUtil
					.convertAttachmentEntryToAttachment(entry);
			downloadAttachment(attachment, directory);
		}
	}

	/**
	 * Uploads an attachment for the given siteName
	 * 
	 * @param siteName
	 * @param fileByteArray
	 * @param fileName
	 * @param parentUrl
	 * @return
	 * @throws AppException
	 */
	public Attachment uploadAttachment(String siteName, byte[] fileByteArray,
			String fileName, String parentUrl) throws AppException {
		return uploadAttachment(siteName, fileByteArray, parentUrl, fileName,
				"");
	}

	/**
	 * Uploads Attachment
	 * 
	 * @param siteName
	 * @param fileByteArray
	 * @param parentLink
	 * @param title
	 * @param description
	 * @return
	 * @throws AppException
	 */
	public Attachment uploadAttachment(String siteName, byte[] fileByteArray,
			String parentLink, String title, String description)
			throws AppException {
		String fileMimeType = mediaTypes.getContentType(title);

		AttachmentEntry newAttachment = new AttachmentEntry();
		newAttachment.setMediaSource(new MediaByteArraySource(fileByteArray,
				fileMimeType));
		newAttachment.setTitle(new PlainTextConstruct(title));
		newAttachment.setSummary(new PlainTextConstruct(description));
		newAttachment.addLink(SitesLink.Rel.PARENT, Link.Type.ATOM, parentLink);

		AttachmentEntry newEntry;
		try {
			newEntry = service.insert(new URL(getContentFeedUrl(siteName)),
					newAttachment);
		}catch(InvalidEntryException e){
			String message = "Cannot upload empty file";
			log.error(message, e);
			throw new EmptyFileException(message, e);
		} catch (VersionConflictException e) {
			String message = "Unable to retrieve Content Feed Url";
			log.error(message, e);
			throw new UniqueValidationException(message, e);
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve Content Feed Url";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to retrieve aContent Feed Url";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve aContent Feed Url";
			log.error(message, e);
			throw new AppException(message, e);
		}

		Attachment attachment = GoogleSitesUtil
				.convertAttachmentEntryToAttachment(newEntry);
		return attachment;

	}

	/**
	 * Creates a web attachment under the selected file cabinet.
	 * 
	 * @param siteName
	 * 
	 * @param contentUrl
	 *            The full URL of the hosted file.
	 * @param filecabinetLink
	 *            is the link of the FileCabinet File cabinet to create the web
	 *            attachment on.
	 * @param title
	 *            A title for the attachment.
	 * @param description
	 *            A description for the attachment.
	 * @return The created web attachment.
	 * @throws AppException
	 * @throws ServiceException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public WebAttachment uploadWebAttachment(String siteName,
			String contentUrl, String filecabinetLink, String title,
			String description) throws AppException {
		MediaContent content = new MediaContent();
		content.setUri(contentUrl);

		WebAttachmentEntry webAttachment = new WebAttachmentEntry();
		webAttachment.setTitle(new PlainTextConstruct(title));
		webAttachment.setSummary(new PlainTextConstruct(description));
		webAttachment.setContent(content);
		webAttachment.addLink(SitesLink.Rel.PARENT, Link.Type.ATOM,
				filecabinetLink);

		WebAttachmentEntry newEntry;
		try {
			newEntry = service.insert(new URL(getContentFeedUrl(siteName)),
					webAttachment);
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve Content Feed Url";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to retrieve aContent Feed Url";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve aContent Feed Url";
			log.error(message, e);
			throw new AppException(message, e);
		}

		WebAttachment webAttach = GoogleSitesUtil
				.convertWebAttachmentEntryToWebAttachment(newEntry);

		return webAttach;
	}

	/**
	 * Updates an existing attachment's metadata.
	 * 
	 * @param attachment
	 * 
	 * @param newTitle
	 *            A new title for the attachment.
	 * @param newDescription
	 *            A new description for the attachment.
	 * @return The created attachment.
	 * @throws AppException
	 * @throws ServiceException
	 * @throws IOException
	 */
	public Attachment updateAttachment(Attachment attachment, String newTitle,
			String newDescription) throws AppException {

		attachment.setTitle(newTitle);
		attachment.setSummary(newDescription);

		return attachment;

	}

	/**
	 * Updates an attachment
	 * 
	 * @param attachmentId
	 * @param siteName
	 * @param fileByteArray
	 * @param newTitle
	 * @param newDescription
	 * @return Attachment
	 * @throws AppException
	 */
	public Attachment updateAttachment(String attachmentId, String siteName,
			byte[] fileByteArray, String newTitle, String newDescription)
			throws AppException {
		AttachmentEntry entry = retrieveAttachmentEntryById(attachmentId);

		String fileMimeType = mediaTypes.getContentType(newTitle);
		entry.setMediaSource(new MediaByteArraySource(fileByteArray,
				fileMimeType));
		if (newTitle != null) {
			entry.setTitle(new PlainTextConstruct(newTitle));
		}
		if (newDescription != null) {
			entry.setSummary(new PlainTextConstruct(newDescription));
		}

		AttachmentEntry newEntry;
		try {
			newEntry = entry.updateMedia(true);
		}catch(InvalidEntryException e){
			String message = "Cannot upload empty file";
			log.error(message, e);
			throw new EmptyFileException(message, e);
		} catch (VersionConflictException e) {
			String message = "Unable to retrieve Content Feed Url";
			log.error(message, e);
			throw new UniqueValidationException(message, e);
		}  catch (IOException e) {
			String message = "Unable to update Attachment";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to update Attachment";
			log.error(message, e);
			throw new AppException(message, e);
		}
		Attachment newAttachment = GoogleSitesUtil
				.convertAttachmentEntryToAttachment(newEntry);
		return newAttachment;
	}

	/**
	 * This method delete attachment with a particular id and siteName
	 * 
	 * @param attachmentId
	 * @param siteName
	 * @throws AppException
	 */
	public void deleteAttachment(String attachmentId, String siteName)
			throws AppException {
		delete(getEntryId(attachmentId), siteName);
	}

	/**
	 * 
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	private List<AttachmentEntry> retrieveAllAttachmentEntry(String siteName)
			throws AppException {

		ContentFeed contentFeed;
		try {
			contentFeed = service.getFeed(new URL(getContentFeedUrl(siteName)
					+ "?kind=" + EntryType.ATTACHMENT), ContentFeed.class);
		} catch (MalformedURLException mue) {
			String msg = "Cannot retrieve feed for the given url";
			throw new AppException(msg, mue);
		} catch (ServiceException se) {
			String msg = "Cannot retrieve feed for the given url";
			throw new AppException(msg, se);
		} catch (IOException io) {
			String msg = "Cannot retrieve feed for the given url";
			throw new AppException(msg, io);
		}
		List<AttachmentEntry> attachmentEntryList = contentFeed
				.getEntries(AttachmentEntry.class);

		return attachmentEntryList;
	}

	/**
	 * 
	 * @param id
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	private AttachmentEntry retrieveAttachmentEntryById(String id)
			throws AppException {
		/*
		 * List<AttachmentEntry> attachmentList =
		 * retrieveAllAttachmentEntry(siteName); for (AttachmentEntry
		 * attachmentEntry : attachmentList) { if
		 * (attachmentEntry.getId().equals(id)) { return attachmentEntry; } }
		 */

		// String siteName = (id.split("/"))[6];

		AttachmentEntry attachmentEntry = null;
		try {
			attachmentEntry = service.getEntry(new URL(id),
					AttachmentEntry.class);
			
		}catch (ResourceNotFoundException e) {
			String msg = "Unable to retrieve Site Resource";
			log.debug(msg);
		} catch (MalformedURLException e) {
			String msg = "Unable to retrieve AttachmentEntry";
			throw new AppException(msg, e);
		} catch (IOException e) {
			String msg = "Unable to retrieve AttachmentEntry";
			throw new AppException(msg, e);
		} catch (ServiceException e) {
			String msg = "Unable to retrieve AttachmentEntry";
			throw new AppException(msg, e);
		}
		return attachmentEntry;
	}

	/**
	 * This method retrieve the attachment by Id
	 * 
	 * @param id
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	public Attachment retrieveAttachmentById(String id) throws AppException {
		return GoogleSitesUtil
				.convertAttachmentEntryToAttachment(retrieveAttachmentEntryById(id));
	}

	/**
	 * Retrieves all attachments
	 * 
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	public List<Attachment> retrieveAllAttachments(String siteName)
			throws AppException {
		return GoogleSitesUtil
				.convertAttachmentEntryListToAttachmentList(retrieveAllAttachmentEntry(siteName));
	}

	/**
	 * Post Announcement
	 * 
	 * @param title
	 * @param summary
	 * @param content
	 * @param parentLink
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	public Announcement postAnnouncement(String title, String content,
			String parentLink, String siteName) throws AppException {

		AnnouncementEntry entry = new AnnouncementEntry();
		entry.setTitle(new PlainTextConstruct(title));
		setContentBlob(entry, content);

		entry.addLink(SitesLink.Rel.PARENT, Link.Type.ATOM, parentLink);

		AnnouncementEntry newEntry;
		try {
			newEntry = service.insert(new URL(getContentFeedUrl(siteName)),
					entry);
		} catch (MalformedURLException e) {
			String message = "Unable to post Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to post Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to post Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		}

		Announcement newAnnouncement = GoogleSitesUtil
				.convertAnnouncementEntryToAnnouncement(newEntry, siteName);

		return newAnnouncement;
	}

	/**
	 * 
	 * @param siteName
	 * @param kind
	 * @return
	 * @throws AppException
	 */
	private ContentFeed getContentFeed(String siteName, EntryType kind)
			throws AppException {
		ContentFeed contentFeed = null;
		try {

			ContentQuery query = new ContentQuery(new URL(this.scheme
					+ "sites.google.com/feeds/content/" + domain + "/"
					+ siteName));
			query.setKind(kind.toString());
			contentFeed = service.getFeed(query, ContentFeed.class);
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve Content feed";
			log.error(message, e);
			throw new AppException(message, e);
		}
		return contentFeed;

	}

	/**
	 * Delete the site
	 * 
	 * @param resourceId
	 * @throws AppException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public void delete(String resourceId, String siteName) throws AppException {
		try {
			service.delete(
					new URL(getContentFeedUrl(siteName)
							+ this.getEntryId(resourceId)), "*");
		} catch (MalformedURLException e) {
			String message = "Unable to delete Resource";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to delete Resource";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to delete Resource";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	private AclEntry addAclRole(AclRole role, AclScope scope,
			SiteEntry siteEntry) throws AppException {
		AclEntry aclEntry = new AclEntry();
		aclEntry.setRole(role);
		aclEntry.setScope(scope);

		Link aclLink = siteEntry.getLink(
				SitesAclFeedLink.Rel.ACCESS_CONTROL_LIST, Link.Type.ATOM);
		AclEntry newAclEntry;
		try {
			AclFeed aclFeed = service.getFeed(new URL(getAclFeedUrl(siteEntry
					.getSiteName().getValue())), AclFeed.class);
			List<AclEntry> aclEntryList = aclFeed.getEntries();
			for (Iterator<AclEntry> aclEntryIterator = aclEntryList.iterator(); aclEntryIterator
					.hasNext();) {
				AclEntry currentIteratorEntry = (AclEntry) aclEntryIterator
						.next();
				if (currentIteratorEntry.getRole().equals(role)
						&& currentIteratorEntry.getScope().equals(scope)) {
					return currentIteratorEntry;
				}
			}
			newAclEntry = service.insert(new URL(aclLink.getHref()), aclEntry);
		} catch (MalformedURLException e) {
			String message = "Unable to add new Acl Role";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to add new Acl Role";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to add new Acl Role";
			log.error(message, e);
			throw new AppException(message, e);
		}

		return newAclEntry;
	}

	/**
	 * 
	 * @param roleType
	 * @param aclEntryId
	 * @return
	 * @throws AppException
	 */
	public AclForSite updateAclForSite(GoogleSiteRoleType roleType,
			String aclEntryId, String siteName) throws AppException {
		AclEntry aclEntry = retrieveAclEntryById(aclEntryId, siteName);
		aclEntry.setRole(new AclRole(roleType.toString()));
		try {
			aclEntry.update();
			return GoogleSitesUtil.convertAclEntriesToAclForSites(aclEntry);
		} catch (IOException e) {
			String message = "Unable to update Acl Entry";
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to update Acl Entry";
			throw new AppException(message, e);
		}
	}

	private AclEntry retrieveAclEntryById(String aclEntryId, String siteName)
			throws AppException {
		AclEntry aclEntry = null;
		String feedUrl = getAclFeedUrl(siteName) + getEntryId(aclEntryId);
		try {
			aclEntry = service.getEntry(new URL(feedUrl), AclEntry.class);
		} catch (MalformedURLException e) {
			String msg = "Unable to obtain AclEntry";
			throw new AppException(msg, e);
		} catch (IOException e) {
			String msg = "Unable to obtain AclEntry";
			throw new AppException(msg, e);
		} catch (ServiceException e) {
			String msg = "Unable to obtain AclEntry";
			throw new AppException(msg, e);
		}
		return aclEntry;
	}

	/**
	 * Fetches and displays a Site's acl feed.
	 * 
	 * @throws AppException
	 */
	private AclFeed getAclFeed(String siteName) throws AppException {
		AclFeed aclFeed;
		try {
			aclFeed = service.getFeed(new URL(getAclFeedUrl(siteName)),
					AclFeed.class);
		} catch (MalformedURLException e) {
			String message = "Unable to retrieve Acl feed";
			// log.error(message, mue);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to retrieve Acl feed";
			// log.error(message, mue);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to retrieve Acl feed";
			// log.error(message, mue);
			throw new AppException(message, e);
		}

		return aclFeed;
	}

	/**
	 * 
	 * @param siteName
	 * @return commons-io-2.0.1.jar
	 * @throws AppException
	 */
	public List<AclForSite> getAclEntries(String siteName) throws AppException {
		AclFeed aclFeed = getAclFeed(siteName);
		List<AclForSite> aclSitesList = new ArrayList<AclForSite>();
		for (AclEntry entry : aclFeed.getEntries()) {

			AclForSite aclForSites = GoogleSitesUtil
					.convertAclEntriesToAclForSites(entry);
			aclSitesList.add(aclForSites);
		}
		return aclSitesList;
	}

	/**
	 * Add user role
	 * 
	 * @param roleType
	 * 
	 * @param siteEntry
	 * @param username
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	public AclForSite addUserRole(GoogleSiteRoleType roleType, String username,
			String siteName) throws AppException {
		SiteEntry siteEntry = getSiteEntry(siteName);
		AclRole aclRole = new AclRole(roleType.toString());
		AclScope scope = new AclScope(AclScope.Type.USER, username + "@"
				+ domain);
		AclEntry aclEntry = addAclRole(aclRole, scope, siteEntry);

		AclForSite aclForSites = GoogleSitesUtil
				.convertAclEntriesToAclForSites(aclEntry);
		return aclForSites;

	}

	/**
	 * Add role to group
	 * 
	 * @param roleType
	 * 
	 * @param groupname
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	public AclForSite addGroupRole(GoogleSiteRoleType roleType,
			UserRoleType group, String siteName) throws AppException {
		SiteEntry siteEntry = getSiteEntry(siteName);

		AclRole aclRole = new AclRole(roleType.toString());
		AclScope scope = new AclScope(AclScope.Type.GROUP, group.toString()
				+ "@" + domain);
		AclEntry aclEntry = addAclRole(aclRole, scope, siteEntry);
		AclForSite aclForSites = GoogleSitesUtil
				.convertAclEntriesToAclForSites(aclEntry);
		return aclForSites;

	}

	/**
	 * Add role to doamin
	 * 
	 * @param roleType
	 * 
	 * @param groupname
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	public AclForSite addDomainRole(GoogleSiteRoleType roleType, String siteName)
			throws AppException {
		AclForSite aclForSites = null;
		SiteEntry siteEntry = null;
		try {
			siteEntry = getSiteEntry(siteName);
		} catch (Exception e) {
			System.out.println("Exception caught");
		}
		if (siteEntry != null) {
			AclRole aclRole = new AclRole(roleType.toString());
			AclScope scope = new AclScope(AclScope.Type.DOMAIN, domain);
			AclEntry aclEntry = addAclRole(aclRole, scope, siteEntry);
			aclForSites = GoogleSitesUtil
					.convertAclEntriesToAclForSites(aclEntry);
		}
		return aclForSites;

	}

	/**
	 * This method returns the site Object from the site name
	 * 
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	public Site getSiteFromSiteName(String siteName) throws AppException {
		return GoogleSitesUtil.convertSiteEntryToSite(getSiteEntry(siteName));
	}

	/**
	 * Fetches and displays the user's site feed.
	 * 
	 * @throws AppException
	 * @throws ServiceException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	private SiteEntry getSiteEntry(String siteName) throws AppException {
		SiteEntry entry;
		try {
			entry = service.getEntry(new URL(getSiteFeedUrl() + siteName),
					SiteEntry.class);
		} catch (MalformedURLException mue) {
			String msg = "error while fetching siteQuery";
			throw new AppException(msg, mue);
		} catch (IOException io) {
			String msg = "error while fetching siteQuery";
			throw new AppException(msg, io);
		} catch (ServiceException se) {
			String msg = "error while fetching siteQuery";
			throw new AppException(msg, se);
		}
		return entry;
	}

	/**
	 * Copies announcement
	 * 
	 * @param entry
	 * @param destinationContainerLink
	 * @param destinationContainer
	 * @param destinationSiteName
	 * @return copied Announcement Object
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ServiceException
	 * @throws AppException
	 */
	public Announcement copyAnnouncement(Announcement entry,
			String destinationContainerLink, String destinationSiteName)
			throws AppException {
		Announcement newEntry = postAnnouncement(entry.getTitle(),
				entry.getContent(), destinationContainerLink,
				destinationSiteName);
		return newEntry;

	}

	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	@Override
	public Site getSiteFromClassName(String name) throws AppException {
		PercentEscaper SLUG_ESCAPER = new PercentEscaper(
				" !\"#$&'()*+,-./:;<=>?@[\\]^_`{|}~", false);
		String escapedText = SLUG_ESCAPER.escape(name);
		String nowhitespace = WHITESPACE.matcher(escapedText).replaceAll("-");
		String lowerCase = nowhitespace.toLowerCase();
		Site siteFound = null;
		try {
			siteFound = getSiteFromSiteName(lowerCase);
		} catch (AppException ex) {
		//	log.error(ex);
		}
		return siteFound;
	}

	@Override
	public String updateAnnouncement(String title, String content,
			String parentLink, String siteName,String announcementEntryId) throws AppException {
		AnnouncementEntry entry=new AnnouncementEntry();
		AnnouncementEntry newEntry;
		try {
				String url = getContentFeedUrl(siteName)+announcementEntryId;
				entry=service.getEntry(new URL(url), AnnouncementEntry.class);
				entry.setTitle(new PlainTextConstruct(title));
				setContentBlob(entry, content);

				entry.addLink(SitesLink.Rel.PARENT, Link.Type.ATOM, parentLink);

				newEntry = service.update(new URL(url),
					entry);
		} catch (MalformedURLException e) {
			String message = "Unable to update Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to update Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to update Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		}

		//Announcement newAnnouncement = GoogleSitesUtil
				//.convertAnnouncementEntryToAnnouncement(newEntry, siteName);

		return getEntryId(newEntry);
	}
	
	/**
	 * Post Announcement
	 * 
	 * @param title
	 * @param summary
	 * @param content
	 * @param parentLink
	 * @param siteName
	 * @return
	 * @throws AppException
	 */
	public String postAnnouncementOnClassSite(String title, String content,
			String parentLink, String siteName) throws AppException {

		AnnouncementEntry entry = new AnnouncementEntry();
		entry.setTitle(new PlainTextConstruct(title));
		setContentBlob(entry, content);

		entry.addLink(SitesLink.Rel.PARENT, Link.Type.ATOM, parentLink);
		
		AnnouncementEntry newEntry;
		
		try {
			newEntry = service.insert(new URL(getContentFeedUrl(siteName)),
					entry);
		} catch (MalformedURLException e) {
			String message = "Unable to post Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to post Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to post Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		}

		return getEntryId(newEntry);
	}

	@Override
	public void deleteAnnouncement(String siteName, String announcementEntryId)
			throws AppException {
		try {
				String url = getContentFeedUrl(siteName)+announcementEntryId;
				AnnouncementEntry entry=service.getEntry(new URL(url), AnnouncementEntry.class);
				entry.delete();
			//	service.delete(new URL(url));
		} catch (MalformedURLException e) {
			String message = "Unable to update Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to update Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to update Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	@Override
	public String getAnnouncementLink(String siteName,
			String announcementEntryId) throws AppException {
		String url= getContentFeedUrl(siteName)+announcementEntryId;
		AnnouncementEntry entry;
		try{
			entry=service.getEntry(new URL(url), AnnouncementEntry.class);
		} catch (MalformedURLException e) {
			String message = "Unable to update Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to update Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to update Announcement";
			log.error(message, e);
			throw new AppException(message, e);
		}
		
		return entry.getHtmlLink().getHref();
	}

	@Override
	public String getAnnouncementParentLink(String siteName,
			String announcementEntryId) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}
}