package com.github.karamelsoft.testing.data.driven.testing.database;

import com.github.karamelsoft.testing.data.driven.testing.database.operations.Execute;
import com.github.karamelsoft.testing.data.driven.testing.database.operations.Query;

/**
 * Created by frederic on 03/06/15.
 */
public class DatabaseTester {

    /**
     *
     * @return
     */
    public static Execute.Builder execute() {
        return Execute.newBuilder();
    }

    /**
     *
     * @return
     */
    public static Query.Builder query() {
        return Query.newBuilder();
    }
}
