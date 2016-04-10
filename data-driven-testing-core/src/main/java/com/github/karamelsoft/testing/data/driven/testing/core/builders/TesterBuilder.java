package com.github.karamelsoft.testing.data.driven.testing.core.builders;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface TesterBuilder<T> extends TestNameBuilder<ScenarioNameBuilder<FolderBuilder<Tester<T>>>> {
}
