package com.github.karamelsoft.testing.data.driven.testing.core;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by frederic on 27/04/15.
 */
public class TestFactoryTest {

    private static final String TEST_NAME = "tester";

    @Test
    public void testNoOuputFile() throws IOException {

        final String scenarioName = "no-output-file";
        final String fileName = "doesnotexists.txt";

        final com.github.karamelsoft.testing.data.driven.testing.api.ActiveTester tester =
            TestFactory.createTest()
                .name(TEST_NAME)
                .scenario(scenarioName)
                .begin()
                    .value("whatever the value")
                    .map(String::toUpperCase)
                    .save(fileName, StringTester.save())
                    .compare(fileName, StringTester.compare());

        try {
            tester.end();

            Assertions.fail("An error should have been thrown above");
        }
        catch (AssertionError e) {
            final Path outputPath =
                Paths.get(
                    "target/tests/",
                    TEST_NAME,
                    scenarioName,
                    "error",
                    fileName);

            assertThat(outputPath).exists();
            assertThat(FileUtils.readFileToString(outputPath.toFile())).isEqualTo("WHATEVER THE VALUE");
        }
    }

    @Test
    public void testFromValue() throws IOException {

        final Map<String, Object> value = new HashMap<>();
        value.put("one", 1);
        value.put("two", 2);

        TestFactory.createTest()
            .name(TEST_NAME)
            .scenario("from-value")
            .begin()
                .value(value)
                .apply((Map<String, Object> map) -> map.put("three", 3))
                .map(Object::toString)
                .save("map.txt", StringTester.save())
                .compare("map.txt", StringTester.compare())
                .end();
    }

    @Test
    public void testFromFile() throws IOException {

        TestFactory.createTest()
            .name(TEST_NAME)
            .scenario("from-file")
            .begin()
                .load("input.txt", StringTester.load())
                .map(String::toUpperCase)
                .save("output.txt", StringTester.save())
                .compare("output.txt", StringTester.compare())
                .end();
    }

    @Test
    public void testScript() throws IOException {

        TestFactory.createTest()
            .name(TEST_NAME)
            .scenario("script")
            .begin()
                .script(tester -> tester
                    .load("one.txt", StringTester.load())
                    .map(String::toUpperCase)
                    .save("one.txt", StringTester.save())
                    .compare("one.txt", StringTester.compare()))
                .end();
    }

    @Test
    public void testSimpleScenario() throws IOException {

        TestFactory.createTest()
            .name(TEST_NAME)
            .scenario("simple-scenario")
            .begin()
                .scenario((fileName, tester) -> tester
                    .load(fileName, StringTester.load())
                    .map(String::toUpperCase)
                    .save(fileName, StringTester.save())
                    .compare(fileName, StringTester.compare())
                    .end())
                .end();
    }

    @Test
    public void testComplexScenario() throws IOException {

        TestFactory.createTest()
            .name(TEST_NAME)
            .scenario("complex-scenario")
            .begin()
                .scenario(tester -> tester
                    .load("headers.txt", StringTester.load())
                    .map(String::toUpperCase)
                    .save("headers.txt", StringTester.save())
                    .compare("headers.txt", StringTester.compare())
                    .load("body.txt", StringTester.load())
                    .map(String::toUpperCase)
                    .save("body.txt", StringTester.save())
                    .compare("body.txt", StringTester.compare())
                    .end())
                .end();
    }
}
