package com.github.karamelsoft.testing.data.driven.testing.camel.operations;

import com.github.karamelsoft.testing.data.driven.testing.camel.CamelTester;
import com.github.karamelsoft.testing.data.driven.testing.core.TestFactory;
import org.apache.camel.EndpointInject;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by frederic on 14/03/16.
 */
public class GetExceptionTest extends CamelTestSupport {

    private static final String SOURCE = "direct:source";

    @Produce(uri = SOURCE)
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:destination")
    private MockEndpoint destination;

    @Before
    public void before() throws Exception {
        context().addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(SOURCE)
                    .process(exchange -> {
                        throw new IllegalArgumentException();
                    });
            }
        });
    }

    @Test
    public void testApply() throws Exception {
        TestFactory.createTest()
            .name("get-exception")
            .scenario("apply")
            .begin()
            .value((Processor) exchange -> {})
            .map((Processor processor) -> producerTemplate.send(processor))
            .map(CamelTester.getException())
            .apply((Optional<Exception> exception) -> {
                Assertions.assertThat(exception.isPresent());
                Assertions.assertThat(exception.get()).isInstanceOf(IllegalArgumentException.class);
            })
            .end();
    }
}