package com.github.karamelsoft.testing.data.driven.testing.api;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Script;

/**
 * Interface used to define a tester.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface Tester {

    /**
     * Set the value that will be used as the input value for other actions.
     *
     * @param <U> new {@link ActiveTester} value type.
     * @param value to set.
     * @return new {@link ActiveTester} instance.
     */
    <U> ActiveTester<U> value(U value);

    /**
     * Load a file with a given @{Load} and assign the result to the
     * test value.
     * @param <U> new {@link ActiveTester} value type.
     * @param fileName to load.
     * @param strategy used to load the given fileName.
     * @return new {@link ActiveTester} instance.
     */
    <U> ActiveTester<U> load(String fileName, Load<U> strategy);

    /**
     * Finish the test asserting that no errors happened
     */
    void end();
}
