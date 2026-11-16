package vendingmachine.State;

import vendingmachine.VendingMachine;
import vendingmachine.model.Coin;
import vendingmachine.model.Item;

public class DispenseState implements VendingMachineState {
    @Override
    public void displayProducts(VendingMachine machine) {
        System.out.println("Transaction in progress. Please wait until the product is dispensed before displaying products.");
    }

    @Override
    public void selectProduct(VendingMachine machine, Item item, int quantity) {
        System.out.println("Product already selected: " + item.getName());
    }

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) {
        System.out.println("Transaction in progress. Please wait until the product is dispensed before inserting more coins.");
    }

    @Override
    public void dispenseProduct(VendingMachine machine) {
        Item selectedItem = machine.getSelectedItem();
        int availableQuantity = machine.getInventory().get(selectedItem);
        int selectedQuantity = machine.getSelectedQuantity();
        int balance = machine.getBalance();
        if(balance >= selectedItem.getPrice() * selectedQuantity) {
            System.out.println("Dispensing " + selectedQuantity + " of " + selectedItem.getName());
            machine.deductBalance(selectedItem.getPrice() * selectedQuantity);
            System.out.println("Returning change: " + machine.getBalance());
            machine.updateInventory(selectedItem, availableQuantity - selectedQuantity);
            machine.setState(new IdleState());

        } else {
            System.out.println("Insufficient balance. Please insert more coins.");
        }

    }

    @Override
    public void cancelTransaction(VendingMachine machine) {
        System.out.println("Cannot cancel transaction while dispensing. Please wait until the product is dispensed.");
    }
    
}
