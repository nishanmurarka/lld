package vendingmachine.State;

import java.util.HashMap;

import vendingmachine.VendingMachine;
import vendingmachine.model.Coin;
import vendingmachine.model.Item;

public class IdleState implements VendingMachineState {
    
    public void displayProducts(VendingMachine machine) {
        HashMap<Item, Integer> products = machine.getInventory();

        products.forEach((item, quantity) -> {
            System.out.println("Item: " + item.getName() + ", Price: " + item.getPrice() + ", Quantity: " + quantity);
        });
    }

    public void selectProduct(VendingMachine machine, Item item, int quantity) {
        int availableQuantity = machine.getInventory().get(item);
        if (availableQuantity >= quantity) {
            System.out.println("Selected Item: " + item.getName() + ", Price: " + item.getPrice());
            machine.setSelectedItem(item);
            machine.setSelectedQuantity(quantity);
            machine.setState(new SelectedState());
        } else {
            System.out.println("Item not available.");
        }
    }

    public void insertCoin(VendingMachine machine, Coin coin) {
        System.out.println("Please select a product first before inserting money.");
    }

    public void dispenseProduct(VendingMachine machine) {
        System.out.println("Please select a product first before dispensing.");
    }

    public void cancelTransaction(VendingMachine machine) {
        System.out.println("No transaction to cancel.");
    }
}
