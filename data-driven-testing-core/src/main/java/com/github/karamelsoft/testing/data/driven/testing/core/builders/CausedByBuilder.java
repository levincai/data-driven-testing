package com.github.karamelsoft.testing.data.driven.testing.core.builders;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface CausedByBuilder<R> {

    R causedBy(Class<? extends Throwable> exception);
}
