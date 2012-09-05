
package com.netkiller.openid.appengine;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.inject.Singleton;
import com.google.step2.http.FetchException;
import com.google.step2.http.FetchRequest;
import com.google.step2.http.FetchResponse;
import com.google.step2.http.HttpFetcher;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

@Singleton
public class AppEngineHttpFetcher implements HttpFetcher {

  private final URLFetchService fetchService;

  public AppEngineHttpFetcher() {
    fetchService = URLFetchServiceFactory.getURLFetchService();
  }

  public FetchResponse fetch(FetchRequest request) throws FetchException {

    HTTPMethod method;
    switch (request.getMethod()) {
      case POST:
        method = HTTPMethod.POST;
        break;
      case HEAD:
        method = HTTPMethod.HEAD;
        break;
      default:
        method = HTTPMethod.GET;
        break;
    }

    try {

      HTTPRequest httpRequest = new HTTPRequest(request.getUri().toURL(), method);
      HTTPResponse httpResponse = fetchService.fetch(httpRequest);
      return new AppEngineFetchResponse(httpResponse);

    } catch (MalformedURLException e) {
      throw new FetchException(e);
    } catch (IOException e) {
      throw new FetchException(e);
    }
  }

  private static class AppEngineFetchResponse implements FetchResponse {

    private final HTTPResponse httpResponse;

    public AppEngineFetchResponse(HTTPResponse httpResponse) {
      this.httpResponse = httpResponse;
    }

    public byte[] getContentAsBytes() {
      return httpResponse.getContent();
    }

    public InputStream getContentAsStream() {
      return new ByteArrayInputStream(getContentAsBytes());
    }

    public String getFirstHeader(String name) {
      List<HTTPHeader> headers = httpResponse.getHeaders();
      for (HTTPHeader header : headers) {
        if (header.getName().equalsIgnoreCase(name)) {
          return header.getValue();
        }
      }
      return null;
    }

    public int getStatusCode() {
      return httpResponse.getResponseCode();
    }
  }
}
