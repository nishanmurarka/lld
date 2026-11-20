package bookmyshow.models;

import java.util.Objects;


public class ShowSeat {
    public Show show;
    public Seat seat;

    public ShowSeat(Show show, Seat seat)
    {
        this.show=show;
        this.seat=seat;
    }

    public Seat getSeat()
    {
        return seat;
    }

    @Override 
    public int hashCode(){
        return Objects.hash(show.getId(), seat.getId());
    }

    @Override 
    public boolean equals(Object obj)
    {
        if(this.equals(obj))
            return true;
        if(obj==null || getClass() != obj.getClass()) return false;
        ShowSeat other = (ShowSeat) obj;
        return this.show.getId().equals(other.show.getId()) && this.seat.getId().equals(other.seat.getId());
    }
}
