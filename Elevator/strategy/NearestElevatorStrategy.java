package Elevator.strategy;

import java.util.List;

import Elevator.dto.Direction;
import Elevator.dto.Request;
import Elevator.model.Elevator;

public class NearestElevatorStrategy implements DispatchStrategy{
    public Elevator dispatch(Request req, List<Elevator> elevators )
    {
        Elevator optimal=null;
        int min=Integer.MAX_VALUE;
        for( Elevator elevator: elevators)
        {
            int elevatorFloor = elevator.getCurrentFloor();
            int targetFloor = req.getTargetFloor();
            if(elevator.getDirection()==req.getDirection() || elevator.getDirection()==Direction.IDLE)
            {
                if(Math.abs(elevatorFloor-targetFloor)<min)
                {
                    min=Math.abs(elevatorFloor-targetFloor);
                    optimal=elevator;
                }
            }

        }
        if(optimal==null)
            optimal=elevators.get(0);
        return optimal;
    }
}
