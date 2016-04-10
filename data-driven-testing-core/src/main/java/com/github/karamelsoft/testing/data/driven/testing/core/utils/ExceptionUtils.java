package com.github.karamelsoft.testing.data.driven.testing.core.utils;

import com.github.karamelsoft.testing.data.driven.testing.api.RuntimeIOException;

import java.io.IOException;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class ExceptionUtils {

    public static void toRuntime(final IOUnsafeExecution operation) {
        try {
            operation.execute();
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public static <O> O toRuntime(final IOUnsafeSupplier<O> operation) {
        try {
            return operation.execute();
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }
}
