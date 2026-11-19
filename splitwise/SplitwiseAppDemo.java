package splitwise;

import java.util.List;


import splitwise.model.ExpenseType;
import splitwise.model.User;
import splitwise.model.splits.ExactSplit;
import splitwise.model.splits.PercentSplit;
import splitwise.model.splits.Splits;
import splitwise.services.SplitwiseService;

public class SplitwiseAppDemo {
    public static void main(String[] args) {
        System.out.println("Welcome to Splitwise App!");
        SplitwiseService splitwiseService = new SplitwiseService();
        User user1 = splitwiseService.createUser("Alice");
        User user2 = splitwiseService.createUser("Bob");
        User user3 = splitwiseService.createUser("Charlie");

        splitwiseService.createGroup("Friends", List.of(user1, user2, user3));

        splitwiseService.addExpense("Friends", "Alice", 300, ExpenseType.EQUAL, List.of(new Splits(user2), new Splits(user3)), "Dinner");
        splitwiseService.addExpense("Friends", "Bob", 200, ExpenseType.EXACT, List.of(new ExactSplit(user1, 100), new ExactSplit(user3, 100)), "Movie Tickets");
        splitwiseService.addExpense("Friends", "Charlie", 500, ExpenseType.PERCENTAGE, List.of(new PercentSplit(user1, 50), new PercentSplit(user2, 25), new PercentSplit(user3, 25)), "Concert Tickets");

        splitwiseService.showBalances();


    }
}
