package Coding.hw11;

// Implement a simple TokenBucketRateLimiter class with the following requirements:
// 1. The class should have two parameters: capacity (maximum tokens) and refillRate (tokens added
// per second)
// 2. Implement a boolean tryAcquire() method that returns true if a token is available, false otherwise
// 3. Use lazy refill strategy (calculate tokens on demand, not with a background thread)
// public class TokenBucketRateLimiter {
//  // TODO: Add fields
//  public TokenBucketRateLimiter(int capacity, int refillRate) {
//  // TODO: Initialize
//  }
//  public synchronized boolean tryAcquire() {
//  // TODO: Implement
//  // 1. Calculate how many tokens should be added since last refill
//  // 2. Add tokens (but not exceeding capacity)
//  // 3. If tokens > 0, consume one and return true
//  // 4. Otherwise return false
//  }
// }

public class TokenBucketRateLimiter {
    private final int capacity;
    private final int refillRate;
    private double tokens;
    private long lastRefillTime;

    public TokenBucketRateLimiter(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();
        double newTokens = (now - lastRefillTime) / 1000.0 * refillRate;

        tokens = Math.min(capacity, tokens + newTokens);
        lastRefillTime = now;

        if (tokens >= 1) {
            tokens -= 1;
            return true;
        }
        return false;
    }
}
