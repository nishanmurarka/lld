package splitwise.factory;

import java.util.List;

import splitwise.model.ExpenseType;
import splitwise.model.User;
import splitwise.model.splits.PercentSplit;
import splitwise.model.splits.Splits;
import splitwise.model.Expense.EqualExpense;
import splitwise.model.Expense.ExactExpense;
import splitwise.model.Expense.Expense;
import splitwise.model.Expense.PercentExpense;

public class ExpenseFactory {
    public Expense createExpense(ExpenseType expenseType, User paidBy, double amount, List<Splits> splits, String description) {
        switch (expenseType) {
            case EXACT:
                return new ExactExpense(paidBy, amount, splits, description);
            case EQUAL:
                int numberOfSplits = splits.size();
                for (Splits split : splits) {
                    split.setAmount((amount * 1.0) / numberOfSplits);
                }
                return new EqualExpense(paidBy, amount, splits, description);
            case PERCENTAGE:
                for(Splits split : splits) {
                    PercentSplit percentSplit = (PercentSplit) split;
                    split.setAmount((amount * percentSplit.getPercent()) / 100.0);
                }
                return new PercentExpense(paidBy, amount, splits, description);
            default:
                throw new IllegalArgumentException("Invalid expense type: " + expenseType);
        }
    }
}
