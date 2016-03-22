package com.github.karamelsoft.testing.data.driven.testing.api;

/**
 * Created by frederic on 26/04/15.
 */
public class NoValueException extends RuntimeException {

    /**
     *
     */
    public NoValueException() {
        super("The tester has no value, please use value() or load() methods");
    }
}
