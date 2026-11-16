package vendingmachine.Inventory;

import java.util.HashMap;

import vendingmachine.model.Item;

public class Inventory {
    private final int MAX_CAPACITY = 10;
    private final HashMap<Item, Integer> inventory;

    public Inventory() {
        this.inventory = new HashMap<>();
    }

    public void addProduct(Item item, int quantity) {
        if (inventory.size() < MAX_CAPACITY) {
            inventory.put(item, quantity);
        } else {
            throw new IllegalStateException("Inventory is full. Cannot add more products.");
        }
    }

    public void removeProduct(Item item, int quantity) {
        if (inventory.containsKey(item)) {
            int currentQuantity = inventory.get(item);
            if (currentQuantity >= quantity) {
                if (currentQuantity == quantity) {
                    inventory.remove(item);
                } else {
                    inventory.put(item, currentQuantity - quantity);
                }
            }
        }
        else {
            throw new IllegalArgumentException("Product not found in inventory.");
        }
    }

    public void updateProductQuantity(Item item, int quantity) {
        inventory.put(item, quantity);
    }

    public int getProductQuantity(Item item) {
        return inventory.getOrDefault(item, 0);
    }

    public HashMap<Item, Integer> getInventory() {
        return new HashMap<>(inventory);
    }

}
