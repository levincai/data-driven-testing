package com.github.karamelsoft.testing.data.driven.testing.api;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.*;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by frederic on 23/04/15.
 */
public interface Tester<T> {

    /**
     * Set the value that will be used as the input value for other actions
     * @param value
     * @return the current Tester instance
     */
    <U> Tester<U> value(U value);

    /**
     * Load a file with a given @{Load} and assign the result to the
     * test value
     * @param fileName
     * @param strategy
     * @return the current Tester instance
     */
    <U> Tester<U> load(String fileName, Load<U> strategy);

    /**
     * Map the current value with with given @{Function} or set the given default value
     * @param <U>
     * @param function
     * @param function
     * @return the current Tester instance
     */
    <U> Tester<U> map(Function<T, U> function);

    /**
     * Execute a given @{Consumer} for the current value
     * @param consumer
     * @return the current Tester instance
     */
    Tester<T> apply(Consumer<T> consumer);

    /**
     * Save the current value to file with a given @{Save}
     * @param fileName
     * @param strategy
     * @return the current Tester instance
     */
    Tester<T> save(String fileName, Save<T> strategy);

    /**
     * Compare resource file to actual file for a given file name with a given
     * {@link Comparison}
     * @param fileName
     * @return the current Tester instance
     */
    Tester<T> compare(String fileName, Comparison strategy);

    /**
     * Execute a given runnable
     * @param runnable
     * @return the current Tester instance
     */
    default Tester<T> execute(Runnable runnable) {
        runnable.run();

        return this;
    }

    /**
     * Execute a given script on the current Tester
     * @param script
     * @return the current Tester instance
     */
    <U> Tester<U> script(Script<T, U> script);

    /**
     * Execute a given scenario for each input file
     * @return
     * @throws IOException
     */
    Tester<T> scenario(Scenario<T> scenario);

    /**
     * Finish the test asserting that no errors happened
     */
    void end();
}
