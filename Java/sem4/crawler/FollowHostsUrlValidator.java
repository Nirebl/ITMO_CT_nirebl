package info.kgeorgiy.ja.tarasevich.crawler;

import info.kgeorgiy.java.advanced.crawler.URLUtils;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validator for Hosts, declines hosts that not in the list
 */
class FollowHostsUrlValidator implements UrlValidator {
    private final Set<String> hosts = new HashSet<>();

    /**
     * Constructor
     *
     * @param hosts list of allowed host
     */
    public FollowHostsUrlValidator(List<String> hosts) {
        this.hosts.addAll(hosts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUrlNotAcceptable(String url) {
        try {
            var host = URLUtils.getHost(url);
            return hosts.stream().noneMatch(host::equals);
        } catch (MalformedURLException ignored) {
            return true;
        }
    }
}
