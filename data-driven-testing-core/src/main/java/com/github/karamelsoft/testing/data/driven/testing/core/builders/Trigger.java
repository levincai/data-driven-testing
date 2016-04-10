package com.github.karamelsoft.testing.data.driven.testing.core.builders;

import java.io.IOException;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface Trigger<O> {

    O begin() throws IOException;
}
