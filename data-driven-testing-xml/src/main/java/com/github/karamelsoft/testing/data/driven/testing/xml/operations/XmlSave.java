package com.github.karamelsoft.testing.data.driven.testing.xml.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

/**
 * Created by Jonathan Schoreels on 27/06/15.
 */
public class XmlSave<I> implements Save<I> {

    public static class Builder<I> {
        private final ObjectMapper objectMapper;

        private Builder() {
            objectMapper = new XmlMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        }

        public Builder configure(Consumer<ObjectMapper> configurator) {
            configurator.accept(objectMapper);

            return this;
        }

        public XmlSave<I> build() {
            return new XmlSave<>(this);
        }
    }

    public static <I> Builder<I> newBuilder() {
        return new Builder<>();
    }

    private final ObjectMapper objectMapper;

    private XmlSave(Builder builder) {
        objectMapper = builder.objectMapper;
    }

    @Override
    public void save(I value, OutputStream output) throws IOException {
        objectMapper.writeValue(output, value);
    }
}
