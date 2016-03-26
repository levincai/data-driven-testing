package com.github.karamelsoft.testing.data.driven.testing.api.operations;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface used to save a value of a given type I to a given {@link OutputStream}.
 *
 * @param <I> is the given type to save.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
@FunctionalInterface
public interface Save<I> {

    /**
     * Saves a value of the given type I to the given {@link OutputStream}.
     *
     * @param value is the value to save.
     * @param output is the {@link OutputStream} which the value will be saved to.
     *
     * @throws IOException
     */
    void save(I value, OutputStream output) throws IOException;
}
