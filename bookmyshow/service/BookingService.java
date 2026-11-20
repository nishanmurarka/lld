package bookmyshow.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import bookmyshow.models.*;
import bookmyshow.enums.*;
import bookmyshow.repository.BookingRepository;
import bookmyshow.strategy.PricingStrategy;

public class BookingService {
    private PricingStrategy pricingStrategy;
    private BookingRepository repository;
    private LockService lockService;

    Map<ShowSeat, SeatStatus> seatStatus;

    public BookingService(PricingStrategy pricingStrategy, BookingRepository repository, LockService lockService)
    {
        this.pricingStrategy=pricingStrategy;
        this.repository=repository;
        this.lockService=lockService;
        seatStatus = new ConcurrentHashMap<>();     
    }

    public Booking book(User user, Cinema cinema, Screen screen, Show show, List<ShowSeat> seats)
    {
        for(ShowSeat seat: seats)
            {
                if(lockService.isLocked(seat) || seatStatus.get(seat) == SeatStatus.BOOKED)
                    throw new RuntimeException("Booking cant be made, seat already booked");
                lockService.lock(user.getName(), seat);
                seatStatus.put(seat, SeatStatus.LOCKED);  
            }
            double amount = pricingStrategy.calculate(seats);

            return repository.createBooking(user, cinema, screen, show, seats, amount);
        
    }

    public void confirmPayment(String user, Booking booking, boolean confirm )
    {
        if(confirm)
        {
            for(ShowSeat seats: booking.getSeats())
            {
                seatStatus.put(seats, SeatStatus.BOOKED);
            }
            booking.setStatus(BookingStatus.CONFIRMED);
            System.out.println(booking);
        }
        else{
            for(ShowSeat seats: booking.getSeats())
            {
                seatStatus.put(seats, SeatStatus.AVAILABLE);
                lockService.unlock(user, seats);
            }
            booking.setStatus(BookingStatus.CANCELLED);
            System.out.println(booking);
        }
    }

    public String showStatus(ShowSeat seat)
    {
        return seatStatus.get(seat).toString();
    }

}
