
package com.netkiller.openid;

import com.google.step2.discovery.HostMeta;
import com.google.step2.discovery.HostMetaException;
import com.google.step2.discovery.HostMetaFetcher;

/**
 * Simple fetcher to serially try different host-meta fetching strategies, returning the
 * first one to find data.
 */
public class SerialHostMetaFetcher implements com.google.step2.discovery.HostMetaFetcher {
    HostMetaFetcher[] fetchers;

    public SerialHostMetaFetcher(HostMetaFetcher ...fetchers) {
        this.fetchers = fetchers;
    }

    public HostMeta getHostMeta(String domain) throws HostMetaException {
        for (int i = 0; i < fetchers.length; ++i) {
            HostMeta info = fetchers[i].getHostMeta(domain);
            if (isValidHostMeta(info)) {
                return info;
            }
        }
        return null;
    }

    protected boolean isValidHostMeta(HostMeta info) {
        if (info != null && !info.getLinks().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
