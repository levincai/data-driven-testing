package com.github.karamelsoft.testing.data.driven.testing.api.builders;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Comparison;

/**
 * Created by frederic on 01/05/15.
 */
public interface ComparisonBuilder<R> {

    /**
     *
     * @param comparison
     * @return
     */
    R comparison(Comparison comparison);
}
