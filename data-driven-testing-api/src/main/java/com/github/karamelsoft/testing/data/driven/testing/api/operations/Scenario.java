package com.github.karamelsoft.testing.data.driven.testing.api.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;

/**
 * Interface used to define a test scenario.
 *
 * @param <T> is the type of the {@link Tester} this scenario will be applied to.
 */
public interface Scenario<T> {

    /**
     * Executes the scenario.
     *
     * @param fileName is the current scenario fileName.
     * @param tester is the {@link Tester} which this scenario will be applied on.
     */
    void execute(String fileName, Tester<T> tester);
}
