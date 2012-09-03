
package com.metacube.ipathshala.openid.appengine;

import com.google.step2.xmlsimplesign.TrustRootsProvider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.cert.X509Certificate;
import java.util.Collection;

public class AppEngineTrustsRootProvider implements TrustRootsProvider {

  private static final String CERT_FILE = "/cacerts.bin";

  private final Collection<X509Certificate> certs;

  @SuppressWarnings("unchecked")
  public AppEngineTrustsRootProvider() {

    try {
      ObjectInputStream in =
          new ObjectInputStream(AppEngineTrustsRootProvider.class.getResourceAsStream(CERT_FILE));
      certs = (Collection<X509Certificate>) in.readObject();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public Collection<X509Certificate> getTrustRoots() {
    return certs;
  }
}
