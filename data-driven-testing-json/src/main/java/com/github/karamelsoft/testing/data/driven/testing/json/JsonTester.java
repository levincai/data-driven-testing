package com.github.karamelsoft.testing.data.driven.testing.json;

import com.github.karamelsoft.testing.data.driven.testing.api.operations.Comparison;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import com.github.karamelsoft.testing.data.driven.testing.json.operations.JsonCompare;
import com.github.karamelsoft.testing.data.driven.testing.json.operations.JsonLoad;
import com.github.karamelsoft.testing.data.driven.testing.json.operations.JsonSave;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * Created by frederic on 26/04/15.
 */
public class JsonTester {

    /**
     *
     * @return
     */
    public static <T> Load<T> load(final Class<T> type) {
        return
            JsonLoad.<T>newBuilder()
                .type(type)
                .build();
    }

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> JsonLoad.Builder<T> customLoad() {
        return JsonLoad.newBuilder();
    }

    /**
     *
     * @param
     * @return
     */
    public static <T> Save<T> save() {
        return
            JsonSave.<T>newBuilder()
                .build();
    }

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> JsonSave.Builder<T> customSave() {
        return JsonSave.newBuilder();
    }

    /**
     *
     * @return
     */
    public static Comparison compare(final JSONCompareMode compareMode) {
        return new JsonCompare(compareMode);
    }
}
