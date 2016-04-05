package com.github.karamelsoft.testing.data.driven.testing.camel.builders;

import org.apache.camel.component.mock.MockEndpoint;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface MockEndpointBuilder<R> {

    R mockEndpoint(MockEndpoint mockEndpoint);
}
