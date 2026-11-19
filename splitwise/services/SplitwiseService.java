package splitwise.services;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import splitwise.model.ExpenseType;
import splitwise.model.Group;

import splitwise.model.User;
import splitwise.model.Expense.Expense;
import splitwise.model.splits.Splits;
import splitwise.factory.ExpenseFactory;

public class SplitwiseService {
    Map<String, Map<String, Double>> balances;
    Map<String, User> users;
    Map<String, Group> groups;
    ExpenseFactory expenseFactory;

    public SplitwiseService() {
        this.balances = new ConcurrentHashMap<>();
        this.users = new ConcurrentHashMap<>();
        this.groups = new ConcurrentHashMap<>();
        this.expenseFactory = new ExpenseFactory();
    }

    public User createUser(String name) {
        User user = new User(UUID.randomUUID().toString(), name);
        users.put(user.getName(), user);
        return user;
    }

    public Group createGroup(String name, List<User> members) {
        Group group = new Group(UUID.randomUUID().toString(), name);
        groups.put(group.getName(), group);
        return group;
    }

    public void addMemberToGroup(String groupName, String userName) {
        Group group = groups.get(groupName);
        User user = users.get(userName);
        if (group != null && user != null) {
            group.addMember(user);
        }
    }

    public void addExpense(String groupName, String paidByUserName, double amount, ExpenseType expenseType, List<Splits> splits, String description) {
        Group group = groups.get(groupName);
        User paidByUser = users.get(paidByUserName);
        if (group == null || paidByUser == null) {
            throw new IllegalArgumentException("Group or User not found");
        }

        Expense expense = expenseFactory.createExpense(expenseType, paidByUser, amount, splits, description);
        try{
            
            expense.validate();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Expense validation failed: " + e.getMessage());
        }

        List<Splits> expenseSplits = expense.getSplits();
        for (Splits split : expenseSplits)
        {
            String owedToUserName = split.getUser().getName();
            double owedAmount = split.getAmount();

            balances.putIfAbsent(paidByUserName, new ConcurrentHashMap<>());
            balances.putIfAbsent(owedToUserName, new ConcurrentHashMap<>());

            if(paidByUserName.equals(owedToUserName)) {
                continue; // Skip if the user owes themselves
            }

            balances.get(paidByUserName).put(owedToUserName, balances.get(paidByUserName).getOrDefault(owedToUserName, 0.0) + owedAmount);
            balances.get(owedToUserName).put(paidByUserName, balances.get(owedToUserName).getOrDefault(paidByUserName, 0.0) - owedAmount);
        }

        group.addExpense(expense);

    }

    public void showBalances() {
        for (String user1 : balances.keySet()) {
            for (String user2 : balances.get(user1).keySet()) {
                double amount = balances.get(user1).get(user2);
                if (amount > 0) {
                    System.out.println(user1 + " owes " + user2 + ": " + amount);
                }
            }
        }
    }

    
    
}
