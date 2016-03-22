package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.api.NoValueException;
import com.github.karamelsoft.testing.data.driven.testing.api.Tester;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by frederic on 15/03/16.
 */
public class InactiveTester<T>
    extends AbstractTester<T>
    implements Tester<T> {

    protected InactiveTester(final Builder builder) throws IOException {
        super(builder);
    }

    @Override
    public <U> Tester<U> map(Function<T, U> function) {
        throw new NoValueException();
    }

    @Override
    public Tester<T> apply(Consumer<T> consumer) {
        throw new NoValueException();
    }

    @Override
    public Tester<T> save(String fileName, Save<T> strategy) {
        throw new NoValueException();
    }
}
