package splitwise.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import splitwise.model.Expense.Expense;

public class Group {
    private final String id;
    private final String name;
    private final List<User> members;
    private final List<Expense> expenses;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
        this.members = new CopyOnWriteArrayList<>();
        this.expenses = new CopyOnWriteArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getMembers() {
        return List.copyOf(members);
    }

    public void addMember(User user) {
        if (!members.contains(user)) {
            members.add(user);
        }
    }

    public List<Expense> getExpenses() {
        return List.copyOf(expenses);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
}
