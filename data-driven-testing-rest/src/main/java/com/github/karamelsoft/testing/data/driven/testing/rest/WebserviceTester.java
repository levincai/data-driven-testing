package com.github.karamelsoft.testing.data.driven.testing.rest;

import com.github.karamelsoft.testing.data.driven.testing.rest.operations.WebserviceSave;
import com.mashape.unirest.http.HttpResponse;

/**
 * Created by frederic on 29/06/15.
 */
public class WebserviceTester {

    /**
     *
     * @return
     */
    public static WebserviceSave.Builder save() {
        return WebserviceSave.<HttpResponse<?>>newBuilder();
    }
}
