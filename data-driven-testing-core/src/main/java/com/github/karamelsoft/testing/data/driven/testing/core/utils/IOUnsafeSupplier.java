package com.github.karamelsoft.testing.data.driven.testing.core.utils;

import java.io.IOException;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface IOUnsafeSupplier<O> {

    O execute() throws IOException;
}
