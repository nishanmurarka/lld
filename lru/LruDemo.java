package lru;

public class LruDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("LRU Cache Demo with Lazy Expiry");
        lru_lazy cache = new lru_lazy(2);

        cache.put(1, 100, 12_000); // expires after 1 second
        cache.put(2, 200, 1_000);

        System.out.println("key 1 before expiry: " + cache.get(1));
        System.out.println("key 2 before expiry: " + cache.get(2));

        Thread.sleep(7_100);

        cache.put(3, 300, 2_000);

        System.out.println("key 1: " + cache.get(1));
        System.out.println("key 2: " + cache.get(2));
        System.out.println("key 3: " + cache.get(3));
        
        Thread.sleep(15_000);
        cache.shutdown(); // Stop the cleanup thread
    }
}
