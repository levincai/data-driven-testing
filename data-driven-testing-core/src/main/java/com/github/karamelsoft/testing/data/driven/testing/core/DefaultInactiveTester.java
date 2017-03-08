package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.api.ActiveTester;
import com.github.karamelsoft.testing.data.driven.testing.api.InactiveTester;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Scenario;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Script;

import java.io.File;
import java.util.function.Consumer;

/**
 * Created by frederic on 15/03/16.
 */
public class DefaultInactiveTester
    extends AbstractTester
    implements InactiveTester {

    protected DefaultInactiveTester(final Builder builder) {
        super(builder);
    }

    @Override
    public <U> ActiveTester<U> script(Script<InactiveTester, U> script) {
        return script.execute(this);
    }

    @Override
    public InactiveTester scenario(final Scenario scenario) {

        getInputResources()
            .filter(File::isFile)
            .map(File::getName)
            .forEach((String fileName) -> scenario.execute(fileName, this));

        return this;
    }

    @Override
    public InactiveTester scenario(final Consumer<InactiveTester> scenario) {
        getFiles(scenarioPath(resourcePath()))
            .filter(File::isDirectory)
            .map(File::getName)
            .forEach(directory ->
                scenario.accept(
                    newBuilder(this)
                        .folder(directory)
                        .name(name())
                        .scenario(scenario())
                        .resourcePath(resourcePath())
                        .targetPath(targetPath())
                        .begin()));

        return this;
    }
}
