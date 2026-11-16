package vendingmachine;
import java.util.HashMap;

import vendingmachine.Inventory.Inventory;
import vendingmachine.State.IdleState;
import vendingmachine.State.VendingMachineState;
import vendingmachine.model.Coin;
import vendingmachine.model.Item;

public class VendingMachine {
    private Inventory inventory;
    private int balance;
    private int quantity;
    private Item selectedItem;
    private VendingMachineState state;

    public VendingMachine(Inventory inventory) {
        this.inventory = inventory;
        this.balance = 0;
        this.quantity = 0;
        this.selectedItem = null;
        this.state = new IdleState();
    }

    public void displayProducts() {
        state.displayProducts(this);
    }

    public void selectProduct(Item item, int quantity) {
        state.selectProduct(this, item, quantity);
    }

    public void insertCoin(Coin coin) {
        state.insertCoin(this, coin);
    }

    public void dispenseProduct() {
        state.dispenseProduct(this);
    }

    public void cancelTransaction() {
        state.cancelTransaction(this);
    }

    public void setState(VendingMachineState state) {
        this.state = state;
    }

    public HashMap<Item, Integer> getInventory() {
        return inventory.getInventory();
    }

    public int getSelectedQuantity() {
        return quantity;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public int getBalance() {
        return balance;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setSelectedQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void deductBalance(int amount) {
        this.balance -= amount;
    }

    public void addBalance(int amount) {
        this.balance += amount;
    }

    public void resetBalance() {
        this.balance = 0;
    }

    public void updateInventory(Item item, int quantity) {
        inventory.updateProductQuantity(item, quantity);
    }

}