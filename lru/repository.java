package lru;

public class repository {
    public Integer get(int key) {
        // Simulate fetching from repository
        System.out.println("Fetching from repository for key: " + key);
        return 42; // Return a dummy value
    }

    public void put(int key, int value) {
        // Simulate storing in repository
        System.out.println("Storing in repository: " + key + " -> " + value);
    }
}
