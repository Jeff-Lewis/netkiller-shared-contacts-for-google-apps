package com.netkiller.view.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.netkiller.util.AppLogger;

public class UrlGeneratorCustomTag extends TagSupport {
	
	private static final AppLogger log = AppLogger
	.getLogger(UrlGeneratorCustomTag.class);
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public int doStartTag() throws JspException {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		String generatedUrl = blobstoreService.createUploadUrl(url);
		JspWriter out = pageContext.getOut();
		try {
			out.print(generatedUrl);
		} catch (IOException e) {
			String message = "Unable to processs custom JSP tag";
			log.error(message);
			JspException jspexception = new JspException(e);
			throw jspexception;
		}
		return (SKIP_BODY);
	}

}
