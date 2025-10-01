import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class TokenBucketRateLimiter {

    private final long capacity;
    private final double refillRate; // Tokens per second

    private double currentTokens;
    private long lastRefillTimestamp;

    /**
     * Creates an instance of a Token Bucket Rate Limiter.
     *
     * @param capacity   The maximum number of tokens the bucket can hold (controls burstiness).
     * @param refillRate The number of tokens to add to the bucket per second.
     */
    public TokenBucketRateLimiter(long capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.currentTokens = capacity; // Start with a full bucket
        this.lastRefillTimestamp = System.nanoTime();
    }

    /**
     * Attempts to allow a request. This method is thread-safe.
     * It checks for available tokens, consumes one if available, and refills the
     * bucket based on the elapsed time since the last call.
     *
     * @return {@code true} if the request is allowed, {@code false} otherwise.
     */
    public synchronized boolean allowRequest() {
        refill();

        if (currentTokens >= 1) {
            currentTokens--;
            return true;
        } else {
            return false;
        }
    }

    private void refill() {
        long now = System.nanoTime();
        // Use nanoseconds for precision against system clock changes
        long elapsedNanos = now - lastRefillTimestamp;

        // Convert elapsed time to seconds and calculate new tokens
        double newTokens = (elapsedNanos / 1_000_000_000.0) * refillRate;
        
        if (newTokens > 0) {
            currentTokens = Math.min(capacity, currentTokens + newTokens);
            lastRefillTimestamp = now;
        }
    }

    // --- Example Usage ---
    public static void main(String[] args) throws InterruptedException {
        // Create a rate limiter:
        // - Bucket capacity of 10 tokens (allows a burst of 10 requests).
        // - Refills at a rate of 2 tokens per second (allows an average of 2 req/sec).
        TokenBucketRateLimiter apiLimiter = new TokenBucketRateLimiter(10, 2.0);

        System.out.println("--- Simulating a burst of 12 requests ---");
        for (int i = 0; i < 12; i++) {
            boolean isAllowed = apiLimiter.allowRequest();
            System.out.printf("Request %d: %s%n", (i + 1), isAllowed ? "Allowed" : "Denied (Rate Limit Exceeded)");
            Thread.sleep(100); // Simulate requests coming in quickly (10 per second)
        }

        System.out.println("\n--- Waiting for 2 seconds for the bucket to refill ---");
        // 2 seconds * 2 tokens/sec = 4 new tokens should be added
        Thread.sleep(2000); 

        System.out.println("\n--- Simulating 5 more requests ---");
        for (int i = 0; i < 5; i++) {
            boolean isAllowed = apiLimiter.allowRequest();
            System.out.printf("Request %d: %s%n", (i + 1), isAllowed ? "Allowed" : "Denied (Rate Limit Exceeded)");
            Thread.sleep(100);
        }
    }
}
