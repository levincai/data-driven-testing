package com.github.karamelsoft.testing.data.driven.testing.api.operations;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by frederic on 23/04/15.
 */
@FunctionalInterface
public interface Load<O> {

    /**
     *
     * @param input
     * @return
     * @throws IOException
     */
    O load(InputStream input) throws IOException;
}
