package com.github.karamelsoft.testing.data.driven.testing.api.builders;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;

/**
 * Internal Interface used to add operation load inside a DSL.
 *
 * @param <O> is the return type of the given {@link Load} operation.
 * @param <R> is the return type of the operation.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface LoadBuilder<O, R> {

    /**
     * Defines a load operation.
     * @param load is the strategy to execute.
     * @return the given type R
     */
    R load(Load<O> load);
}
