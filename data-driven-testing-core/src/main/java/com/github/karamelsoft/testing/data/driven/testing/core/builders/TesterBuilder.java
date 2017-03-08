package com.github.karamelsoft.testing.data.driven.testing.core.builders;

import com.github.karamelsoft.testing.data.driven.testing.api.InactiveTester;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface TesterBuilder
    extends
        TestNameBuilder<
            ScenarioNameBuilder<
                FolderBuilder<
                    InactiveTester>>> {
}
