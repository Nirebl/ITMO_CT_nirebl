package info.kgeorgiy.ja.tarasevich.implementor;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static info.kgeorgiy.ja.tarasevich.implementor.Constants.*;

/**
 * The ClassMethods class is responsible for creating class methods
 *
 * @author Nikita Tarasevich
 */
public final class ClassMethods {
    /**
     * Available methods in a class
     */
    private final List<ClassMethod> methods;

    /**
     * Constructor
     *
     * @param token {@link Class} of the implemented class methods
     */
    public ClassMethods(Class<?> token) {
        this.methods = Stream.concat(
                        Arrays.stream(token.getMethods()).map(ClassMethod::new),
                        getAllParents(token).stream()
                                .map(clazz -> Arrays.asList(clazz.getDeclaredMethods()))
                                .flatMap(List::stream)
                                .filter(m -> {
                                    int modifiers = m.getModifiers();
                                    return !Modifier.isPrivate(modifiers) && !Modifier.isPublic(modifiers);
                                }).map(ClassMethod::new))
                .distinct()
                .filter(m -> Modifier.isAbstract(m.getMethod().getModifiers())).toList();
    }

    /**
     * Returns all token types
     *
     * @param token {@link Class} of the type
     *
     * @return {@Link List} list of all parents
     */
    private static List<Class<?>> getAllParents(Class<?> token) {
        var parents = new ArrayList<>(Arrays.asList(token.getInterfaces()));
        while (token != null) {
            parents.add(token);
            token = token.getSuperclass();
        }
        return parents;
    }

    /**
     * Returns the generated methods.
     *
     * @return {@link String}
     */
    @Override
    public String toString() {
        return methods.stream().map(ClassMethod::toString).collect(Collectors.joining(NEW_LINE));
    }

}
