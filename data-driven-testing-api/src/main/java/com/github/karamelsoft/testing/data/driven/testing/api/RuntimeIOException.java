package com.github.karamelsoft.testing.data.driven.testing.api;

import java.io.IOException;

/**
 * Created by frederic on 22/03/16.
 */
public class RuntimeIOException extends RuntimeException {

    public RuntimeIOException(IOException cause) {
        super(cause);
    }
}
