package com.github.karamelsoft.testing.data.driven.testing.api.operations;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface used to compare 2 different {@link InputStream}.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
@FunctionalInterface
public interface Comparison {

    /**
     * Compares 2 given {@link InputStream}.
     *
     * @param expected is the reference {@link InputStream}.
     * @param actual is the actual {@link InputStream}.

     * @return {@link Boolean#TRUE} if the 2 given {@link InputStream} are equivalent.
     *         {@link Boolean#FALSE} otherwise.

     * @throws IOException
     */
    Boolean equivalent(InputStream expected, InputStream actual) throws IOException;
}
