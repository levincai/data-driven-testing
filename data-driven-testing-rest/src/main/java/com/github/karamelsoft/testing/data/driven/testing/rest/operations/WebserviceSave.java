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
public class WebserviceSave<T> implements Save<HttpResponse<T>> {

    private final Save<Map<String, Object>> envelopSave;
    private final Load<?> bodyLoad;

    private WebserviceSave(final Builder builder) {
        envelopSave = JsonTester.save();
        bodyLoad = builder.bodyLoad;
    }

    public static <T> Builder<T> newBuilder() {
        return new Builder<>();
    }

    public static <T> Builder<T> newBuilder(final WebserviceSave<T> copy) {
        final Builder builder = new Builder<T>();
        builder.bodyLoad = copy.bodyLoad;
        return builder;
    }

    @Override
    public void save(final HttpResponse<T> response, final OutputStream output) throws IOException {
        envelopSave.save(
            createMap(response),
            output);
    }

    private Map<String, Object> createMap(final HttpResponse<T> response) throws IOException {
        final Map<String, Object> map = new HashMap<>();
        map.put("headers", response.getHeaders());
        map.put("status", response.getStatus());
        map.put("body", bodyLoad.load(response.getRawBody()));

        return map;
    }

    public static final class Builder<T> {
        private Load<?> bodyLoad;

        private Builder() {
        }

        public Builder loadBodyWith(final Load<?> val) {
            bodyLoad = val;
            return this;
        }

        public WebserviceSave<T> build() {
            return new WebserviceSave<>(this);
        }
    }
}
