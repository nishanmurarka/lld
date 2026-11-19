package splitwise.model.splits;

import splitwise.model.User;

public class ExactSplit extends Splits {
    public ExactSplit(User user, double amount) {
        super(user);
        this.setAmount(amount);
    }
    
}
