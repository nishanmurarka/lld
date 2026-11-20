package bookmyshow.enums;

public enum SeatType {
    Silver(100),
    Gold(300),
    Platinum(500);

    private int price;

    SeatType(int price)
    {
        this.price=price;
    }

    public int getPrice()
    {
        return this.price;
    }
}
