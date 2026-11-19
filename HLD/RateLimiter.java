package HLD;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RateLimiter {
    public static void main(String[] args) {
        Ratelimiter_Bucket bucket = new Ratelimiter_Bucket(5, 3); // Capacity of 5 tokens, refills 1 token per second
        try{
            // Simulate requests
            Thread.sleep(500); // Wait for 1 second to allow some tokens to refill
            for (int i = 1; i <= 4; i++) {
                if(bucket.allowRequest(null)) {
                    System.out.println("Request " + i + " allowed.");
                } else {
                    System.out.println("Request " + i + " denied.");
                }
            }
            Thread.sleep(2000); // Wait for 2 seconds to allow some tokens to refill
            for (int i = 5; i <= 20; i++) {
                if(i==11)
                    Thread.sleep(1000);
                if(bucket.allowRequest(null)) {
                    System.out.println("Request " + i + " allowed.");
                } else {
                    System.out.println("Request " + i + " denied.");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bucket.shutdown();
        }
    }
}

class Ratelimiter_Bucket {
    private int capacity;
    private int tokens;
    private final int refillRate;
    private ScheduledExecutorService executor;

    public Ratelimiter_Bucket(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::refill, 0, 1, TimeUnit.SECONDS);
    }

    private synchronized void refill() {
        tokens = Math.min(tokens + refillRate, capacity);
    }

    public synchronized boolean allowRequest(String userId) {
        // Implement the logic to check if the request is allowed based on the token
        // bucket algorithm
        if (tokens <= 0)
            return false;
        tokens--;
        return true;
    }

    public void shutdown() {
        executor.shutdown();
    }

}
