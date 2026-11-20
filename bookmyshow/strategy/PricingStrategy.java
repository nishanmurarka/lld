package bookmyshow.strategy;

import java.util.List;

import bookmyshow.models.ShowSeat;

public interface PricingStrategy {
    public double calculate(List<ShowSeat> showSeats);   
}
