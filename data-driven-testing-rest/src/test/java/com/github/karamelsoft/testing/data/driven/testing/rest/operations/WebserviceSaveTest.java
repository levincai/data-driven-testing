package com.github.karamelsoft.testing.data.driven.testing.rest.operations;

import com.github.karamelsoft.testing.data.driven.testing.core.TestFactory;
import com.github.karamelsoft.testing.data.driven.testing.core.StringTester;
import com.github.karamelsoft.testing.data.driven.testing.json.JsonTester;
import com.github.karamelsoft.testing.data.driven.testing.rest.WebserviceTester;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.Rule;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by frederic on 29/06/15.
 */
public class WebserviceSaveTest {

    @Rule
    public WireMockRule server = new WireMockRule(60000);

    @Test
    public void testString() throws Exception {

        TestFactory.createTest()
            .name("save")
            .scenario("string")
            .begin()
                .value("response from mocked server")
                .apply((String body) ->
                    stubFor(
                        get(urlEqualTo("/string"))
                            .willReturn(aResponse()
                                .withBody(body)
                                .withStatus(200))))
                .value(
                    Unirest
                        .get("http://localhost:60000/string")
                        .asString())
                .save(
                    "response.json",
                    WebserviceTester.save()
                        .loadBodyWith(StringTester.load())
                        .build())
                .compare("response.json", JsonTester.compare(JSONCompareMode.LENIENT))
            .end();
    }

    @Test
    public void testJson() throws Exception {

        TestFactory.createTest()
            .name("save")
            .scenario("json")
            .begin()
                .load("body.json", StringTester.load())
                .apply((String body) ->
                    stubFor(
                        get(urlEqualTo("/json"))
                            .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody(body)
                                .withStatus(200))))
                .value(
                    Unirest
                        .get("http://localhost:60000/json")
                        .asJson())
            .save(
                "response.json",
                WebserviceTester.save()
                    .loadBodyWith(JsonTester.load(Map.class))
                    .build())
                .compare("response.json", JsonTester.compare(JSONCompareMode.LENIENT))
            .end();
    }
}
