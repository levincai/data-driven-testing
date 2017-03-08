package com.github.karamelsoft.testing.data.driven.testing.json.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Comparison;
import org.apache.commons.io.IOUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class JsonCompare implements Comparison {

    private final JSONCompareMode compareMode;

    public JsonCompare(final JSONCompareMode compareMode) {
        this.compareMode = compareMode;
    }

    @Override
    public Boolean equivalent(final InputStream expected, final InputStream actual) throws IOException {
        try {
            JSONAssert.assertEquals(
                IOUtils.toString(expected),
                IOUtils.toString(actual),
                compareMode);

            return true;
        } catch (final Throwable e) {
            return false;
        }
    }
}
