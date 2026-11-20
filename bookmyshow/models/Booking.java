package bookmyshow.models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import bookmyshow.enums.BookingStatus;

public class Booking {
    private String id;
    private User user;
    private Cinema cinema;
    private Screen screen;
    private Show show;
    private List<ShowSeat> seats;
    private Instant createdAt;
    private double totalAmount;
    private BookingStatus status;

    public Booking(User user, Cinema cinema, Screen screen, Show show, List<ShowSeat> seats, double totalAmount) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.cinema = cinema;
        this.screen = screen;
        this.show = show;
        this.seats = seats;
        this.createdAt = Instant.now();
        this.totalAmount = totalAmount;
        this.status = BookingStatus.PENDING;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public String getId()
    {
        return id;
    }
    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public List<ShowSeat> getSeats() {
        return seats;
    }

    public void setSeats(List<ShowSeat> seats) {
        this.seats = seats;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override 
    public String toString(){
        List<String> seatIds = seats.stream().map(ShowSeat::getSeat).map(Seat::getId).toList();
        return "Booking {id=" + id + ", user=" + user.getName() + ", cinema=" + cinema.getHallName() 
        + ", screen=" + screen.getId() + " + , seats=" + String.join(",", seatIds)
        + ", createdAt=" + createdAt + ", totalAmount=" + totalAmount + ", status=" + status;
    }
}
