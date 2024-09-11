package info.kgeorgiy.ja.tarasevich.crawler;

/**
 * Validates url
 */
interface UrlValidator {
    /**
     * @param url current url
     * @return checks that url is acceptable
     */
    boolean isUrlNotAcceptable(String url);
}
