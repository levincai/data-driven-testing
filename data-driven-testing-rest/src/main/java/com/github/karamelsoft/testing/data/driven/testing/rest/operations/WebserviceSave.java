package com.github.karamelsoft.testing.data.driven.testing.rest.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import com.github.karamelsoft.testing.data.driven.testing.json.JsonTester;
import com.mashape.unirest.http.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by frederic on 04/07/15.
 */
public class WebserviceSave<T> implements Save<HttpResponse<?>> {

    private final Save<Map<String, Object>> envelopSave;
    private final Load<?> bodyLoad;

    private WebserviceSave(Builder builder) {
        envelopSave = JsonTester.save();
        bodyLoad = builder.bodyLoad;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(WebserviceSave copy) {
        Builder builder = new Builder();
        builder.bodyLoad = copy.bodyLoad;
        return builder;
    }

    @Override
    public void save(HttpResponse<?> response, OutputStream output) throws IOException {
        envelopSave.save(
            createMap(response),
            output);
    }

    private Map<String, Object> createMap(HttpResponse<?> response) throws IOException {
        final Map<String, Object> map = new HashMap<>();
        map.put("headers", response.getHeaders());
        map.put("status", response.getStatus());
        map.put("body", bodyLoad.load(response.getRawBody()));

        return map;
    }

    public static final class Builder {
        private Load<?> bodyLoad;

        private Builder() {
        }

        public Builder loadBodyWith(Load<?> val) {
            bodyLoad = val;
            return this;
        }

        public WebserviceSave build() {
            return new WebserviceSave(this);
        }
    }
}
