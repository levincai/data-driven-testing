package com.github.karamelsoft.testing.data.driven.testing.api.builders;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;

/**
 * Internal Interface used to add operation save inside a DSL.
 *
 * @param <I> is the input type of the given {@link Save} operation.
 * @param <R> is the return type of the operation.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface SaveBuilder<I, R> {

    /**
     * Defines a save operation.
     * @param save is the strategy to execute.
     * @return
     */
    R save(Save<I> save);
}
