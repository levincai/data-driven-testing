package com.github.karamelsoft.testing.data.driven.testing.api.builders;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;

/**
 * Created by frederic on 01/05/15.
 */
public interface SaveBuilder<I, R> {

    /**
     *
     * @param save
     * @return
     */
    R save(Save<I> save);
}
