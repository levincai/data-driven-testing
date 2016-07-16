package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.api.NoScenarioException;
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
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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
    public static <T> Builder<T> newBuilder() {
        return new Builder<>();
    }

    public static <T> Builder<T> newBuilder(final AbstractTester<?> abstractTester) {
        final Builder<T> builder = new Builder<>();
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
    protected AbstractTester(final Builder<?> builder) {
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

    protected AbstractTester(final AbstractTester<?> prototype) {
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
    public <U> Tester<U> value(final U value) {
        return new ActiveTester<>(this, value);
    }

    @Override
    public <T> Tester<T> load(final String fileName, final Load<T> strategy) {
        try (final InputStream inputStream = createInputStream(inputResource(fileName))) {
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
    public Tester<T> compare(final String fileName, final Comparison strategy) {

        Boolean errors = false;

        final File targetFile = outputTarget(fileName);
        try (
            final InputStream target    = createInputStream(targetFile);
            final InputStream resource  = createInputStream(outputResource(fileName))) {

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
                        errorTarget(),
                        true));
            }
        }
    }

    @Override
    public Tester<T> script(final Consumer<Tester<T>> script) {
        script.accept(this);

        return this;
    }

    @Override
    public <U> Tester<U> script(final Script<T, U> script) {
        return script.execute(this);
    }

    @Override
    public Tester<T> scenario(final Scenario<T> scenario) {

        getFiles(inputResourcePath)
            .stream()
                .filter(File::isFile)
                .map(File::getName)
                .forEach((String fileName) -> scenario.execute(fileName, this));

        return this;
    }

    @Override
    public Tester<T> scenario(final Consumer<Tester<?>> scenario) {

        getFiles(scenarioPath(resourcePath))
            .stream()
                .filter(File::isDirectory)
                .map(File::getName)
                .forEach(directory ->
                    scenario.accept(
                        newBuilder(this)
                            .folder(directory)
                            .name(name)
                            .scenario(this.scenario)
                            .resourcePath(resourcePath)
                            .targetPath(targetPath)
                            .begin()));

        return this;
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
        return new FileInputStream(file);
    }

    protected OutputStream createOutputStream(final File file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }

    //--------------------------------------------------------------------------
    // Private methods
    //--------------------------------------------------------------------------
    private void prepareFolders() {
        ExceptionUtils.toRuntime(() ->
            FileUtils.deleteDirectory(errorTarget()));
    }

    private Path scenarioPath(final String path) {
        return
            folder
                .map(directory  -> Paths.get(path, name, scenario, directory))
                .orElseGet(()   -> Paths.get(path, name, scenario));
    }

    private Path getOrCreateFolders(final Path path) {
        path.toFile().mkdirs();

        return path;
    }

    private List<File> getFiles(final Path inputResourcePath) {
        final File folder = getOrCreateFolders(inputResourcePath).toFile();
        final List<File> files = Arrays.asList(folder.listFiles());
        if (files.isEmpty()) {
            throw new NoScenarioException(folder);
        }

        return files;
    }

    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static final class Builder<T>
        implements
            TesterBuilder<T>,
            ScenarioNameBuilder<FolderBuilder<Tester<T>>>,
            FolderBuilder<Tester<T>> {

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
        public ScenarioNameBuilder<FolderBuilder<Tester<T>>> name(final String testName) {
            this.name = testName;

            return this;
        }

        @Override
        public FolderBuilder<Tester<T>> scenario(final String scenarioName) {
            this.scenario = scenarioName;

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

        public Builder<T> folder(final String folder) {
            this.folder = Optional.of(folder);

            return this;
        }

        @Override
        public Tester<T> begin() {
            return new InactiveTester<>(this);
        }
    }
}
