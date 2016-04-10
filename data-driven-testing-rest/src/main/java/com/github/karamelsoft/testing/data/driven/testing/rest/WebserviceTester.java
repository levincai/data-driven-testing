package com.github.karamelsoft.testing.data.driven.testing.rest;

import com.github.karamelsoft.testing.data.driven.testing.rest.operations.WebserviceSave;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class WebserviceTester {

    public static <T> WebserviceSave.Builder<T> save() {
        return WebserviceSave.<T>newBuilder();
    }
}
