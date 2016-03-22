package com.github.karamelsoft.testing.data.driven.testing.camel.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.FileNameBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.SaveBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.TypeBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Script;
import com.github.karamelsoft.testing.data.driven.testing.camel.builders.MockEndpointBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;

import java.text.MessageFormat;

/**
 * Created by frederic on 01/05/15.
 */
public class SaveMockEndpoint<I, O> implements Script<I, I> {

    //--------------------------------------------------------------------------
    // Private fields
    //--------------------------------------------------------------------------
    private final MockEndpoint mockEndpoint;
    private final Class<O>     type;
    private final String       fileName;
    private final Save<O>      save;

    private SaveMockEndpoint(final Builder builder) {
        mockEndpoint    = builder.mockEndpoint;
        type            = builder.type;
        fileName        = builder.fileName;
        save            = builder.save;
    }

    public static <I, O> Builder<I, O> newBuilder() {
        return new Builder<>();
    }

    //--------------------------------------------------------------------------
    // Public methods
    //--------------------------------------------------------------------------
    @Override
    public Tester<I> execute(final Tester<I> tester) {

        Integer index = 0;
        for (final Exchange exchange : mockEndpoint.getExchanges()) {
            tester
                .value(exchange.getIn().getBody(type))
                .save(
                    MessageFormat.format("{0}-{1}", index++, fileName),
                    save);
        }

        return tester;
    }
    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static final class Builder<I, O>
        implements
            MockEndpointBuilder<FileNameBuilder<TypeBuilder<O, SaveBuilder<O, Builder<I, O>>>>>,
            FileNameBuilder<TypeBuilder<O, SaveBuilder<O, Builder<I, O>>>>,
            TypeBuilder<O, SaveBuilder<O, Builder<I, O>>>,
            SaveBuilder<O, Builder<I, O>> {

        private MockEndpoint    mockEndpoint;
        private Class<O>        type;
        private String          fileName;
        private Save<O>         save;

        private Builder() {
        }

        public FileNameBuilder<TypeBuilder<O, SaveBuilder<O, Builder<I, O>>>> mockEndpoint(final MockEndpoint mockEndpoint) {
            this.mockEndpoint = mockEndpoint;

            return this;
        }

        public TypeBuilder<O, SaveBuilder<O, Builder<I, O>>> fileName(final String fileName) {
            this.fileName = fileName;
            return this;
        }

        public SaveBuilder<O, Builder<I, O>> type(final Class<O> type) {
            this.type = type;
            return this;
        }

        public Builder<I, O> save(final Save<O> save) {
            this.save = save;
            return this;
        }

        public SaveMockEndpoint<I, O> build() {
            return new SaveMockEndpoint<>(this);
        }
    }
}
