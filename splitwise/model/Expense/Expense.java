package splitwise.model.Expense;

import java.util.List;

import splitwise.model.User;
import splitwise.model.splits.Splits;

public class Expense {
    private User paidBy;
    private double amount;
    private List<Splits> splits;
    private String description;

    public Expense(User paidBy, double amount, List<Splits> splits, String description) {
        this.paidBy = paidBy;
        this.amount = amount;
        this.splits = splits;
        this.description = description;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public double getAmount() {
        return amount;
    }

    public List<Splits> getSplits() {
        return splits;
    }

    public String getDescription() {
        return description;
    }

    public void validate(){};

    
}
