package vendingmachine.model;
public enum Coin {
    Fifty(50),
    Hundred(100),
    TwoHundred(200),
    FiveHundred(500);

    private final int value;

    Coin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
