package com.netkiller.service.conversion.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.conversion.Conversion;
import com.google.appengine.api.conversion.ConversionResult;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.media.MediaByteArraySource;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.netkiller.core.AppException;
import com.netkiller.security.DomainConfig;
import com.netkiller.service.conversion.ConversionResource;
import com.netkiller.service.conversion.ConversionService;
import com.netkiller.service.conversion.ConversionType;

//@Component("DocumentConversionService")
public class DocumentConversionService implements ConversionService {
	public static final String APP_NAME = "metacube-iPathshala-1.1";
	private String domain;
	public DocsService service;
	@Autowired
	DomainConfig domainConfig;
	
	@Autowired
	public DocumentConversionService(DomainConfig domainConfig) throws AppException	{
		this.domain = domainConfig.getDomainName();
		this.service = new DocsService(APP_NAME);
		this.service.setProtocolVersion(DocsService.Versions.V2);
		login(domainConfig.getDomainAdminEmail(),
				domainConfig.getDomainAdminPassword());
	}
	private void client() throws AppException	{
		this.domain = domainConfig.getDomainName();
		this.service = new DocsService(APP_NAME);
		this.service.setProtocolVersion(DocsService.Versions.V2);
		login(domainConfig.getDomainAdminEmail(),
				domainConfig.getDomainAdminPassword());
	
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
			String msg = "Cannot login the user for Document Service";
			throw new AppException(msg, ae);
		}
	}

	@Override
	public void convertAndWrite(ConversionType srcType, ConversionType destType, byte[] input,
			OutputStream outputStream, Map<String, ConversionResource> resources) throws AppException {
		try {
			client();
			outputStream.write(convert(srcType, destType, input, resources));
		} catch (IOException e) {
			throw new AppException("Unable to convert and write ", e);
		}
		
	}

	@Override
	public byte[] convert(ConversionType srcType, ConversionType destType, byte[] input,
			Map<String, ConversionResource> resources) throws AppException {
		DocumentListEntry entry;
		byte[] data  = null;
		try {
			entry = createNewDocument(RandomStringUtils.randomAlphanumeric(3),new String(input));
			 try {
				data = IOUtils.toByteArray(getPdfInputStream(this.service,entry));
			} catch (IOException e) {
				throw new AppException("Unable to convert ", e);
			}
		} catch (ServiceException e) {
			throw new AppException("Unable to convert ", e);
		}
		
	
		return data;
	}

	@Override
	public List<byte[]> convert(ConversionType srcType, ConversionType destType, List<byte[]> inputList,
			Map<String, ConversionResource> resources) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<byte[]> convertAsync(ConversionType srcType, ConversionType destType, byte[] input,
			Map<String, ConversionResource> resources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<List<byte[]>> convertAsync(ConversionType srcType, ConversionType destType, List<byte[]> input,
			Map<String, ConversionResource> resources) {
		// TODO Auto-generated method stub
		return null;
	}

	private DocumentListEntry fetchDoc(String rresourceId, DocsService client) throws IOException, ServiceException {
		  URL feedUri = new URL("https://docs.google.com/feeds/documents/private/full/rresourceId");
		  DocumentListFeed feed = client.getFeed(feedUri, DocumentListFeed.class);
		  return feed.getEntries().get(0);
		 
		}
	
	private DocumentListEntry createNewDocument(String title, String content) throws ServiceException {
		try {
			
			URL feedUrl = new URL("https://docs.google.com/feeds/documents/private/full/");
			DocumentListEntry newEntry = new DocumentEntry();
			newEntry.setTitle(new PlainTextConstruct(title));
			newEntry =this.service.insert(feedUrl, newEntry);
			this.service.getRequestFactory().setHeader("If-Match", "*");
			newEntry.setMediaSource(new MediaByteArraySource(content.getBytes(), "text/html"));
			DocumentListEntry newEntry1 = newEntry.updateMedia(false);
			return newEntry1;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	private InputStream getPdfInputStream(DocsService client,DocumentListEntry document) throws ServiceException {
		
		String exportUrl = ((MediaContent)document.getContent()).getUri() + "&exportFormat=pdf";
		System.out.println("Export Url"+exportUrl);
		MediaContent mc = new MediaContent();
		mc.setUri(exportUrl);
		try {
			MediaSource ms = client.getMedia(mc);
			return ms.getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
