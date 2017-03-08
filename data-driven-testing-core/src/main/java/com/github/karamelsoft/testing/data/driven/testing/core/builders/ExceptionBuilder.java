package com.github.karamelsoft.testing.data.driven.testing.core.builders;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface ExceptionBuilder<R> {

    R exception(Class<? extends Throwable> exception);
}
