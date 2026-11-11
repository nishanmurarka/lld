package Elevator.Controller;

import java.util.List;

import Elevator.dto.Direction;
import Elevator.dto.Request;
import Elevator.model.Elevator;
import Elevator.strategy.DispatchStrategy;

public class ElevatorController {
    private List<Elevator> elevators;
    private DispatchStrategy strategy;
    public ElevatorController(int count, DispatchStrategy strategy)
    {
        this.strategy=strategy;
        for(int i=0;i<count;i++)
        {
            Elevator elevator = new Elevator(i,0);
            elevators.add(elevator);
            Thread thread = new Thread(elevator);
            thread.start();     
        }    
    }

    public DispatchStrategy getStrategy(){
        return this.strategy;
    }

    public void call(int floor, Direction dir, Integer elevatorId)
    {
        Elevator elevator=null;
        if(elevatorId==null)
        {
            elevator = strategy.dispatch(new Request(floor, dir), elevators);
        }
        else
        {
            for(Elevator elev: elevators)
            {
                if(elev.getId()==(int)elevatorId)
                {
                    elevator=elev;
                    break;
                }
            }
        }

        elevator.addRequest(new Request(floor, dir));
        
        
    }
    

}
