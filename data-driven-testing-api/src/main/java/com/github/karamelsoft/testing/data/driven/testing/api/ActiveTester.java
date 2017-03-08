package com.github.karamelsoft.testing.data.driven.testing.api;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.*;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Interface used to define a ActiveTester.
 *
 * @param <T> type of the ActiveTester value.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface ActiveTester<T> extends Tester {

    /**
     * Execute a given runnable.
     * @param runnable to execute.
     * @return the current ActiveTester instance.
     */
    default ActiveTester<T> execute(Runnable runnable) {
        runnable.run();

        return this;
    }

    /**
     * Map the current value with with given {@link Function} or set the given default value.
     *
     * @param <U> new ActiveTester value type.
     * @param function used to map the current ActiveTester value.
     * @return new ActiveTester instance.
     */
    <U> ActiveTester<U> map(Function<T, U> function);

    /**
     * Execute a given {@link Consumer} for the current value.
     *
     * @param consumer to apply on ActiveTester value.
     * @return the current ActiveTester instance
     */
    ActiveTester<T> apply(Consumer<T> consumer);

    /**
     * Save the current value to file with a given {@link Save}
     *
     * @param fileName to save the current ActiveTester value to.
     * @param strategy used to save current ActiveTester value to the given fileName.
     * @return the current ActiveTester instance.
     */
    ActiveTester<T> save(String fileName, Save<T> strategy);

    /**
     * Compare resource file to actual file for a given file name with a given
     * {@link Comparison}.
     *
     * @param fileName of the resource and actual file to compare.
     * @param strategy to use to do the comparison.
     * @return the current ActiveTester instance.
     */
    ActiveTester<T> compare(String fileName, Comparison strategy);

    /**
     * Execute a given consumer on the current ActiveTester.
     *
     * @param script {@link Consumer}
     * @return current ActiveTester instance.
     */
    ActiveTester<T> script(Consumer<ActiveTester<T>> script);

    /**
     * Execute a given script on the current ActiveTester.
     *
     * @param <U> new ActiveTester value type.
     * @param script to execute.
     * @return new or current ActiveTester instance.
     */
    <U> ActiveTester<U> script(Script<ActiveTester<T>, U> script);
}
