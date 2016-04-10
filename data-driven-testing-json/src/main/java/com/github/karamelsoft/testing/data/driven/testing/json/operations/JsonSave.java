package com.github.karamelsoft.testing.data.driven.testing.json.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class JsonSave<I> implements Save<I> {

    public static class Builder<I> {
        private final ObjectMapper objectMapper;

        private Builder() {
            objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        }

        public Builder configure(final Consumer<ObjectMapper> configurator) {
            configurator.accept(objectMapper);

            return this;
        }

        public JsonSave<I> build() {
            return new JsonSave<>(this);
        }
    }

    public static <I> Builder<I> newBuilder() {
        return new Builder<>();
    }

    private final ObjectMapper objectMapper;

    private JsonSave(final Builder builder) {
        objectMapper = builder.objectMapper;
    }

    @Override
    public void save(final I value, final OutputStream output) throws IOException {
        objectMapper.writeValue(output, value);
    }
}
