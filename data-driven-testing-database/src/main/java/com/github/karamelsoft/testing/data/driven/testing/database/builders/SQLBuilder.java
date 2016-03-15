package com.github.karamelsoft.testing.data.driven.testing.database.builders;

/**
 * Created by frederic on 05/06/15.
 */
public interface SQLBuilder<R> {

    /**
     *
     * @param order
     * @return
     */
    R sql(String order);
}
