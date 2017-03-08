package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.api.ActiveTester;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Comparison;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Script;
import com.github.karamelsoft.testing.data.driven.testing.core.utils.ExceptionUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
class DefaultActiveTester<T>
    extends AbstractTester
    implements ActiveTester<T> {

    private final T value;

    DefaultActiveTester(final AbstractTester activeTester, final T value) {
        super(activeTester);

        this.value = value;
    }

    @Override
    public <U> ActiveTester<U> map(final Function<T, U> function) {
        return
            new DefaultActiveTester(
                this,
                function.apply(value));
    }

    @Override
    public ActiveTester<T> apply(final Consumer<T> consumer) {
        consumer.accept(value);

        return this;
    }

    @Override
    public ActiveTester<T> save(final String fileName, final Save<T> strategy) {
        ExceptionUtils.toRuntime(() ->
            strategy.save(
                value,
                createOutputStream(outputTarget(fileName))));

        return this;
    }

    @Override
    public ActiveTester<T> compare(final String fileName, final Comparison strategy) {

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
    public ActiveTester<T> script(final Consumer<ActiveTester<T>> script) {
        script.accept(this);

        return this;
    }

    @Override
    public <U> ActiveTester<U> script(final Script<ActiveTester<T>, U> script) {
        return script.execute(this);
    }

}
