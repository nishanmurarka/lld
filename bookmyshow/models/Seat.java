package bookmyshow.models;

import bookmyshow.enums.SeatType;

public class Seat {
    private String id;
    private SeatType type;

    public Seat(String id, SeatType type)
    {
        this.id = id;
        this.type = type;
    }

    public String getId()
    {
        return this.id;
    }

    public SeatType getType()
    {
        return type;
    }

}
