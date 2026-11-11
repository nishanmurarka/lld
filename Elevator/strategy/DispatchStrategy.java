package Elevator.strategy;

import java.util.List;

import Elevator.dto.Request;
import Elevator.model.Elevator;

public interface DispatchStrategy {
    public Elevator dispatch(Request req, List<Elevator> elevators);
}
