package org.example.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.context.TestContext;

public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║   🚀 STARTING: " + scenario.getName());
        System.out.println("╚══════════════════════════════════════════════════╝\n");
    }

    @After
    public void afterScenario(Scenario scenario) {
        // if the scenario failed, print and attach the response body to the report
        if (scenario.isFailed() && context.getResponse() != null) {
            System.out.println("❌ FAILED — Response Body:");
            System.out.println(context.getResponse().asPrettyString());

            // attaches response to Cucumber HTML report for easy debugging
            scenario.attach(
                    context.getResponse().asPrettyString().getBytes(),
                    "application/json",
                    "Failed Response Body"
            );
        }

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║   " + (scenario.isFailed() ? "❌ FAILED: " : "✅ PASSED: ") + scenario.getName());
        System.out.println("╚══════════════════════════════════════════════════╝\n");
    }
}