package bookmyshow.repository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import bookmyshow.models.*;
public class BookingRepository {
    private Map<String, Booking> bookings;

    public BookingRepository()
    {
        bookings=new ConcurrentHashMap<>();
    }

    public Booking getBooking(String bookingId)
    {
        Booking booking = bookings.get(bookingId);
        if(booking == null)
            throw new RuntimeException("booking not found");
        
        return booking;
    }

    public Booking createBooking(User user, Cinema cinema, Screen screen, Show show, List<ShowSeat> seats, double totalAmount)
    {
        Booking booking = new Booking(user, cinema, screen, show, seats, totalAmount);    
        bookings.putIfAbsent(booking.getId(), booking);
        return booking; 
    }


}
