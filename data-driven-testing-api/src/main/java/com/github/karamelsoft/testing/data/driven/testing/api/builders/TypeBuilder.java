package com.github.karamelsoft.testing.data.driven.testing.api.builders;

/**
 * Internal Interface used to add operation type inside a DSL.
 *
 * @param <T> is the input {@link Class} type.
 * @param <R> is the return type of the operation.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface TypeBuilder<T, R> {

    /**
     * Method used to define a type.
     * @param type to define.
     * @return the generic type R.
     */
    R type(Class<T> type);
}
