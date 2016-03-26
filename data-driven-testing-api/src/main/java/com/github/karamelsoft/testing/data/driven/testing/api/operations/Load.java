package com.github.karamelsoft.testing.data.driven.testing.api.operations;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface used to load a value of a given type T from a given {@link InputStream}.
 *
 * @param <O> is the given type of the result.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
@FunctionalInterface
public interface Load<O> {

    /**
     * Loads a value of the given type O from a given {@link InputStream}.
     *
     * @param input is the given {@link InputStream} which the value will be load from.
     *
     * @return the result of the loading.
     *
     * @throws IOException
     */
    O load(InputStream input) throws IOException;
}
