package com.github.karamelsoft.testing.data.driven.testing.database;

import com.github.karamelsoft.testing.data.driven.testing.database.operations.Execute;
import com.github.karamelsoft.testing.data.driven.testing.database.operations.Query;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public class DatabaseTester {

    public static Execute.Builder execute() {
        return Execute.newBuilder();
    }

    public static Query.Builder query() {
        return Query.newBuilder();
    }
}
