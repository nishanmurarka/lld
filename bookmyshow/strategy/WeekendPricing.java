package bookmyshow.strategy;

import java.util.List;
import bookmyshow.models.ShowSeat;

public class WeekendPricing implements PricingStrategy{
    
    @Override 
    public double calculate(List<ShowSeat> showSeats)
    {
        double amount=0.0;
        for(ShowSeat seat: showSeats)
        {
            amount+=(seat.getSeat().getType().getPrice()*1.5);
        }
        return amount;
    }
}
