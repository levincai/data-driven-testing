package com.github.karamelsoft.testing.data.driven.testing.api;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Scenario;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Script;

import java.util.function.Consumer;

/**
 * Interface used to define an empty tester.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface InactiveTester extends Tester {

    /**
     * Execute a given runnable.
     * @param runnable to execute.
     * @return the current instance.
     */
    default InactiveTester execute(Runnable runnable) {
        runnable.run();

        return this;
    }

    /**
     * Execute a given consumer on the current InactiveTester.
     *
     * @param <U> new {@link ActiveTester} value type.
     * @param script to use.
     * @return new {@link ActiveTester} instance.
     */
    <U> ActiveTester<U> script(Script<InactiveTester, U> script);

    /**
     * Execute a given scenario for each input file.
     *
     * @param scenario to execute.
     * @return the current instance.
     */
    InactiveTester scenario(Scenario scenario);

    /**
     * Execute a given scenario for each folder.
     *
     * @param scenario {@link Consumer} to execute.
     * @return the current instance.
     */
    InactiveTester scenario(Consumer<InactiveTester> scenario);

    /**
     * Finish the test asserting that no errors happened
     */
    void end();
}
