package com.github.karamelsoft.testing.data.driven.testing.camel.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.ActiveTester;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.FileNameBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.SaveBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.TypeBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import com.github.karamelsoft.testing.data.driven.testing.camel.builders.MockEndpointBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;

import java.text.MessageFormat;
import java.util.function.Consumer;

/**
 * Created by frederic on 01/05/15.
 */
public class SaveMockEndpoint<T, I> implements Consumer<ActiveTester<T>> {

    //--------------------------------------------------------------------------
    // Private fields
    //--------------------------------------------------------------------------
    private final MockEndpoint mockEndpoint;
    private final Class<I>     type;
    private final String       fileName;
    private final Save<I>      save;

    private SaveMockEndpoint(final Builder<T, I> builder) {
        mockEndpoint    = builder.mockEndpoint;
        type            = builder.type;
        fileName        = builder.fileName;
        save            = builder.save;
    }

    public static <T, I> Builder<T, I> newBuilder() {
        return new Builder<>();
    }

    //--------------------------------------------------------------------------
    // Public methods
    //--------------------------------------------------------------------------
    @Override
    public void accept(final ActiveTester<T> tester) {

        Integer index = 0;
        for (final Exchange exchange : mockEndpoint.getReceivedExchanges()) {
            tester
                .value(exchange.getIn().getBody(type))
                .save(
                    MessageFormat.format("{0}-{1}", index++, fileName),
                    save);
        }
    }
    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static final class Builder<T, I>
        implements
            MockEndpointBuilder<FileNameBuilder<TypeBuilder<I, SaveBuilder<I, Builder<T, I>>>>>,
            FileNameBuilder<TypeBuilder<I, SaveBuilder<I, Builder<T, I>>>>,
            TypeBuilder<I, SaveBuilder<I, Builder<T, I>>>,
            SaveBuilder<I, Builder<T, I>> {

        private MockEndpoint    mockEndpoint;
        private Class<I>        type;
        private String          fileName;
        private Save<I>         save;

        private Builder() {
        }

        public FileNameBuilder<TypeBuilder<I, SaveBuilder<I, Builder<T, I>>>> mockEndpoint(final MockEndpoint mockEndpoint) {
            this.mockEndpoint = mockEndpoint;

            return this;
        }

        public TypeBuilder<I, SaveBuilder<I, Builder<T, I>>> fileName(final String fileName) {
            this.fileName = fileName;
            return this;
        }

        public SaveBuilder<I, Builder<T, I>> type(final Class<I> type) {
            this.type = type;
            return this;
        }

        public Builder<T, I> save(final Save<I> save) {
            this.save = save;
            return this;
        }

        public SaveMockEndpoint<T, I> build() {
            return new SaveMockEndpoint<>(this);
        }
    }
}
