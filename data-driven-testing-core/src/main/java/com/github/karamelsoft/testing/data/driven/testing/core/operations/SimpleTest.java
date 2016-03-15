package com.github.karamelsoft.testing.data.driven.testing.core.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.ComparisonBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.FunctionBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.SaveBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Comparison;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.SimpleTestBuilder;

import java.io.IOException;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.fail;

/**
 * Created by frederic on 27/04/15.
 */
public abstract class SimpleTest<I, O> {

    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static abstract class Builder<I, O, B extends Builder<I, O, B>>
        implements
            SimpleTestBuilder<I, O, B>,
            FunctionBuilder<I, O, SaveBuilder<O, ComparisonBuilder<B>>>,
            SaveBuilder<O, ComparisonBuilder<B>>,
            ComparisonBuilder<B> {

        private Load<I>        load;
        private Function<I, O> function;
        private Save<O>        save;
        private Comparison     comparison;

        protected Builder() {
        }

        public abstract B self();

        public B load(Load<I> load) {
            this.load = load;

            return self();
        }

        public B function(final Function<I, O> function) {
            this.function = function;

            return self();
        }

        public B save(Save<O> save) {
            this.save = save;

            return self();
        }

        public B comparison(Comparison comparison) {
            this.comparison = comparison;

            return self();
        }
    }

    //--------------------------------------------------------------------------
    // Private fields
    //--------------------------------------------------------------------------
    private final Function<I, O> function;
    private final Load<I>        load;
    private final Save<O>        save;
    private final Comparison     comparison;

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------
    protected SimpleTest(final Builder builder) {
        function = builder.function;
        load = builder.load;
        save = builder.save;
        comparison = builder.comparison;
    }

    //--------------------------------------------------------------------------
    // Public methods
    //--------------------------------------------------------------------------
    public void script(final String fileName, final Tester tester) {
        try {
            tester
                .load(fileName, load)
                .map(function)
                .save(fileName, save)
                .compare(fileName, comparison);
        }
        catch (IOException e) {
            fail("Something went wrong", e);
        }
    }
}
