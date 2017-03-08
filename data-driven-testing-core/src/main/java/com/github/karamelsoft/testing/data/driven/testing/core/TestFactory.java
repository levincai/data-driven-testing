package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.api.ActiveTester;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.TesterBuilder;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class TestFactory {

    /**
     * Factory method used to create a {@link ActiveTester}.
     *
     * @return an instance of {@link TesterBuilder}.
     */
    public static TesterBuilder createTest() {
        return AbstractTester.newBuilder();
    }
}
