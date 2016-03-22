package com.github.karamelsoft.testing.data.driven.testing.rest;

import com.github.karamelsoft.testing.data.driven.testing.rest.operations.WebserviceSave;

/**
 * Created by frederic on 29/06/15.
 */
public class WebserviceTester {

    /**
     *
     * @return
     */
    public static <T> WebserviceSave.Builder<T> save() {
        return WebserviceSave.<T>newBuilder();
    }
}
