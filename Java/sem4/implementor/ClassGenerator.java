package info.kgeorgiy.ja.tarasevich.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.lang.reflect.Modifier;

import static info.kgeorgiy.ja.tarasevich.implementor.Constants.*;

/**
 * The ClassGenerator class is responsible for creating listing parts
 *
 * @author Nikita Tarasevich
 */
public final class ClassGenerator {

    /**
     * Private field token of type {@link Class}
     */
    private final Class<?> token;

    /**
     * Private field packageHeader of type {@link PackageHeader}
     */
    private final PackageHeader packageHeader;

    /**
     * Private field classHeader of type {@link ClassHeader}
     */
    private final ClassHeader classHeader;

    /**
     * Private field methods of type {@link ClassMethods}
     */
    private final ClassMethods methods;

    /**
     * Constructor
     *
     * @param token {@link Class} of the implemented interface type
     */
    private ClassGenerator(Class<?> token) {
        this.token = token;
        this.packageHeader = new PackageHeader(token);
        this.classHeader = new ClassHeader(token);
        this.methods = new ClassMethods(token);
    }

    /**
     * The builder of the {@link ClassGenerator} instance
     *
     * @param token {@link Class} of the implemented interface type
     * @return instance of {@link ClassGenerator}
     * @throws ImplerException if the token is not a public interface type
     */
    public static ClassGenerator createFromType(Class<?> token) throws ImplerException {
        validateType(token);

        return new ClassGenerator(token);
    }

    /**
     * Validates that the token is a public interface type
     *
     * @param token {@link Class} of the implemented interface type
     * @throws ImplerException if the token is not a public interface type
     */
    public static void validateType(Class<?> token) throws ImplerException {
        if (!token.isInterface()) {
            throw new ImplerException("Can implement interface only. This type isn't interface");
        } else if (Modifier.isPrivate(token.getModifiers())) {
            throw new ImplerException("Can;t implement private interface");
        }
    }

    /**
     * Returns the current token
     *
     * @return {@link Class}
     */
    public Class<?> getToken() {
        return token;
    }

    /**
     * Returns the package header
     *
     * @return {@link PackageHeader}
     */
    public PackageHeader getPackageHeader(){
        return packageHeader;
    }

    /**
     * Returns the generated class name.
     *
     * @return {@link String}
     */
    public String getClassName() {
        return classHeader.getClassName();
    }

    /**
     * Returns the generated class listing.
     *
     * @return {@link String}
     */
    @Override
    public String toString() {
        return String.join(EMPTY_STRING,
                packageHeader.toString(), NEW_LINE,
                classHeader.toString(), CURLY_OPEN_BRACKET, NEW_LINE,
                methods.toString(), NEW_LINE,
                CURLY_CLOSE_BRACKET
        );
    }
}
