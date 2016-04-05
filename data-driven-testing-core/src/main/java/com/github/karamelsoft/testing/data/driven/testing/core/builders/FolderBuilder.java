package com.github.karamelsoft.testing.data.driven.testing.core.builders;

/**
 * @author Frédéric Gendebien (frederic.gendebien@gmail.com)
 */
public interface FolderBuilder<O> extends Trigger<O> {

    FolderBuilder<O> resourcePath(String resourceFolderPath);

    FolderBuilder<O> targetPath(String targetFolder);
}
