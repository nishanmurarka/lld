package lru;

import lru.localCache.lru_dequeue;

public class cacheService {
    public lru_dequeue localCache;
    public redisCache redisCache;
    public repository repository;

    public long local_ttl = 10_000; // 10 seconds
    public long redis_ttl = 30_000; // 30 seconds

    public cacheService(int capacity) {
        this.localCache = new lru_dequeue(capacity);
        this.redisCache = new redisCache();
        this.repository = new repository();
    }

    public Integer get(int key) {
        // Check local cache first
        Integer value = localCache.get(key);
        if (value != -1) {
            System.out.println("Cache hit in local cache for key: " + key);
            return value;
        }

        // Check Redis cache next
        value = redisCache.get(key);
        if (value != null) {
            System.out.println("Cache hit in Redis for key: " + key);
            // Store in local cache for future requests
            localCache.put(key, value, local_ttl);
            return value;
        }

        // Fetch from repository as a last resort
        value = repository.get(key);
        if (value != null) {
            System.out.println("Fetched from repository for key: " + key);
            // Store in both caches for future requests
            redisCache.put(key, value, redis_ttl);
            localCache.put(key, value, local_ttl);
        }

        return value;
    }

    public void put(int key, int value) {
        
        repository.put(key, value);
        
        redisCache.deleteKey(key);

        localCache.invalidate(key);

        redisCache.publishInvalidation(key);
        
    }
}
