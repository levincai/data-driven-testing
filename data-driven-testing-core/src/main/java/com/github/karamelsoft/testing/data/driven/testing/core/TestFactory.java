package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.core.builders.TesterBuilder;

/**
 * Created by frederic on 25/04/15.
 */
public class TestFactory {

    public static <T> TesterBuilder<T> createTest() {
        return AbstractTester.createTest();
    }
}
