package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.core.builders.ExpectExceptionBuilder;
import com.github.karamelsoft.testing.data.driven.testing.core.operations.ExpectException;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class CoreTester {

    /**
     * Factory method used to define the expected exception.
     *
     * @return an instance of {@link ExpectExceptionBuilder}
     */
    public static ExpectExceptionBuilder<ExpectException.Builder> expectException() {
        return ExpectException.newBuilder();
    }
}
