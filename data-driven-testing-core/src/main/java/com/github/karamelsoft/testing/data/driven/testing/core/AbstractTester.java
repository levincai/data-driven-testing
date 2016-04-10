package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.api.RuntimeIOException;
import com.github.karamelsoft.testing.data.driven.testing.api.Tester;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Comparison;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Scenario;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Script;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.FolderBuilder;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.ScenarioNameBuilder;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.TesterBuilder;
import com.github.karamelsoft.testing.data.driven.testing.core.utils.ExceptionUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
abstract class AbstractTester<T> implements Tester<T> {

    //--------------------------------------------------------------------------
    // Private constants
    //--------------------------------------------------------------------------
    private static final String DEFAULT_RESOURCE_FOLDER = "src/test/resources";
    private static final String DEFAULT_TARGET_FOLDER   = "target/tests";
    private static final String INPUT_FOLDER_NAME       = "input";
    private static final String OUTPUT_FOLDER_NAME      = "output";
    private static final String ERROR_FOLDER_NAME       = "error";

    //--------------------------------------------------------------------------
    // Private fields
    //--------------------------------------------------------------------------
    private final String testName;
    private final String scenarioName;

    private final Path inputResourcePath;
    private final Path outputResourcePath;
    private final Path outputTargetPath;
    private final Path errorTargetPath;

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------
    public static TesterBuilder createTest() {
        return new Builder();
    }

    //-------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------
    protected AbstractTester(final Builder<T> builder) throws IOException {
        testName = builder.testName;
        scenarioName = builder.scenarioName;

        final Path resourcePath =
            Paths.get(
                builder.resourcePath.orElse(DEFAULT_RESOURCE_FOLDER),
                testName,
                scenarioName);

        inputResourcePath = resourcePath.resolve(INPUT_FOLDER_NAME);
        outputResourcePath = resourcePath.resolve(OUTPUT_FOLDER_NAME);

        final Path targetPath =
            Paths.get(
                builder.targetPath.orElse(DEFAULT_TARGET_FOLDER),
                testName,
                scenarioName);

        outputTargetPath = targetPath.resolve(OUTPUT_FOLDER_NAME);
        errorTargetPath = targetPath.resolve(ERROR_FOLDER_NAME);

        FileUtils.deleteDirectory(targetPath.toFile());
    }

    protected AbstractTester(final AbstractTester<?> abstractTester) {
        this.testName           = abstractTester.testName;
        this.scenarioName       = abstractTester.scenarioName;
        this.inputResourcePath  = abstractTester.inputResourcePath;
        this.outputResourcePath = abstractTester.outputResourcePath;
        this.outputTargetPath   = abstractTester.outputTargetPath;
        this.errorTargetPath    = abstractTester.errorTargetPath;
    }

    //--------------------------------------------------------------------------
    // Public methods
    //--------------------------------------------------------------------------
    @Override
    public <U> Tester<U> value(final U value) {
        return new ActiveTester<>(this, value);
    }

    @Override
    public <T> Tester load(final String fileName, final Load<T> strategy) {
        try (final InputStream inputStream = readFromResource(fileName)) {
            return
                new ActiveTester<>(
                    this,
                    strategy.load(inputStream));
        }
        catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public Tester compare(final String fileName, final Comparison strategy) {

        Boolean errors = false;

        final File targetFile = outputTargetPath.resolve(fileName).toFile();

        try (
            final InputStream target    = createInputStream(outputTargetPath, fileName);
            final InputStream resource  = createInputStream(outputResourcePath, fileName)) {

            errors =
                !strategy.equivalent(
                    resource,
                    target);

            return this;
        }
        catch (final Throwable e) {
            errors = true;

            return this;
        }
        finally {
            if (errors) {
                ExceptionUtils.toRuntime(() ->
                    FileUtils.moveFileToDirectory(
                        targetFile,
                        errorTargetPath.toFile(),
                        true));
            }
        }
    }

    @Override
    public <U> Tester<U> script(final Script<T, U> script) {
        return script.execute(this);
    }

    @Override
    public Tester<T> scenario(final Scenario<T> scenario) {
        Arrays
            .asList(getOrCreateFolder(inputResourcePath).list())
            .forEach((String fileName) -> scenario.execute(fileName, this));

        return this;
    }

    @Override
    public void end() {

        final File errorFolder = errorTargetPath.toFile();

        assertThat(!errorFolder.exists() || errorFolder.list().length == 0)
            .overridingErrorMessage("Errors occured, see folder %s", errorFolder.getAbsolutePath())
            .isTrue();
    }

    //--------------------------------------------------------------------------
    // Portected methods
    //--------------------------------------------------------------------------
    protected InputStream readFromResource(final String fileName) throws FileNotFoundException {
        return createInputStream(inputResourcePath, fileName);
    }

    protected OutputStream writeIntoTarget(final String fileName) throws FileNotFoundException {
        return createOutputStream(outputTargetPath, fileName);
    }

    //--------------------------------------------------------------------------
    // Private methods
    //--------------------------------------------------------------------------
    private InputStream createInputStream(final Path path, final String fileName) throws FileNotFoundException {
        return
            new FileInputStream(
                new File(
                    getOrCreateFolder(path),
                    fileName));
    }

    private OutputStream createOutputStream(final Path path, final String fileName) throws FileNotFoundException {
        return
            new FileOutputStream(
                new File(
                    getOrCreateFolder(path),
                    fileName));
    }

    private File getOrCreateFolder(final Path path) {
        final File folder = path.toFile();
        folder.mkdirs();

        return folder;
    }

    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static final class Builder<T>
        implements
            TesterBuilder<T>,
            ScenarioNameBuilder<FolderBuilder<Tester<T>>>,
            FolderBuilder<Tester<T>> {

        private String           testName;
        private String           scenarioName;
        private Optional<String> resourcePath;
        private Optional<String> targetPath;

        private Builder() {
            resourcePath = Optional.empty();
            targetPath = Optional.empty();
        }

        @Override
        public ScenarioNameBuilder<FolderBuilder<Tester<T>>> name(final String testName) {
            this.testName = testName;

            return this;
        }

        @Override
        public FolderBuilder<Tester<T>> scenario(final String scenarioName) {
            this.scenarioName = scenarioName;

            return this;
        }

        @Override
        public FolderBuilder<Tester<T>> resourcePath(final String resourcePath) {
            this.resourcePath = Optional.of(resourcePath);

            return this;
        }

        @Override
        public FolderBuilder<Tester<T>> targetPath(final String targetPath) {
            this.targetPath = Optional.of(targetPath);

            return this;
        }

        @Override
        public Tester<T> begin() throws IOException {
            return new InactiveTester<>(this);
        }
    }
}
