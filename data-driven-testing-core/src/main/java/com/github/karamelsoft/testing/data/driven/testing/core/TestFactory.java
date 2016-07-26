package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.TesterBuilder;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class TestFactory {

    /**
     * Factory method used to create a {@link Tester}.
     *
     * @param <T> type of the {@link Tester}.
     *
     * @return an instance of {@link TesterBuilder}.
     */
    public static <T> TesterBuilder<T> createTest() {
        return AbstractTester.newBuilder();
    }
}
