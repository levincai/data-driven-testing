package com.github.karamelsoft.testing.data.driven.testing.core.utils;

import java.io.IOException;

/**
 * Created by frederic on 22/03/16.
 */
public interface IOUnsafeSupplier<O> {

    O execute() throws IOException;
}
