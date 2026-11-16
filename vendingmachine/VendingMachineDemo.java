package vendingmachine;
import java.util.concurrent.locks.ReentrantLock;

import vendingmachine.Inventory.Inventory;
import vendingmachine.model.Coin;
import vendingmachine.model.Item;

public class VendingMachineDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Inventory inventory = new Inventory();
        VendingMachine machine = new VendingMachine(inventory);

        Item item1 = new Item("Coke", 100);
        Item item2 = new Item("Pepsi", 200);

        machine.updateInventory(item1, 5);
        machine.updateInventory(item2, 3);
        
        try {
            lock.lock();
            machine.displayProducts();
            Thread.sleep(4000);
            machine.selectProduct(item1, 1);
            Thread.sleep(1000);
            machine.insertCoin(Coin.Fifty);
            Thread.sleep(1000);
            machine.insertCoin(Coin.Fifty);
            Thread.sleep(1000);
            machine.dispenseProduct();

            Thread.sleep(4000);

            machine.displayProducts();
            machine.selectProduct(item2, 2);
            machine.insertCoin(Coin.FiveHundred);
            machine.dispenseProduct();
            Thread.sleep(1000);
            machine.displayProducts();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        

    }
}
