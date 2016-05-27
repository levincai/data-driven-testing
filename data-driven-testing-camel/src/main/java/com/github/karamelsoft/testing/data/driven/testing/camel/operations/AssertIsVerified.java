package com.github.karamelsoft.testing.data.driven.testing.camel.operations;

import org.apache.camel.component.mock.MockEndpoint;
import org.assertj.core.api.Assertions;

/**
 * @author Frederic Gendebien (frederic.gendebien@gmail.com).
 */
public class AssertIsVerified implements Runnable {

    private final MockEndpoint mockEndpoint;

    public AssertIsVerified(MockEndpoint mockEndpoint) {
        this.mockEndpoint = mockEndpoint;
    }

    @Override
    public void run() {
        try {
            mockEndpoint.assertIsSatisfied();
        } catch (InterruptedException e) {
            Assertions.fail("could not assert if mockEnpoint is satified: " + e.getMessage());
        }
    }
}
