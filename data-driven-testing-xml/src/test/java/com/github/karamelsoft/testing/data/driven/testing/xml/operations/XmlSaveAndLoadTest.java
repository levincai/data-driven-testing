package com.github.karamelsoft.testing.data.driven.testing.xml.operations;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static com.github.karamelsoft.testing.data.driven.testing.xml.operations.TestUtils.*;

/**
 * Created by jschoreels on 05.04.16.
 */
public class XmlSaveAndLoadTest {

    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    XmlSave<Person> xmlSave = XmlSave.<Person>newBuilder().build();
    XmlLoad<Person> xmlLoad = XmlLoad.<Person>newBuilder().type(Person.class).build();

    @Before
    public void tearDown() throws Exception {
        Paths.get(TARGET).toFile().mkdirs();
    }

    @Test
    public void save() throws Exception {

        Person input = Person.newBuilder()
            .firstName(FIRSTNAME)
            .lastName(LASTNAME)
            .build();

        xmlSave.save(input, getOutputStream(TARGET, "person1.xml"));
        Person output = xmlLoad.load(getInputStream(TARGET, "person1.xml"));

        Assertions.assertThat(output.getFirstName()).isEqualTo(FIRSTNAME);
        Assertions.assertThat(output.getLastName()).isEqualTo(LASTNAME);
    }


}