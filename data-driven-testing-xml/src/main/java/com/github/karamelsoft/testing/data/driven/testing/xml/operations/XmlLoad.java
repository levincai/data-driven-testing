package com.github.karamelsoft.testing.data.driven.testing.xml.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

/**
 * Created by Jonathan Schoreels on 27/06/15.
 */
public class XmlLoad<O> implements Load<O> {

    public static class Builder<O> {

        private final ObjectMapper objectMapper;
        private Class<O> type;

        private Builder() {
            objectMapper = new XmlMapper();
        }

        public Builder configure(Consumer<ObjectMapper> configurator) {
            configurator.accept(objectMapper);

            return this;
        }

        public Builder type(Class<O> type) {
            this.type = type;

            return this;
        }

        public XmlLoad<O> build() {
            return new XmlLoad<>(this);
        }
    }

    public static <O> Builder<O> newBuilder() {
        return new Builder<>();
    }

    private final ObjectMapper objectMapper;
    private final Class<O> type;

    private XmlLoad(Builder builder) {
        objectMapper = builder.objectMapper;
        type = builder.type;
    }

    @Override
    public O load(InputStream input) throws IOException {
        return objectMapper.readValue(input, type);
    }
}
