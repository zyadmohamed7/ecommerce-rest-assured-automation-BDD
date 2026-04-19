package org.example.runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "org.example.stepdefinitions, org.example.hooks"
)

@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-reports/report.html, json:target/cucumber-reports/report.json"
)

@ConfigurationParameter(
        key = FILTER_TAGS_PROPERTY_NAME,
        value = "@regression or @smoke"
)

@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, " +
                "html:target/cucumber-reports/report.html, " +
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
)
public class TestRunner {
    // empty — Cucumber handles everything via the annotations above
}