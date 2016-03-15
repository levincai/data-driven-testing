package com.github.karamelsoft.testing.data.driven.testing.core;

import org.apache.commons.io.IOUtils;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Comparison;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;

/**
 * Created by frederic on 26/04/15.
 */
public class StringTester {

    /**
     *
     * @return
     */
    public static Load<String> load() {
        return IOUtils::toString;
    }

    /**
     *
     * @param
     * @return
     */
    public static Save<String> save() {
        return IOUtils::write;
    }

    /**
     *
     * @return
     */
    public static Comparison compare() {
        return IOUtils::contentEquals;
    }
}
