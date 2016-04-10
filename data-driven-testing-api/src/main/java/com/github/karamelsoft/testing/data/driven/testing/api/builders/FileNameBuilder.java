package com.github.karamelsoft.testing.data.driven.testing.api.builders;

/**
 * Internal Interface used to add operation fileName inside a DSL.
 *
 * @param <R> is the return type of the operation
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface FileNameBuilder<R> {

    /**
     * Defines a fileName operation.
     * @param fileName to use.
     * @return the given type R.
     */
    R fileName(String fileName);
}
