package bookmyshow;

import java.util.Arrays;
import java.util.List;
import bookmyshow.enums.SeatType;
import bookmyshow.models.Booking;
import bookmyshow.models.Cinema;
import bookmyshow.models.Screen;
import bookmyshow.models.Seat;
import bookmyshow.models.Show;
import bookmyshow.models.ShowSeat;
import bookmyshow.models.User;
import bookmyshow.repository.BookingRepository;
import bookmyshow.service.BookingService;
import bookmyshow.service.LockService;
import bookmyshow.strategy.PricingStrategy;
import bookmyshow.strategy.WeekendPricing;

public class BMSAppDemo {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     MOVIE BOOKING SYSTEM - END TO END DEMO       ");
        System.out.println("==================================================\n");

        // ----------------------------------------------------
        // 1. INITIALIZE SYSTEM DATA & DEPENDENCIES
        // ----------------------------------------------------
        User alice = new User("Alice", "alice@example.com");
        User bob = new User("Bob", "bob@example.com");

        Seat seat1 = new Seat("S1", SeatType.Silver);
        Seat seat2 = new Seat("S2", SeatType.Gold);
        Seat seat3 = new Seat("S3", SeatType.Platinum);

        Screen screen = new Screen("SCR1", "Screen 1", Arrays.asList(seat1, seat2, seat3));
        Cinema cinema = new Cinema("PVR IMAX",List.of(screen));

        Show show = new Show("Inception", System.currentTimeMillis(), System.currentTimeMillis() + 9000000, cinema, screen);

        // Map physical seats to show seats
        ShowSeat showSeat1 = new ShowSeat(show, seat1);
        ShowSeat showSeat2 = new ShowSeat(show, seat2);
        ShowSeat showSeat3 = new ShowSeat(show, seat3);

        // System Providers & Services setup
        LockService lockProvider = new LockService(); 
        PricingStrategy pricingStrategy = new WeekendPricing();
        BookingRepository repository = new BookingRepository();
        BookingService bookingService = new BookingService(pricingStrategy, repository, lockProvider);

        // ----------------------------------------------------
        // 2. SCENARIO 1: ALICE SUCCESSFULLY BOOKS SEATS SS1 & SS2
        // ----------------------------------------------------
        System.out.println("--- SCENARIO 1: Alice creates a booking ---");
        List<ShowSeat> aliceSeats = Arrays.asList(showSeat1, showSeat2);

        Booking aliceBooking = bookingService.book(alice, cinema, screen, show, aliceSeats);
        System.out.println("Booking Created for Alice!");
        System.out.println("Booking ID     : " + aliceBooking.getId());
        System.out.println("Total Amount   : $" + aliceBooking.getTotalAmount());
        System.out.println("Booking Status : " + aliceBooking.getStatus());
        System.out.println();

        System.out.println("--- Alice completes payment ---");
        bookingService.confirmPayment(alice.getName(), aliceBooking,true);
        System.out.println("Payment Successful!");
        System.out.println("Booking Status : " + aliceBooking.getStatus());
        System.out.println("Seat 1 Status  : " + bookingService.showStatus(showSeat1));
        System.out.println("Seat 2 Status  : " + bookingService.showStatus(showSeat2));
        System.out.println();

        // ----------------------------------------------------
        // 2. SCENARIO 2: BOB Tries to book SS3 hold by Alice
        // ----------------------------------------------------

        aliceSeats=Arrays.asList(showSeat3);
        aliceBooking = bookingService.book(alice, cinema, screen, show, aliceSeats);
        System.out.println("Booking Created for Alice!");
        System.out.println("Booking ID     : " + aliceBooking.getId());
        System.out.println("Total Amount   : $" + aliceBooking.getTotalAmount());
        System.out.println("Booking Status : " + aliceBooking.getStatus());
        System.out.println();
        try{
            Thread.sleep(1000);
            List<ShowSeat> bobSeats=Arrays.asList(showSeat3);
            Booking bobBooking = bookingService.book(bob, cinema, screen, show, bobSeats);
            System.out.println("Booking Created for Bob!");
            System.out.println("Booking ID     : " + bobBooking.getId());
            System.out.println("Total Amount   : $" + bobBooking.getTotalAmount());
            System.out.println("Booking Status : " + bobBooking.getStatus());
            System.out.println();
        } catch(Exception e)
        {
            System.err.println(e.getMessage());
        }

        try{
            Thread.sleep(11000);
            List<ShowSeat> bobSeats=Arrays.asList(showSeat3);
            Booking bobBooking = bookingService.book(bob, cinema, screen, show, bobSeats);
            System.out.println("Booking Created for Bob!");
            System.out.println("Booking ID     : " + bobBooking.getId());
            System.out.println("Total Amount   : $" + bobBooking.getTotalAmount());
            System.out.println("Booking Status : " + bobBooking.getStatus());
            System.out.println();
        } catch(Exception e)
        {
            System.err.println(e.getMessage());
        }



        
    }
}
