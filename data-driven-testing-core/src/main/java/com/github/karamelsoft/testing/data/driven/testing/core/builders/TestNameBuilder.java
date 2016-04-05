package com.github.karamelsoft.testing.data.driven.testing.core.builders;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface TestNameBuilder<O> {

    /**
     * Defines test name
     * @param testName
     * @return
     */
    O name(String testName);
}
