package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.api.NoValueException;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.ExpectExceptionBuilder;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.SimpleTestBuilder;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.FileNameBuilder;
import com.github.karamelsoft.testing.data.driven.testing.core.operations.BiConsumerSimpleTest;
import com.github.karamelsoft.testing.data.driven.testing.core.operations.ConsumerSimpleTest;
import com.github.karamelsoft.testing.data.driven.testing.core.operations.ExpectException;

/**
 * Created by frederic on 26/04/15.
 */
public class CoreTester {

    /**
     *
     * @return
     */
    public static NoValueException noValue() {
        return new NoValueException("No value defined, use Tester::value or Tester::load methods");
    }

    /**
     * 
     * @return
     */
    public static ExpectExceptionBuilder<ExpectException.Builder> expectException() {
        return ExpectException.newBuilder();
    }

    /**
     *
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> FileNameBuilder<SimpleTestBuilder<I, O, ConsumerSimpleTest.Builder<I, O>>> script() {
        return ConsumerSimpleTest.<I, O>newBuilder();
    }

    /**
     *
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> SimpleTestBuilder<I, O, BiConsumerSimpleTest.Builder<I, O>> scenario() {
        return BiConsumerSimpleTest.<I, O>newBuilder();
    }
}
