package com.github.karamelsoft.testing.data.driven.testing.api;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.*;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Interface used to define a Tester.
 *
 * @param <T> type of the Tester value.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface Tester<T> {

    /**
     * Set the value that will be used as the input value for other actions.
     * @param <U> new Tester value type.
     * @param value to set.
     * @return new Tester instance.
     */
    <U> Tester<U> value(U value);

    /**
     * Load a file with a given @{Load} and assign the result to the
     * test value.
     * @param <U> new Tester value type.
     * @param fileName to load.
     * @param strategy used to load the given fileName.
     * @return new Tester instance.
     */
    <U> Tester<U> load(String fileName, Load<U> strategy);

    /**
     * Map the current value with with given @{Function} or set the given default value.
     * @param <U> new Tester value type.
     * @param function used to map the current Tester value.
     * @return new Tester instance.
     */
    <U> Tester<U> map(Function<T, U> function);

    /**
     * Execute a given @{Consumer} for the current value.
     * @param consumer to apply on Tester value.
     * @return the current Tester instance
     */
    Tester<T> apply(Consumer<T> consumer);

    /**
     * Save the current value to file with a given @{Save}
     * @param fileName to save the current Tester value to.
     * @param strategy used to save current Tester value to the given fileName.
     * @return the current Tester instance.
     */
    Tester<T> save(String fileName, Save<T> strategy);

    /**
     * Compare resource file to actual file for a given file name with a given
     * {@link Comparison}.
     * @param fileName of the resource and actual file to compare.
     * @param strategy to use to do the comparison.
     * @return the current Tester instance.
     */
    Tester<T> compare(String fileName, Comparison strategy);

    /**
     * Execute a given runnable.
     * @param runnable to execute.
     * @return the current Tester instance.
     */
    default Tester<T> execute(Runnable runnable) {
        runnable.run();

        return this;
    }

    /**
     * Execute a given consumer on the current Tester.
     * @param script {@link Consumer}
     * @return current Tester instance.
     */
    Tester<T> script(Consumer<Tester<T>> script);

    /**
     * Execute a given script on the current Tester.
     * @param <U> new Tester value type.
     * @param script to execute.
     * @return new or current Tester instance.
     */
    <U> Tester<U> script(Script<T, U> script);

    /**
     * Execute a given scenario for each input file.
     * @param scenario to execute.
     * @return the current Tester instance.
     */
    Tester<T> scenario(Scenario<T> scenario);

    /**
     * Finish the test asserting that no errors happened
     */
    void end();
}
