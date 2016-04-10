package com.github.karamelsoft.testing.data.driven.testing.camel.operations;

import org.apache.camel.Exchange;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class GetException implements Function<Exchange, Optional<Exception>> {

    @Override
    public Optional<Exception> apply(Exchange exchange) {
        return
            Optional.ofNullable(
                Optional
                    .ofNullable(exchange)
                    .map(Exchange::getException)
                    .orElse(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class)));
    }
}
