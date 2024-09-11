package info.kgeorgiy.ja.tarasevich.implementor;

import static info.kgeorgiy.ja.tarasevich.implementor.Constants.*;

/**
 * The PackageHeader class is responsible for creating the package header
 *
 * @author Nikita Tarasevich
 */
public final class PackageHeader {
    /**
     * Private field token of type {@link Class}
     */
    private final Class<?> token;

    /**
     * Constructor
     *
     * @param token {@link Class} of the implemented interface type
     */
    public PackageHeader(Class<?> token) {
        this.token = token;
    }

    /**
     * Returns the package header.
     *
     * @return {@link String} package header
     */
    public String getPackageName() {
        return token.getPackageName();
    }

    /**
     * Returns the generated package header.
     *
     * @return {@link String}
     */
    @Override
    public String toString() {
        String classPackage = token.getPackageName();

        return classPackage.isEmpty() ? EMPTY_STRING : String.join(SPACE, PACKAGE, classPackage, SEMICOLON);
    }
}
