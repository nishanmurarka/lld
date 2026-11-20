package bookmyshow.models;

import java.util.List;

public class Screen {
    private String id;
    private String name;
    private List<Seat> seats;

    public Screen(String id, String name, List<Seat> seats)
    {
        this.id=id;
        this.name=name;
        this.seats=seats;
    }
    public void addSeat(Seat seat)
    {
       if(!seats.contains(seat))
        seats.add(seat); 
    }

    public String getId()
    {
        return id;
    }
    
}
