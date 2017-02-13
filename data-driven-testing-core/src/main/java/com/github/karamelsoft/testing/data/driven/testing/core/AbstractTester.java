package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.api.*;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.FolderBuilder;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.ScenarioNameBuilder;
import com.github.karamelsoft.testing.data.driven.testing.core.builders.TesterBuilder;
import com.github.karamelsoft.testing.data.driven.testing.core.utils.ExceptionUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
abstract class AbstractTester implements Tester {

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
    private final String name;
    private final String scenario;

    private final String resourcePath;
    private final String targetPath;
    private final Optional<String> folder;

    private final Path inputResourcePath;
    private final Path outputResourcePath;
    private final Path outputTargetPath;
    private final Path errorTargetPath;

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------
    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(final AbstractTester abstractTester) {
        final Builder builder = new Builder();
        builder.name(abstractTester.name);
        builder.scenario(abstractTester.scenario);
        builder.resourcePath(abstractTester.resourcePath);
        builder.targetPath(abstractTester.targetPath);

        abstractTester.folder.ifPresent(builder::folder);

        return builder;
    }

    //-------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------
    protected AbstractTester(final Builder builder) {
        name            = builder.name;
        scenario        = builder.scenario;
        resourcePath    = builder.resourcePath.orElse(DEFAULT_RESOURCE_FOLDER);
        targetPath      = builder.targetPath.orElse(DEFAULT_TARGET_FOLDER);
        folder          = builder.folder;

        final Path scenarioResourcePath = scenarioPath(resourcePath);
        inputResourcePath   = scenarioResourcePath.resolve(INPUT_FOLDER_NAME);
        outputResourcePath  = scenarioResourcePath.resolve(OUTPUT_FOLDER_NAME);

        final Path scenarioTargetPath = scenarioPath(targetPath);
        outputTargetPath    = scenarioTargetPath.resolve(OUTPUT_FOLDER_NAME);
        errorTargetPath     = scenarioTargetPath.resolve(ERROR_FOLDER_NAME);

        prepareFolders();
    }

    protected AbstractTester(final AbstractTester prototype) {
        name                = prototype.name;
        scenario            = prototype.scenario;
        resourcePath        = prototype.resourcePath;
        targetPath          = prototype.targetPath;
        folder              = prototype.folder;

        inputResourcePath   = prototype.inputResourcePath;
        outputResourcePath  = prototype.outputResourcePath;
        outputTargetPath    = prototype.outputTargetPath;
        errorTargetPath     = prototype.errorTargetPath;
    }

    //--------------------------------------------------------------------------
    // Public methods
    //--------------------------------------------------------------------------
    @Override
    public <U> ActiveTester<U> value(final U value) {
        return new DefaultActiveTester<>(this, value);
    }

    @Override
    public <T> ActiveTester<T> load(final String fileName, final Load<T> strategy) {
        try (final InputStream inputStream = createInputStream(inputResource(fileName))) {
            return
                new DefaultActiveTester(
                    this,
                    strategy.load(inputStream));
        }
        catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public void end() {

        final File errorFolder = errorTarget();

        assertThat(!errorFolder.exists() || errorFolder.list().length == 0)
            .overridingErrorMessage("Errors occured, see folder %s", errorFolder.getPath())
            .isTrue();
    }

    //--------------------------------------------------------------------------
    // Protected methods
    //--------------------------------------------------------------------------
    protected String name() {
        return name;
    }

    protected String scenario() {
        return scenario;
    }

    protected String resourcePath() {
        return resourcePath;
    }

    protected String targetPath() {
        return targetPath;
    }

    protected File inputResource(final String fileName) {
        return getOrCreateFolders(inputResourcePath).resolve(fileName).toFile();
    }

    protected File outputResource(final String fileName) {
        return getOrCreateFolders(outputResourcePath).resolve(fileName).toFile();
    }

    protected File outputTarget(final String fileName) {
        return getOrCreateFolders(outputTargetPath).resolve(fileName).toFile();
    }

    protected File errorTarget() {
        return errorTargetPath.toFile();
    }

    protected InputStream createInputStream(final File file) throws FileNotFoundException {
        return
            new BufferedInputStream(
                new FileInputStream(file));
    }

    protected OutputStream createOutputStream(final File file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }

    protected Stream<File> getInputResources() {
        return getFiles(inputResourcePath);
    }

    protected Path scenarioPath(final String path) {
        return
            folder
                .map(directory  -> Paths.get(path, name, scenario, directory))
                .orElseGet(()   -> Paths.get(path, name, scenario));
    }

    protected Stream<File> getFiles(final Path inputResourcePath) {
        final File folder = getOrCreateFolders(inputResourcePath).toFile();
        final List<File> files = Arrays.asList(folder.listFiles());
        if (files.isEmpty()) {
            throw new NoScenarioException(folder);
        }

        return files.stream();
    }

    //--------------------------------------------------------------------------
    // Private methods
    //--------------------------------------------------------------------------
    private void prepareFolders() {
        ExceptionUtils.toRuntime(() ->
            FileUtils.deleteDirectory(errorTarget()));
    }

    private Path getOrCreateFolders(final Path path) {
        path.toFile().mkdirs();

        return path;
    }

    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static final class Builder
        implements
            TesterBuilder,
            ScenarioNameBuilder<FolderBuilder<InactiveTester>>,
            FolderBuilder<InactiveTester> {

        private String              name;
        private String              scenario;
        private Optional<String>    resourcePath;
        private Optional<String>    targetPath;
        private Optional<String>    folder;

        private Builder() {
            resourcePath    = Optional.empty();
            targetPath      = Optional.empty();
            folder          = Optional.empty();
        }

        @Override
        public ScenarioNameBuilder<FolderBuilder<InactiveTester>> name(final String testName) {
            this.name = testName;

            return this;
        }

        @Override
        public FolderBuilder<InactiveTester> scenario(final String scenarioName) {
            this.scenario = scenarioName;

            return this;
        }

        @Override
        public FolderBuilder<InactiveTester> resourcePath(final String resourcePath) {
            this.resourcePath = Optional.of(resourcePath);

            return this;
        }

        @Override
        public FolderBuilder<InactiveTester> targetPath(final String targetPath) {
            this.targetPath = Optional.of(targetPath);

            return this;
        }

        public Builder folder(final String folder) {
            this.folder = Optional.of(folder);

            return this;
        }

        @Override
        public InactiveTester begin() {
            return new DefaultInactiveTester(this);
        }
    }
}
