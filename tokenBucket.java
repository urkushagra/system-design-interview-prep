import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class TokenBucket {
    private final long capacity;
    private final long refillTokens;
    private final long refillIntervalMillis;
    private AtomicLong availableTokens;
    private long lastRefillTimestamp;

    public TokenBucket(long capacity, long refillTokens, long refillIntervalMillis) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillIntervalMillis = refillIntervalMillis;
        this.availableTokens = new AtomicLong(capacity);
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    private synchronized void refill() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTimestamp;
        if (elapsed > refillIntervalMillis) {
            long tokensToAdd = (elapsed / refillIntervalMillis) * refillTokens;
            availableTokens.set(Math.min(capacity, availableTokens.get() + tokensToAdd));
            lastRefillTimestamp = now;
        }
    }

    public synchronized boolean allowRequest() {
        refill();
        if (availableTokens.get() > 0) {
            availableTokens.decrementAndGet();
            return true;
        }
        return false;
    }
}

public class RateLimiter {
    private ConcurrentHashMap<String, TokenBucket> userBuckets = new ConcurrentHashMap<>();

    public boolean isAllowed(String userId) {
        userBuckets.putIfAbsent(userId, new TokenBucket(10, 1, 1000));
        return userBuckets.get(userId).allowRequest();
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiter limiter = new RateLimiter();

        for (int i = 0; i < 20; i++) {
            System.out.println("Request " + i + ": " + limiter.isAllowed("user1"));
            Thread.sleep(200);
        }
    }
}
