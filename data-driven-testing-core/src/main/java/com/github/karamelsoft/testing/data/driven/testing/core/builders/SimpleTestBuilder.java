package com.github.karamelsoft.testing.data.driven.testing.core.builders;

import com.github.karamelsoft.testing.data.driven.testing.api.builders.ComparisonBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.FunctionBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.LoadBuilder;
import com.github.karamelsoft.testing.data.driven.testing.api.builders.SaveBuilder;

/**
 * Created by frederic on 01/05/15.
 */
public interface SimpleTestBuilder<I, O, B>
    extends
        LoadBuilder<
            I,
            FunctionBuilder<
                I,
                O,
                SaveBuilder<O,
                    ComparisonBuilder<B>>>> {
}
