package info.kgeorgiy.ja.tarasevich.crawler;

/**
 * When there is no URL validation rule, this class is used to accept all URLs.
 */
class NoRuleUrlValidator implements UrlValidator {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUrlNotAcceptable(String url) {
        return false;
    }
}
