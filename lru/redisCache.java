package lru;

public class redisCache {
    public Integer get(int key) {
        // Simulate fetching from Redis
        System.out.println("Fetching from Redis for key: " + key);
        return null; // Return null to simulate cache miss
    }

    public void put(int key, int value, long ttl) {
        // Simulate storing in Redis
        System.out.println("Storing in Redis: " + key + " -> " + value);
    }

    public void deleteKey(int key) {
        // Simulate deleting a key from Redis
        System.out.println("Deleting key from Redis: " + key);
    }

    public void publishInvalidation(int key) {
        // Simulate publishing an invalidation message to Redis
        System.out.println("Publishing invalidation for key: " + key);
    }
}
