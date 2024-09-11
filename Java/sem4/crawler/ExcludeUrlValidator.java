package info.kgeorgiy.ja.tarasevich.crawler;

import java.util.Set;

/**
 * Validator that exclude urls from excludes set
 */
class ExcludeUrlValidator implements UrlValidator {
    private final Set<String> excludes;

    /**
     * Constructor
     * @param excludes a set of excluded urls
     */
    public ExcludeUrlValidator(Set<String> excludes) {
        this.excludes = excludes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUrlNotAcceptable(String url) {
        return excludes.stream().anyMatch(url::contains);
    }
}
