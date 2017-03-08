package com.github.karamelsoft.testing.data.driven.testing.api.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.InactiveTester;

/**
 * Interface used to define a test scenario.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
@FunctionalInterface
public interface Scenario {

    /**
     * Executes the scenario.
     *
     * @param fileName is the current scenario fileName.
     * @param tester is the {@link InactiveTester} which this scenario will be applied on.
     */
    void execute(String fileName, InactiveTester tester);
}
