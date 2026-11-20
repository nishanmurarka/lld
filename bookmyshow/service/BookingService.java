package bookmyshow.service;

import java.util.List;

import bookmyshow.models.*;
import bookmyshow.enums.*;
import bookmyshow.repository.BookingRepository;
import bookmyshow.strategy.PricingStrategy;

public class BookingService {
    private PricingStrategy pricingStrategy;
    private BookingRepository repository;
    private LockService lockService;

    public BookingService(PricingStrategy pricingStrategy, BookingRepository repository, LockService lockService)
    {
        this.pricingStrategy=pricingStrategy;
        this.repository=repository;
        this.lockService=lockService;  
    }

    public Booking book(User user, Cinema cinema, Screen screen, Show show, List<ShowSeat> seats)
    {
        for(ShowSeat seat: seats)
            {
                if(seat.getStatus() == SeatStatus.BOOKED)
                    throw new RuntimeException("Booking cant be made, seat already booked");
                lockService.lock(user.getName(), seat);
                seat.setStatus(SeatStatus.LOCKED);  
            }
            double amount = pricingStrategy.calculate(seats);

            return repository.createBooking(user, cinema, screen, show, seats, amount);
        
    }

    public void confirmPayment(String user, Booking booking, boolean confirm )
    {
        if(confirm)
        {
            for(ShowSeat seat: booking.getSeats())
            {
                if(!lockService.validateLock(user, seat))
                    throw new RuntimeException("Lock expired for seat, retry booking from start");
                seat.setStatus(SeatStatus.BOOKED);
            }
            booking.setStatus(BookingStatus.CONFIRMED);
            System.out.println(booking);
        }
        else{
            for(ShowSeat seat: booking.getSeats())
            {
                seat.setStatus(SeatStatus.AVAILABLE);
                lockService.unlock(user, seat);
            }
            booking.setStatus(BookingStatus.CANCELLED);
            System.out.println(booking);
        }
    }

}
