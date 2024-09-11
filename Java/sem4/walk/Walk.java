package info.kgeorgiy.ja.tarasevich.walk;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;


public class Walk {
    public static void main(final String[] args) {
        if (args == null) {
            System.out.println("Null arguments");
            return;
        }
        if (args.length != 2) {
            System.out.println("Invalid number of arguments");
            return;
        }
        if (args[0] == null || args[1] == null) {
            System.out.println("Invalid arguments");
            return;
        }
        try (final var reader = Files.newBufferedReader(Path.of(args[0]))) {
            if (Path.of(args[1]).getParent() != null) {
                Files.createDirectories(Path.of(args[1]).getParent());
            }
            try (final var writer = Files.newBufferedWriter(Path.of(args[1]))) {
                String fileline;
                while ((fileline = reader.readLine()) != null) {
                    try {
                        new WalkFileVisiter(writer).answerHashRequest(fileline);
                    } catch (InvalidPathException e) {
                        System.out.println("Invalid path: " + fileline);
                    }
                }
            } catch (InvalidPathException e) {
                System.out.println("Wrong path " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO Error while writing to file: " + e.getMessage());
            } catch (SecurityException e) {
                System.out.println("SecurityException while writing to file: " + e.getMessage());
            }
        } catch (NoSuchFileException e) {
            System.out.println("There is no file " + e.getMessage());
        } catch (InvalidPathException e) {
            System.out.println("Wrong path " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Error while reading file: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("SecurityException while writing to file: " + e.getMessage());
        }
    }

    private static class WalkFileVisiter {
        private final BufferedWriter writer;
        private static final int BUFFER_SIZE = 1024;
        private final byte[] buffer = new byte[BUFFER_SIZE];

        public WalkFileVisiter(final BufferedWriter writer) {
            this.writer = writer;
        }

        //Hashing
        private int getHash(final String filePath) {
            try (final var stream = new FileInputStream(filePath)) {
                int hash = 0;
                int length;
                while ((length = stream.read(buffer)) != -1) {
                    for (int i = 0; i < length; i++) {
                        hash += buffer[i] & 0xFF;
                        hash += hash << 10;
                        hash ^= hash >>> 6;
                    }
                }
                hash += hash << 3;
                hash ^= hash >>> 11;
                hash += hash << 15;
                return hash;
            } catch (SecurityException | IOException e) {
                return 0;
            }
        }

        //writing file hash in the output file
        public void answerHashRequest(final String filePath) {
            try {
                writer.write(String.format("%08x", getHash(filePath)) + " " + filePath);
                writer.newLine();
            } catch (SecurityException | IOException e) {
                System.out.println("Error writing hash of file " + filePath);
            }
        }
    }
}
