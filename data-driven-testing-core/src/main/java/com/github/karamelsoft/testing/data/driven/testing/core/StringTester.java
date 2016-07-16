package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Comparison;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import org.apache.commons.io.IOUtils;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class StringTester {

    /**
     * Factory method used to create a String {@link Load} strategy.
     *
     * @return an instance of {@link Load}.
     */
    public static Load<String> load() {
        return IOUtils::toString;
    }

    /**
     * Factory method used to create a String {@link Save} strategy.
     *
     * @return an instance of {@link Save}.
     */
    public static Save<String> save() {
        return IOUtils::write;
    }

    /**
     * Factory method used to create a String {@link Comparison} strategy.
     *
     * @return an instance of {@link Comparison}.
     */
    public static Comparison compare() {
        return IOUtils::contentEquals;
    }
}
