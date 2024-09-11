package info.kgeorgiy.ja.tarasevich.implementor;

import static info.kgeorgiy.ja.tarasevich.implementor.Constants.*;

/**
 * The ClassHeader class is responsible for creating the class header
 *
 * @author Nikita Tarasevich
 */
public class ClassHeader {
    /**
     * Private field token of type {@link Class}
     */
    private final Class<?> token;

    /**
     * Constructor
     *
     * @param token {@link Class} of the implemented interface type
     */
    public ClassHeader(Class<?> token) {
        this.token = token;
    }

    /**
     * Returns the generated class name.
     *
     * @return {@link String}
     */
    public String getClassName() {
        return token.getSimpleName() + CLASS_SUFFIX;
    }

    /**
     * Returns the generated class header.
     *
     * @return {@link String}
     */
    @Override
    public String toString() {
        return String.join(SPACE,
                PUBLIC,
                CLASS,
                token.getSimpleName() + CLASS_SUFFIX,
                IMPLEMENTS,
                token.getCanonicalName());
    }
}
