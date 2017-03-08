package com.github.karamelsoft.testing.data.driven.testing.xml.operations;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Jonathan Schoreels on 27/06/15.
 */
public class XmlCompareTest {


    @Test
    public void testSimilarOk() throws IOException {

        final Boolean equivalent =
            XmlTester.compare(XmlComparisonMode.LENIENT)
                .equivalent(
                    TestUtils.getInputStream(TestUtils.RESOURCES, "person1.xml"),
                    TestUtils.getInputStream(TestUtils.RESOURCES, "person1-similar.xml"));

        Assertions.assertThat(equivalent).isTrue();
    }

    @Test
    public void testEqualOk() throws IOException {

        final Boolean equivalent =
            XmlTester.compare(XmlComparisonMode.STRICT)
                .equivalent(
                    TestUtils.getInputStream(TestUtils.RESOURCES, "person1.xml"),
                    TestUtils.getInputStream(TestUtils.RESOURCES, "person1.xml"));

        Assertions.assertThat(equivalent).isTrue();
    }

    @Test
    public void testWhiteSpaceEqualOk() throws IOException {

        final Boolean equivalent =
            XmlTester.compare(XmlComparisonMode.STRICT)
                .equivalent(
                    TestUtils.getInputStream(TestUtils.RESOURCES, "person1.xml"),
                    TestUtils.getInputStream(TestUtils.RESOURCES, "person1-withoutwhitespace.xml"));

        Assertions.assertThat(equivalent).isTrue();
    }

    @Test
    public void testSimilarNotOk() throws IOException {

        final Boolean equivalent =
            XmlTester.compare(XmlComparisonMode.LENIENT)
                .equivalent(
                    TestUtils.getInputStream(TestUtils.RESOURCES, "person1.xml"),
                    TestUtils.getInputStream(TestUtils.RESOURCES, "person1-complete.xml"));

        Assertions.assertThat(equivalent).isFalse();
    }

    @Test
    public void testEqualNotOk() throws IOException {

        final Boolean equivalent =
            XmlTester.compare(XmlComparisonMode.STRICT)
                .equivalent(
                    TestUtils.getInputStream(TestUtils.RESOURCES, "person1.xml"),
                    TestUtils.getInputStream(TestUtils.RESOURCES, "person1-similar.xml"));

        Assertions.assertThat(equivalent).isFalse();
    }

}
