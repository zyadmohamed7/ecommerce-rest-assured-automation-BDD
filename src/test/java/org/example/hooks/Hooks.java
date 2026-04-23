package org.example.hooks;

import org.example.framework.utils.FrameworkLogger;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.context.TestContext;
import org.example.framework.auth.AuthManager;

public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        FrameworkLogger.info("\n╔══════════════════════════════════════════════════╗");
        FrameworkLogger.info("║   🚀 STARTING: " + scenario.getName());
        FrameworkLogger.info("╚══════════════════════════════════════════════════╝\n");
    }

    @After
    public void afterScenario(Scenario scenario) {
        // if the scenario failed, print and attach the response body to the report
        if (scenario.isFailed() && context.getResponse() != null) {
            FrameworkLogger.error("❌ FAILED — Response Body:");
            FrameworkLogger.error(context.getResponse().asPrettyString());

            // attaches response to Cucumber HTML report for easy debugging
            scenario.attach(
                    context.getResponse().asPrettyString().getBytes(),
                    "application/json",
                    "Failed Response Body"
            );
        }

        // clean up the ThreadLocal token after each scenario
        AuthManager.clear();

        FrameworkLogger.info("\n╔══════════════════════════════════════════════════╗");
        FrameworkLogger.info("║   " + (scenario.isFailed() ? "❌ FAILED: " : "✅ PASSED: ") + scenario.getName());
        FrameworkLogger.info("╚══════════════════════════════════════════════════╝\n");
    }
}