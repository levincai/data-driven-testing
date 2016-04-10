package com.github.karamelsoft.testing.data.driven.testing.api.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;

/**
 * Interface used to define a test script.
 *
 * @param <T> is the type of the {@link Tester} this scenario will be applied to.
 * @param <U> is the type of the {@link Tester} this scenario will become.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
@FunctionalInterface
public interface Script<T, U> {

    /**
     * Method used to define script operations.
     * @param tester to use.
     * @return a new instance of {@link Tester}
     */
    Tester<U> execute(Tester<T> tester);
}
