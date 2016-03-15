package com.github.karamelsoft.testing.data.driven.testing.camel;

import com.github.karamelsoft.testing.data.driven.testing.camel.operations.GetException;
import com.github.karamelsoft.testing.data.driven.testing.camel.operations.SaveMockEndpoint;
import org.apache.camel.Exchange;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by frederic on 01/05/15.
 */
public class CamelTester {

    /**
     *
     * @param <I>
     * @return
     */
    public static <I> SaveMockEndpoint.Builder<I> saveMockEndpoint() {
        return SaveMockEndpoint.newBuilder();
    }

    public static Function<Exchange, Optional<Exception>> getException() {
        return new GetException();
    }
}
