package vendingmachine.State;

import vendingmachine.VendingMachine;
import vendingmachine.model.Coin;
import vendingmachine.model.Item;

public class SelectedState implements VendingMachineState {
    @Override
    public void displayProducts(VendingMachine machine) {
        System.out.println("Products already selected. Please wait until the product is dispensed before displaying products.");
    }

    @Override
    public void selectProduct(VendingMachine machine, Item item, int quantity) {
        System.out.println("Product already selected: " + item.getName());
    }

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) {
        System.out.println("Inserting coin of value: " + coin.getValue());
        machine.addBalance(coin.getValue());
    }

    @Override
    public void dispenseProduct(VendingMachine machine) {
        if(machine.getBalance() >= machine.getSelectedItem().getPrice() * machine.getSelectedQuantity()) {
            machine.setState(new DispenseState());
            machine.dispenseProduct();
        } else {
            System.out.println("Insufficient balance. Please insert more coins.");
        }
    }

    @Override
    public void cancelTransaction(VendingMachine machine) {
        machine.setSelectedItem(null);
        System.out.println("Transaction cancelled. Returning to idle state.");
        System.out.println("Returning coins: " + machine.getBalance());
        machine.resetBalance();
    }
    
}
