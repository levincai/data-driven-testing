package com.github.karamelsoft.testing.data.driven.testing.api.builders;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;

/**
 * Created by frederic on 01/05/15.
 */
public interface LoadBuilder<O, R> {

    /**
     *
     * @param load
     * @return
     */
    R load(Load<O> load);
}
