package org.jsimple.data.driven.testing.xml.function;

import org.apache.commons.io.IOUtils;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.ElementSelectors;
import org.xmlunit.diff.NodeMatcher;
import org.xmlunit.matchers.CompareMatcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.function.Function;

public class XmlCompare implements Comparison {

    private final XmlComparisonMode comparisonMode;

    private final static EnumMap<XmlComparisonMode, Function<Object, CompareMatcher>>
        compareMatcherStrategies;

    private final static EnumMap<XmlComparisonMode, NodeMatcher> nodeMatcherStrategies;

    static {
        compareMatcherStrategies = new EnumMap<>(XmlComparisonMode.class);
        compareMatcherStrategies.put(XmlComparisonMode.STRICT, CompareMatcher::isIdenticalTo);
        compareMatcherStrategies.put(XmlComparisonMode.LENIENT, CompareMatcher::isSimilarTo);
        Collections.unmodifiableMap(compareMatcherStrategies);

        nodeMatcherStrategies = new EnumMap<>(XmlComparisonMode.class);
        nodeMatcherStrategies.put(
            XmlComparisonMode.STRICT,
            new DefaultNodeMatcher()
        );
        nodeMatcherStrategies.put(
            XmlComparisonMode.LENIENT,
            new DefaultNodeMatcher(ElementSelectors.byName)
        );
        Collections.unmodifiableMap(nodeMatcherStrategies);
    }


    public XmlCompare(XmlComparisonMode comparisonMode) {
        this.comparisonMode = comparisonMode;
    }

    @Override
    public Boolean equivalent(final InputStream resource, final InputStream actual) throws IOException {
        return compareMatcherStrategies.get(comparisonMode)
            .apply(IOUtils.toString(resource))
            .withNodeMatcher(nodeMatcherStrategies.get(comparisonMode))
            .ignoreComments()
            .ignoreWhitespace()
            .matches(IOUtils.toString(actual));
    }
}
