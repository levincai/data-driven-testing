package org.jsimple.data.driven.testing.xml.function;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;

import static org.jsimple.data.driven.testing.xml.function.TestUtils.*;

/**
 * Created by Jonathan Schoreels on 27/06/15.
 */
public class XmlCompareTest {


    @Test
    public void testSimilarOk() throws IOException {

        final Boolean equivalent =
            XmlTester.compare(XmlComparisonMode.LENIENT)
                .equivalent(
                    getInputStream(RESOURCES, "person1.xml"),
                    getInputStream(RESOURCES, "person1-similar.xml"));

        Assertions.assertThat(equivalent).isTrue();
    }

    @Test
    public void testEqualOk() throws IOException {

        final Boolean equivalent =
            XmlTester.compare(XmlComparisonMode.STRICT)
                .equivalent(
                    getInputStream(RESOURCES, "person1.xml"),
                    getInputStream(RESOURCES, "person1.xml"));

        Assertions.assertThat(equivalent).isTrue();
    }

    @Test
    public void testWhiteSpaceEqualOk() throws IOException {

        final Boolean equivalent =
            XmlTester.compare(XmlComparisonMode.STRICT)
                .equivalent(
                    getInputStream(RESOURCES, "person1.xml"),
                    getInputStream(RESOURCES, "person1-withoutwhitespace.xml"));

        Assertions.assertThat(equivalent).isTrue();
    }

    @Test
    public void testSimilarNotOk() throws IOException {

        final Boolean equivalent =
            XmlTester.compare(XmlComparisonMode.LENIENT)
                .equivalent(
                    getInputStream(RESOURCES, "person1.xml"),
                    getInputStream(RESOURCES, "person1-complete.xml"));

        Assertions.assertThat(equivalent).isFalse();
    }

    @Test
    public void testEqualNotOk() throws IOException {

        final Boolean equivalent =
            XmlTester.compare(XmlComparisonMode.STRICT)
                .equivalent(
                    getInputStream(RESOURCES, "person1.xml"),
                    getInputStream(RESOURCES, "person1-similar.xml"));

        Assertions.assertThat(equivalent).isFalse();
    }

}
