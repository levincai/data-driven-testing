package com.github.karamelsoft.testing.data.driven.testing.camel.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.FileNameBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.SaveBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.TypeBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import com.github.karamelsoft.testing.data.driven.testing.camel.builders.MockEndpointBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.fail;

/**
 * Created by frederic on 01/05/15.
 */
public class SaveMockEndpoint<I> implements Consumer<Tester> {

    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static final class Builder<I>
        implements
            MockEndpointBuilder<FileNameBuilder<TypeBuilder<I, SaveBuilder<I, Builder<I>>>>>,
            FileNameBuilder<TypeBuilder<I, SaveBuilder<I, Builder<I>>>>,
            TypeBuilder<I, SaveBuilder<I, Builder<I>>>,
            SaveBuilder<I, Builder<I>> {

        private MockEndpoint    mockEndpoint;
        private Class<I>        type;
        private String          fileName;
        private Save<I>         save;

        private Builder() {
        }

        public FileNameBuilder<TypeBuilder<I, SaveBuilder<I, Builder<I>>>> mockEndpoint(final MockEndpoint mockEndpoint) {
            this.mockEndpoint = mockEndpoint;

            return this;
        }

        public TypeBuilder<I, SaveBuilder<I, Builder<I>>> fileName(final String fileName) {
            this.fileName = fileName;
            return this;
        }

        public SaveBuilder<I, Builder<I>> type(final Class<I> type) {
            this.type = type;
            return this;
        }

        public Builder<I> save(final Save<I> save) {
            this.save = save;
            return this;
        }

        public SaveMockEndpoint<I> build() {
            return new SaveMockEndpoint<>(this);
        }
    }

    public static <I> Builder<I> newBuilder() {
        return new Builder<>();
    }

    //--------------------------------------------------------------------------
    // Private fields
    //--------------------------------------------------------------------------
    private final MockEndpoint mockEndpoint;
    private final Class<I>     type;
    private final String       fileName;
    private final Save<I>      save;

    private SaveMockEndpoint(Builder builder) {
        mockEndpoint    = builder.mockEndpoint;
        type            = builder.type;
        fileName        = builder.fileName;
        save            = builder.save;
    }

    //--------------------------------------------------------------------------
    // Public methods
    //--------------------------------------------------------------------------
    @Override
    public void accept(Tester tester) {

        Integer index = 0;
        for (Exchange exchange : mockEndpoint.getExchanges()) {
            try {
                tester
                    .value(exchange.getIn().getBody(type))
                    .save(MessageFormat.format("{0}-{1}", index++, fileName), save);
            }
            catch (IOException e) {
                fail("Something went wrong", e);
            }
        }
    }
}
