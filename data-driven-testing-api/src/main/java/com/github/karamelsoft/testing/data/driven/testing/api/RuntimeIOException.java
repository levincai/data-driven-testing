package com.github.karamelsoft.testing.data.driven.testing.api;

import java.io.IOException;

/**
 * Runtime exception used to wrap {@link IOException}.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class RuntimeIOException extends RuntimeException {

    /**
     * Constructor used to construct this exception
     * @param cause is the {@link IOException} to wrap.
     */
    public RuntimeIOException(IOException cause) {
        super(cause);
    }
}
