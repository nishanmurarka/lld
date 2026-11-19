package splitwise.model.Expense;

import java.util.List;

import splitwise.model.User;
import splitwise.model.splits.PercentSplit;
import splitwise.model.splits.Splits;

public class PercentExpense extends Expense {
    public PercentExpense(User paidBy, double amount, List<Splits> splits, String description) {
        super(paidBy, amount, splits, description);
    }

    @Override
    public void validate() {
        double totalPercentage = 0;
        for (Splits split : this.getSplits()) {
            PercentSplit percentSplit = (PercentSplit) split;
            totalPercentage += percentSplit.getPercent();
        }
        if (Math.abs(totalPercentage - 100) > 0.001) { // Allowing a small margin for floating-point comparison
            throw new IllegalArgumentException("Total percentage of splits does not equal 100%.");
        }
    }
    
}
