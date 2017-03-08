package com.github.karamelsoft.testing.data.driven.testing.database.operations;

import com.github.karamelsoft.testing.data.driven.testing.database.DatabaseTester;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by frederic on 04/06/15.
 */
public class ExecuteTest {

    @Test
    public void testExecute() {

        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(
            "jdbc:h2:mem:order;" +
                "INIT=" +
                "RUNSCRIPT FROM 'classpath:database/create.sql'");

        DatabaseTester.execute()
            .dataSource(dataSource)
            .order("INSERT INTO TEST (FIRST_NAME, LAST_NAME) VALUES ('John', 'Doe')")
            .order("INSERT INTO TEST (FIRST_NAME, LAST_NAME) VALUES ('Johnathan', 'Smith')")
            .build()
                .run();

        final Collection<Map<String, Object>> result =
            new JdbcTemplate(dataSource)
                .queryForList("SELECT * FROM TEST");

        assertThat(result).hasSize(2);
    }
}
