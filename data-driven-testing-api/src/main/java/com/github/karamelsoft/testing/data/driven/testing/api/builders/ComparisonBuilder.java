package com.github.karamelsoft.testing.data.driven.testing.api.builders;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Comparison;

/**
 * Internal Interface used to add operation comparison inside a DSL.
 *
 * @param <R> is the return type of the operation
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface ComparisonBuilder<R> {

    /**
     * Defines a comparison operation.
     * @param comparison to use.
     * @return the given type R.
     */
    R comparison(Comparison comparison);
}
