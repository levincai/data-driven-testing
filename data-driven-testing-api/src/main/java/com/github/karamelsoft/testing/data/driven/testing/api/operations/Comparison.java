package com.github.karamelsoft.testing.data.driven.testing.api.operations;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by frederic on 26/04/15.
 */
@FunctionalInterface
public interface Comparison {

    /**
     *
     * @param resource
     * @param actual
     * @return
     * @throws IOException
     */
    Boolean equivalent(InputStream resource, InputStream actual) throws IOException;
}
