package com.github.karamelsoft.testing.data.driven.testing.camel.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.ActiveTester;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.FileNameBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.SaveBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import com.github.karamelsoft.testing.data.driven.testing.camel.builders.MockEndpointBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;

import java.text.MessageFormat;
import java.util.function.Consumer;

/**
 * Created by frederic on 01/05/15.
 */
public class SaveMockEndpoint<T, U> implements Consumer<ActiveTester<T>> {

    //--------------------------------------------------------------------------
    // Private fields
    //--------------------------------------------------------------------------
    private final MockEndpoint mockEndpoint;
    private final String       fileName;
    private final Save<U>      save;

    private SaveMockEndpoint(final Builder<T, U> builder) {
        mockEndpoint    = builder.mockEndpoint;
        fileName        = builder.fileName;
        save            = builder.save;
    }

    public static <T, U> Dsl<T, U> newBuilder() {
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
                .value((U) exchange.getIn().getBody())
                .save(
                    MessageFormat.format("{0}-{1}", index++, fileName),
                    save);
        }
    }
    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public interface Dsl<T, U> extends MockEndpointBuilder<FileNameBuilder<SaveBuilder<U, Builder<T, U>>>> {}

    public static final class Builder<T, U>
        implements
            Dsl<T, U>,
            FileNameBuilder<SaveBuilder<U, Builder<T, U>>>,
            SaveBuilder<U, Builder<T, U>> {

        private MockEndpoint    mockEndpoint;
        private String          fileName;
        private Save<U>         save;

        private Builder() {
        }

        public FileNameBuilder<SaveBuilder<U, Builder<T, U>>> mockEndpoint(final MockEndpoint mockEndpoint) {
            this.mockEndpoint = mockEndpoint;

            return this;
        }

        public SaveBuilder<U, Builder<T, U>> fileName(final String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder<T, U> save(final Save<U> save) {
            this.save = save;
            return this;
        }

        public SaveMockEndpoint<T, U> build() {
            return new SaveMockEndpoint<>(this);
        }
    }
}
