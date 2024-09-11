package info.kgeorgiy.ja.tarasevich.implementor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static info.kgeorgiy.ja.tarasevich.implementor.Constants.*;

/**
 * The ClassMethod class is responsible for creating the method with body
 *
 * @author Nikita Tarasevich
 */
public class ClassMethod {
    /**
     * Private field argTypes of method arguments of type {@link List}
     */
    final List<String> argTypes;

    /**
     * Private field arsHash
     */
    final int arsHash;

    /**
     * Private field method
     */
    private final Method method;

    /**
     * Constructor
     *
     * @param method {@link Method} of the implemented method
     */
    public ClassMethod(Method method) {
        this.argTypes = Arrays.stream(method.getParameterTypes())
                .map(Class::getCanonicalName)
                .toList();
        this.arsHash = argTypes.hashCode();
        this.method = method;
    }

    /**
     * Returns Method of the implemented method
     *
     * @return {@link Method}
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Calculates hash
     *
     * @return {@link int}
     */
    @Override
    public int hashCode() {
        return arsHash * method.getName().hashCode();
    }

    /**
     * {@link ClassMethod} equal to another ClassMethod
     *
     * @param other other {@link Object}
     * @return true if this instance equals to other {@link ClassMethod}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof ClassMethod otherMethod) {
            if (this.hashCode() != other.hashCode())
                return false;
            return this.method.getName().equals(otherMethod.method.getName())
                    && otherMethod.argTypes.equals(this.argTypes);
        }
        return false;
    }

    /**
     * Returns a string default value of the type
     *
     * @param clazz clazz {@Link Class}
     * @return {@Link String} default value
     */
    private static String getDefaultValue(Class<?> clazz) {
        return clazz != void.class ? clazz == boolean.class ? "false" : !clazz.isPrimitive() ? "null" : "0" : "";
    }

    /**
     * Returns method parameters generated in the string
     *
     * @param method method {@Link Method}
     * @return  {@Link String} method parameters
     */
    private static String getParameters(Method method) {
        return Arrays.stream(method.getParameters())
                .map(parameter -> String.join(SPACE, parameter.getType().getCanonicalName(), parameter.getName()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Returns the generated method with body.
     *
     * @return {@link String}
     */
    @Override
    public String toString() {
        return String.join(SPACE,
                PUBLIC,
                method.getReturnType().getCanonicalName(),
                method.getName() + ROUND_OPEN_BRACKET + getParameters(method) + ROUND_CLOSE_BRACKET,
                CURLY_OPEN_BRACKET, NEW_LINE,
                "return", getDefaultValue(method.getReturnType()) + SEMICOLON, NEW_LINE,
                CURLY_CLOSE_BRACKET
        );
    }
}
