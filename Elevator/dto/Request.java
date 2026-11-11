package Elevator.dto;

public class Request {
    private int targetFloor;
    private Direction direction;

    public Request(int targetFloor, Direction direction)
    {
        this.targetFloor = targetFloor;
        this.direction = direction;
    }
    public int getTargetFloor()
    {
        return  this.targetFloor;
    }

    public Direction getDirection()
    {
        return this.direction;
    }
}
