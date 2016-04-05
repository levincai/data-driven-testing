package com.github.karamelsoft.testing.data.driven.testing.camel;

import com.github.karamelsoft.testing.data.driven.testing.camel.operations.GetException;
import com.github.karamelsoft.testing.data.driven.testing.camel.operations.SaveMockEndpoint;
import org.apache.camel.Exchange;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class CamelTester {

    public static <I, O> SaveMockEndpoint.Builder<I, O> saveMockEndpoint() {
        return SaveMockEndpoint.newBuilder();
    }

    public static Function<Exchange, Optional<Exception>> getException() {
        return new GetException();
    }
}
