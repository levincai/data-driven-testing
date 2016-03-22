package com.github.karamelsoft.testing.data.driven.testing.database.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Script;
import com.github.karamelsoft.testing.data.driven.testing.database.builders.DataSourceBuilder;
import com.github.karamelsoft.testing.data.driven.testing.database.builders.SQLBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created by frederic on 03/06/15.
 */
public class Query implements Script<Object, List<Map<String, Object>>> {

    private final DataSource dataSource;
    private final String query;

    private Query(final Builder builder) {
        dataSource = builder.dataSource;
        query = builder.query;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(final Query copy) {
        final Builder builder = new Builder();
        builder.dataSource = copy.dataSource;
        builder.query = copy.query;

        return builder;
    }

    @Override
    public Tester<List<Map<String, Object>>> execute(final Tester<Object> tester) {
        return
            tester.value(
                new JdbcTemplate(dataSource)
                    .queryForList(query));
    }

    public static class Builder
        implements
            DataSourceBuilder<SQLBuilder<Builder>>,
            SQLBuilder<Builder> {

        private DataSource dataSource;
        private String query;

        private Builder() {
        }

        @Override
        public SQLBuilder<Builder> dataSource(final DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        @Override
        public Builder sql(final String order) {
            this.query = order;
            return this;
        }

        public Query build() {
            return new Query(this);
        }
    }
}
