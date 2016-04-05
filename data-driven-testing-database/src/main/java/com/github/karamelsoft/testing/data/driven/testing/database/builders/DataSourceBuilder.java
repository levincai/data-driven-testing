package com.github.karamelsoft.testing.data.driven.testing.database.builders;

import javax.sql.DataSource;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface DataSourceBuilder<R> {

    R dataSource(DataSource dataSource);
}
