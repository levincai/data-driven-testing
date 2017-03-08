package com.github.karamelsoft.testing.data.driven.testing.database.operations;

import com.github.karamelsoft.testing.data.driven.testing.database.builders.DataSourceBuilder;
import com.github.karamelsoft.testing.data.driven.testing.database.builders.SQLBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class Execute implements Runnable {

    public static class Builder
        implements
            DataSourceBuilder<SQLBuilder<Builder>>,
            SQLBuilder<Builder> {

        private DataSource dataSource;
        private Collection<String> orders;

        private Builder() {
            orders = new ArrayList<>();
        }

        @Override
        public SQLBuilder<Builder> dataSource(final DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        @Override
        public Builder order(final String order) {
            orders.add(order);
            return this;
        }

        public Execute build() {
            return new Execute(this);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(final Execute copy) {
        final Builder builder = new Builder();
        builder.dataSource = copy.dataSource;
        builder.orders = new ArrayList<>(copy.orders);
        return builder;
    }

    private final DataSource dataSource;
    private final Collection<String> orders;

    private Execute(final Builder builder) {
        dataSource = builder.dataSource;
        orders = builder.orders;
    }

    @Override
    public void run() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        orders.forEach(jdbcTemplate::execute);
    }
}
