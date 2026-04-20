package org.example.framework.utils;

import io.restassured.response.Response;
import java.util.concurrent.Callable;

public class RetryHandler {

    /**
     * Executes a task with a retry logic.
     * @param task The task to execute (e.g. an API call).
     * @param maxRetries Maximum number of attempts.
     * @param baselineDelayMs Initial delay before every call.
     * @param retryDelayMs Delay between retries after a failure.
     * @return The response from the task.
     */
    public static Response handle(Callable<Response> task, int maxRetries, int baselineDelayMs, int retryDelayMs) {
        int currentAttempt = 0;

        while (currentAttempt < maxRetries) {
            try {
                // Baseline breather
                Thread.sleep(baselineDelayMs);

                return task.call();

            } catch (Exception e) {
                currentAttempt++;
                if (currentAttempt >= maxRetries) {
                    throw new RuntimeException("Action failed after " + maxRetries + " attempts due to: " + e.getMessage());
                }
                FrameworkLogger.warn("⚠️ Connection/Server Error. Retrying in " + retryDelayMs + "ms... (Attempt " + currentAttempt + ")");
                try {
                    Thread.sleep(retryDelayMs);
                } catch (InterruptedException ignored) {}
            }
        }
        return null;
    }
}
