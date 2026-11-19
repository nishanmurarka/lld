package splitwise.model.Expense;

import java.util.List;

import splitwise.model.User;
import splitwise.model.splits.Splits;

public class EqualExpense extends Expense {
    public EqualExpense(User paidBy, double amount, List<Splits> splits, String description) {
        super(paidBy, amount, splits, description);
    }

    @Override 
    public void validate() {
        double totalAmount = 0;
        for (Splits split : this.getSplits()) {
            totalAmount += split.getAmount();
        }
        if (Math.abs(totalAmount - this.getAmount()) > 0.001) { // Allowing a small margin for floating-point comparison
            throw new IllegalArgumentException("Total amount of splits does not match the expense amount.");
        }
    }
    
}
