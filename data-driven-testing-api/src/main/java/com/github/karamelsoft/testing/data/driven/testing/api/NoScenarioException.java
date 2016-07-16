package com.github.karamelsoft.testing.data.driven.testing.api;

import java.io.File;

/**
 * @author Frederic Gendebien (frederic.gendebien@gmail.com).
 */
public class NoScenarioException extends RuntimeException {

    public NoScenarioException(final File file) {
        super("No scenario have been executed from: " + file.getPath());
    }
}
