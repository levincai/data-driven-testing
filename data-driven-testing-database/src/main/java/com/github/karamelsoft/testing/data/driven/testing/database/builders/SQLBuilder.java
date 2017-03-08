package com.github.karamelsoft.testing.data.driven.testing.database.builders;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface SQLBuilder<R> {

    R order(String order);
}
