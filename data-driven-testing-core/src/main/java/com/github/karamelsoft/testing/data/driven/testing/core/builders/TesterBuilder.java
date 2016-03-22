package com.github.karamelsoft.testing.data.driven.testing.core.builders;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;

/**
 * Created by frederic on 27/04/15.
 */
public interface TesterBuilder<T> extends TestNameBuilder<ScenarioNameBuilder<FolderBuilder<Tester<T>>>> {
}
