package Elevator.model;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import Elevator.dto.Direction;
import Elevator.dto.Request;

public class Elevator implements Runnable{
    private int id;
    private AtomicInteger currentFloor;
    private AtomicReference<Direction> direction;
    private ConcurrentSkipListSet<Integer> upRequests;
    private ConcurrentSkipListSet<Integer> downRequests;

    public Elevator(int id, int currentFloor)
    {
        this.id=id;
        this.currentFloor=new AtomicInteger(currentFloor);
        this.direction=new AtomicReference<>(Direction.IDLE);
        this.upRequests=new ConcurrentSkipListSet<>();
        this.downRequests=new ConcurrentSkipListSet<>();
    }
    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor.get();
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor.set(currentFloor);
    }

    public Direction getDirection() {
        return direction.get();
    }

    public void setDirection(Direction direction) {
        this.direction.set(direction);
    }

    public void addRequest(Request request)
    {
        if(request.getDirection()==Direction.UP)
            upRequests.add(request.getTargetFloor());
        else
            downRequests.add(request.getTargetFloor());

        if (direction.get()==Direction.IDLE)
        {
            direction.compareAndSet(Direction.IDLE, (request.getTargetFloor()>currentFloor.get())? Direction.UP: Direction.DOWN);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (direction.get() == Direction.UP || direction.get() == Direction.IDLE) {
                    processUpRequests();
                    processDownRequests();
                } else {
                    processDownRequests();
                    processUpRequests();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void processUpRequests() throws InterruptedException
    {
        while(!upRequests.isEmpty())
        {
            int nextFloor=upRequests.pollFirst();
            if (nextFloor>currentFloor.get())
            {
                System.out.println("Elevator "+id+" is moving up from "+currentFloor.get()+" to "+nextFloor);
                Thread.sleep((nextFloor-currentFloor.get())*1000);
                currentFloor.set(nextFloor);
                System.out.println("Elevator Opening at "+currentFloor.get());
            }
            else{
                downRequests.add(nextFloor);
            }

        }
        if(!downRequests.isEmpty())
            direction.set(Direction.DOWN);
    }

    public void processDownRequests() throws InterruptedException
    {
        while(!downRequests.isEmpty())
        {
            int nextFloor=downRequests.pollLast();
            if (nextFloor<currentFloor.get())
            {
                System.out.println("Elevator "+id+" is moving down from "+currentFloor.get()+" to "+nextFloor);
                Thread.sleep((currentFloor.get()-nextFloor)*1000);
                currentFloor.set(nextFloor);
                System.out.println("Elevator Opening at "+currentFloor.get());
            }
            else{
                upRequests.add(nextFloor);
            }

        }
        if(!upRequests.isEmpty())
            direction.set(Direction.UP);
    }
    

}
