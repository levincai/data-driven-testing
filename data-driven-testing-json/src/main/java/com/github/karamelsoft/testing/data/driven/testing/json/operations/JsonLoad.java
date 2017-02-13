package com.github.karamelsoft.testing.data.driven.testing.json.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class JsonLoad<O> implements Load<O> {

    public static class Builder<O> {

        private final ObjectMapper objectMapper;
        private Class<O> type;

        private Builder() {
            objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
        }

        public Builder configure(final Consumer<ObjectMapper> configurator) {
            configurator.accept(objectMapper);

            return this;
        }

        public Builder type(final Class<O> type) {
            this.type = type;

            return this;
        }

        public JsonLoad<O> build() {
            return new JsonLoad<>(this);
        }
    }

    public static <O> Builder<O> newBuilder() {
        return new Builder<>();
    }

    private final ObjectMapper objectMapper;
    private final Class<O> type;

    private JsonLoad(final Builder builder) {
        objectMapper = builder.objectMapper;
        type = builder.type;
    }

    @Override
    public O load(final InputStream input) throws IOException {
        return objectMapper.readValue(input, type);
    }
}
