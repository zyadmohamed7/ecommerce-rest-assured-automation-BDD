package org.example.framework.utils;

import io.restassured.response.Response;
import java.util.concurrent.Callable;

public class RetryHandler {

    /**
     * Executes a task with a retry logic.
     * @param task The task to execute (e.g. an API call).
     * @param maxRetries Maximum number of attempts.
     * @param retryDelayMs Delay between retries after a failure.
     * @return The response from the task.
     */
    public static Response handle(Callable<Response> task, int maxRetries, int retryDelayMs) {
        int currentAttempt = 0;

        while (currentAttempt < maxRetries) {
            try {
                // Execute the task IMMEDIATELY without waiting
                Response response = task.call();
                
                // If we get a 5xx Server Error, treat it as a failure so we can retry!
                if (response != null && response.statusCode() >= 500) {
                    throw new RuntimeException("Server returned HTTP " + response.statusCode());
                }
                
                return response;

            } catch (Exception e) {
                currentAttempt++;
                if (currentAttempt >= maxRetries) {
                    throw new RuntimeException("Action failed after " + maxRetries + " attempts due to: " + e.getMessage());
                }
                FrameworkLogger.warn("⚠️ API Failure: " + e.getMessage() + ". Retrying in " + retryDelayMs + "ms... (Attempt " + currentAttempt + ")");
                try {
                    Thread.sleep(retryDelayMs); // Only sleep AFTER a failure
                } catch (InterruptedException ignored) {}
            }
        }
        return null;
    }
}
