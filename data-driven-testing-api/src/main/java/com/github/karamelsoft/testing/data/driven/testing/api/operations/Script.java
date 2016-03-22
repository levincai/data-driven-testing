package com.github.karamelsoft.testing.data.driven.testing.api.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;

/**
 * Created by frederic on 21/03/16.
 */
public interface Script<T, U> {

    Tester<U> execute(Tester<T> tester);
}
