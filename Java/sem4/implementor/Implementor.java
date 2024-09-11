package info.kgeorgiy.ja.tarasevich.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.jar.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static info.kgeorgiy.ja.tarasevich.implementor.Constants.*;

/**
 * Implementor of classes from interface
 *
 * @author Nikita Tarasevich
 * @see info.kgeorgiy.java.advanced.implementor.Impler
 * @see info.kgeorgiy.java.advanced.implementor.JarImpler
 *
 */
public class Implementor implements Impler, JarImpler {
    /**
     * Default constructor
     */
    public Implementor(){

    }

    /**
     * Function called from the console with 2 arguments
     *
     * @param args { @link String}: interface type and file name
     */
    public static void main(String[] args) {
        if (args.length < 2 || args[0] == null || args[1] == null) {
            System.out.println("Please enter two args: name and file");
            return;
        }
        var implementor = new Implementor();
        try {
            implementor.implementJar(ClassLoader.getSystemClassLoader().loadClass(args[0]), Path.of(args[1]));
        } catch (ClassNotFoundException | ImplerException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Generates a class from the interface and saves it to text file to the root directory
     * {@inheritDoc}
     * @param token type token to create implementation for.
     * @param root  root directory.
     * @throws ImplerException if errors are occurred
     *
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        saveGeneratedClass(ClassGenerator.createFromType(token), root);
    }

    /**
     * Generates a class from the interface and saves it to jar file to the root directory
     * {@inheritDoc}
     * @param token   type token to create implementation for.
     * @param jarFile target <var>.jar</var> file.
     * @throws ImplerException if errors are occured
     *
     */
    // :NOTE: inherit doc
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        createDirectories(jarFile);
        Path temp = null;
        try {
            temp = Files.createTempDirectory(jarFile.toAbsolutePath().getParent(), "temp");
            var classGenerator =  ClassGenerator.createFromType(token);
            saveGeneratedClass(classGenerator, temp);
            compileClass(classGenerator, temp);
            createJar(classGenerator, temp, jarFile);
        } catch (IOException e) {
            throw new ImplerException("Error while creating temp dir");
        }
    }

    /**
     * Compiles the class from { @link ClassGenerator}
     *
     * @param classGenerator { @link ClassGenerator} for compiling the class
     * @param root { @link Path} for saving the path
     * @throws ImplerException { @link ImplerException}
     */
    public void compileClass(ClassGenerator classGenerator, final Path root) throws ImplerException {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new ImplerException("Could not find java compiler, include tools.jar to classpath");
        }

        final String classPath = getClassPath(classGenerator.getToken());
        final String filePath = getClassFile(classGenerator, root).toString();

        final String[] args = Stream.of(filePath,
                        "-encoding", "UTF-8", "-cp", String.join(File.pathSeparator, root.toString(), classPath))
                .toArray(String[]::new);

        final int exitCode = compiler.run(null, null, null, args);
        if (exitCode != 0) {
            throw new ImplerException("Compiler exit code does not equals to 0: exitCode = " + exitCode);
        }
    }

    /**
     * Returns the class path
     *
     * @param token { @link Class} class token
     * @return { @link String} the class path
     * @throws ImplerException { @link ImplerException}
     */
    private String getClassPath(Class<?> token) throws ImplerException {
        try {
            return Path.of(token.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
        } catch (final URISyntaxException e) {
            throw new ImplerException("Could not convert token to URI: token = " + token.getSimpleName());
        }
    }

    /**
     * Gets class file
     *
     * @param classGenerator { @link ClassGenerator} file listing generator
     * @param root { @link Path} the path to which to save the file
     * @return { @link Path} class file
     * @throws ImplerException { @link ImplerException}
     */
    // :NOTE: add descr
    private Path getClassFile(ClassGenerator classGenerator, Path root) throws ImplerException {
        final Path result = root.resolve(getFullFileName(classGenerator));
        return createDirectories(result);
    }


    /**
     * Creates jar file
     *
     * @param classGenerator { @link ClassGenerator} file listing generator
     * @param temp { @link Path} the temporary folder
     * @param jarFile { @link Path} the jar file name
     * @throws ImplerException { @link ImplerException}
     */
    private void createJar(ClassGenerator classGenerator, Path temp, Path jarFile) throws ImplerException {
        final Manifest manifest = new Manifest();
        final Attributes attributes = manifest.getMainAttributes();
        attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");


        try (final JarOutputStream output = new JarOutputStream(Files.newOutputStream(jarFile), manifest)) {
            final String clazzName = String.format(
                    "%s/%s.class",
                    classGenerator.getPackageHeader().getPackageName().replace('.', '/'),
                    classGenerator.getClassName()
            );
            output.putNextEntry(new JarEntry(clazzName));
            Files.copy(Paths.get(temp.toString(), clazzName), output);
        } catch (IOException e) {
            throw new ImplerException("Exception while writing to jar");
        }
    }

    /**
     * Creates the folder
     *
     * @param path { @link Path} folder path
     * @return { @link Path} the created folder
     * @throws ImplerException { @link ImplerException}
     */
    private Path createDirectories(final Path path) throws ImplerException {
        if (path.getParent() != null) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new ImplerException("Exception while creating parent directory");
            }
        }

        return path;
    }

    /**
     * Saves the generated listing to file
     *
     * @param classGenerator { @link ClassGenerator} file listing generator
     * @param root { @link Path} the folder to save the listing to
     * @throws ImplerException { @link ImplerException}
     */
    private void saveGeneratedClass(ClassGenerator classGenerator, Path root) throws ImplerException {
        try {
            var file = getClassFile(classGenerator, root);

            if (!Files.exists(file)) {
                Files.createFile(file);
            }
            try (BufferedWriter bw = Files.newBufferedWriter(file)) {
                bw.write(toUnicode(classGenerator.toString()));
            }
        } catch (IOException e) {
            throw new ImplerException(String.format("Internal error: %s", e.getMessage()));
        }
    }

    /**
     * Returns the full file name
     *
     * @param classGenerator { @link ClassGenerator} file listing generator
     * @return { @link Path} the full file name
     */
    private Path getFullFileName(ClassGenerator classGenerator) {
        return Path.of(classGenerator.getPackageHeader().getPackageName().replace('.', FILE_SEPARATOR))
                .resolve(classGenerator.getClassName() + JAVA_FILE_EXTENSION);
    }


    /**
     * Converts string to Unicode format
     *
     * @param str { @link String} the source string
     * @return { @link String} the string in Unicode
     */
    private String toUnicode(String str) {
        return str.chars()
                .mapToObj(c -> c >= 128 ? String.format("\\u%04X", c) : String.valueOf((char) c))
                .collect(Collectors.joining());
    }
}