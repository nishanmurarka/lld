package splitwise.model.splits;

import splitwise.model.User;

public class Splits {
    private final User user;
    private double amount;

    public Splits(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
