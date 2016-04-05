package org.jsimple.data.driven.testing.xml.function;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

/**
 * Created by jschoreels on 05.04.16.
 */
public class TestUtils {

    public static final String RESOURCES = "src/test/resources/comparison";
    public static final String TARGET = "target/test/resources/comparison";

    static InputStream getInputStream(String folder, final String fileName) throws FileNotFoundException {
        return new FileInputStream(Paths.get(folder, fileName).toFile());
    }

    static OutputStream getOutputStream(String folder, final String fileName) throws FileNotFoundException {
        return new FileOutputStream(Paths.get(folder, fileName).toFile());
    }

}
