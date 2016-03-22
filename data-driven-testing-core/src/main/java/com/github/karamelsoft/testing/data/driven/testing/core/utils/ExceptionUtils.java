package com.github.karamelsoft.testing.data.driven.testing.core.utils;

import com.github.karamelsoft.testing.data.driven.testing.api.RuntimeIOException;

import java.io.IOException;

/**
 * Created by frederic on 22/03/16.
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
