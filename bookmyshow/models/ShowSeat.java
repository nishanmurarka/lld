package bookmyshow.models;

import java.util.Objects;

import bookmyshow.enums.SeatStatus;


public class ShowSeat {
    private Show show;
    private Seat seat;
    private SeatStatus status;

    public ShowSeat(Show show, Seat seat)
    {
        this.show=show;
        this.seat=seat;
    }

    public Seat getSeat()
    {
        return seat;
    }

    public SeatStatus getStatus(){
        return status;
    }

    public void setStatus(SeatStatus status){
        this.status=status;
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
