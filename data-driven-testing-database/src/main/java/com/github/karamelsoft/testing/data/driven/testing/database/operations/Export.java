package com.github.karamelsoft.testing.data.driven.testing.database.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.function.Consumer;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class Export<T> implements Consumer<Tester<T>> {

    private final DataSource dataSource;
    private final String query;
    private final String fileName;
    private final Save<Object> save;

    private Export(Builder builder) {
        dataSource = builder.dataSource;
        query = builder.query;
        fileName = builder.fileName;
        save = builder.save;
    }

    public static <T> Builder<T> newBuilder() {
        return new Builder<>();
    }

    public static <T> Builder<T> newBuilder(Export<T> copy) {
        Builder builder = new Builder<>();
        builder.dataSource = copy.dataSource;
        builder.query = copy.query;
        builder.fileName = copy.fileName;
        builder.save = copy.save;
        return builder;
    }

    @Override
    public void accept(final Tester<T> tTester) {
        tTester
            .value(
                (Object)
                    new JdbcTemplate(dataSource)
                        .queryForList(query))
            .save(fileName, save);
    }

    public static final class Builder<T> {
        private DataSource dataSource;
        private String query;
        private String fileName;
        private Save<Object> save;

        private Builder() {
        }

        public Builder<T> dataSource(DataSource val) {
            dataSource = val;
            return this;
        }

        public Builder<T> query(String val) {
            query = val;
            return this;
        }

        public Builder<T> fileName(String val) {
            fileName = val;
            return this;
        }

        public Builder<T> save(Save<Object> val) {
            save = val;
            return this;
        }

        public Export<T> build() {
            return new Export<>(this);
        }
    }
}
