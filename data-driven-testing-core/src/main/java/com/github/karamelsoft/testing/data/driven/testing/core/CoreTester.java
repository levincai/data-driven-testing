package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.core.builders.ExpectExceptionBuilder;
import com.github.karamelsoft.testing.data.driven.testing.core.operations.ExpectException;

/**
 * Created by frederic on 26/04/15.
 */
public class CoreTester {

    /**
     * 
     * @return
     */
    public static ExpectExceptionBuilder<ExpectException.Builder> expectException() {
        return ExpectException.newBuilder();
    }
}
