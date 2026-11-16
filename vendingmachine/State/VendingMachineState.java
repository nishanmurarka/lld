package vendingmachine.State;

import vendingmachine.VendingMachine;
import vendingmachine.model.Coin;
import vendingmachine.model.Item;

public interface VendingMachineState {
    
    public void displayProducts(VendingMachine machine);
    public void selectProduct(VendingMachine machine, Item item, int quantity);
    public void insertCoin(VendingMachine machine, Coin coin);
    public void dispenseProduct(VendingMachine machine);
    public void cancelTransaction(VendingMachine machine);

}
