package com.github.karamelsoft.testing.data.driven.testing.api;

/**
 * Exception used to notify that the {@link Tester} has no value.
 *
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class NoValueException extends RuntimeException {

    /**
     * Constructs the exception with the default meaningful message.
     */
    public NoValueException() {
        super("The tester has no value, please use value() or load() methods");
    }
}
