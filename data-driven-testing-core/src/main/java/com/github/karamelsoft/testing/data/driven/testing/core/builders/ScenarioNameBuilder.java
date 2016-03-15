package com.github.karamelsoft.testing.data.driven.testing.core.builders;

/**
 * Created by frederic on 26/04/15.
 */
public interface ScenarioNameBuilder<O> {

    /**
     * Defines script name
     * @param scenarioName
     * @return
     */
    O scenario(String scenarioName);
}
