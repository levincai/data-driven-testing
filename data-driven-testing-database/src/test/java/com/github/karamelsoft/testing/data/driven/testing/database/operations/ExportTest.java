package com.github.karamelsoft.testing.data.driven.testing.database.operations;

import com.github.karamelsoft.testing.data.driven.testing.api.Tester;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import com.github.karamelsoft.testing.data.driven.testing.database.DatabaseTester;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by frederic on 03/06/15.
 */
public class ExportTest {

    @Test
    public void testExport() throws IOException {

        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(
            "jdbc:h2:mem:order;" +
                "INIT=" +
                    "RUNSCRIPT FROM 'classpath:database/create.sql'\\;" +
                    "RUNSCRIPT FROM 'classpath:database/populate.sql'");

        final Tester<Object> inputTester = mock(Tester.class);
        when(inputTester.value(any()))
            .thenReturn(inputTester);

        final Save<Object> save = mock(Save.class);

        DatabaseTester.export()
            .dataSource(dataSource)
            .query("SELECT * FROM TEST")
            .fileName("fileName")
            .save(save)
            .build()
                .accept(inputTester);

        final ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(inputTester, times(1)).value(captor.capture());
        verify(inputTester, times(1)).save(eq("fileName"), eq(save));

        final Collection<Map<String, Object>> collection = (Collection<Map<String, Object>>) captor.getValue();
        assertThat(collection).hasSize(2);

        verifyNoMoreInteractions(inputTester);
    }
}
