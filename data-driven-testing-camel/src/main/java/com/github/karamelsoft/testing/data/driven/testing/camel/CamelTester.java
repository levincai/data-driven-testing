package com.github.karamelsoft.testing.data.driven.testing.camel;

import com.github.karamelsoft.testing.data.driven.testing.camel.operations.AssertIsVerified;
import com.github.karamelsoft.testing.data.driven.testing.camel.operations.GetException;
import com.github.karamelsoft.testing.data.driven.testing.camel.operations.SaveMockEndpoint;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class CamelTester {

    public static Runnable assertIsVerified(MockEndpoint mockEndpoint) {
        return new AssertIsVerified(mockEndpoint);
    }

    public static <T, I> SaveMockEndpoint.Builder<T, I> saveMockEndpoint() {
        return SaveMockEndpoint.newBuilder();
    }

    public static Function<Exchange, Optional<Exception>> getException() {
        return new GetException();
    }
}
