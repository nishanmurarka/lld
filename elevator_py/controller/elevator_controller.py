import sys
from typing import List, Optional
from elevator.dto.direction import Direction
from elevator.dto.request import Request
from elevator.model.elevator import Elevator
from elevator.strategy.dispatch_strategy import DispatchStrategy


class ElevatorController:
    """
    Central system controller. Delegates tasks.
    """

    def __init__(self, num_elevators: int, strategy: DispatchStrategy):
        self.elevators: List[Elevator] = []
        self.dispatch_strategy = strategy

        for i in range(1, num_elevators + 1):
            elevator = Elevator(i)
            self.elevators.append(elevator)
            elevator.start()  # Start elevator threads

    # Allows dynamic changing of algorithm at runtime (e.g., Rush hour vs Normal hour)
    def set_dispatch_strategy(self, strategy: DispatchStrategy) -> None:
        self.dispatch_strategy = strategy

    # Called when a user presses a button in the hallway
    def submit_external_request(self, floor: int, direction: Direction) -> None:
        req = Request(floor, direction)
        optimal_elevator = self.dispatch_strategy.select_optimal_elevator(self.elevators, req)
        print(f"Dispatcher assigned Elevator {optimal_elevator.get_id()} to floor {floor}")
        optimal_elevator.add_request(req)

    # Called when a user presses a button inside the elevator
    def submit_internal_request(self, elevator_id: int, target_floor: int) -> None:
        optional_elevator: Optional[Elevator] = next(
            (e for e in self.elevators if e.get_id() == elevator_id), None
        )

        if optional_elevator is not None:
            direction = (
                Direction.UP
                if target_floor > optional_elevator.get_current_floor()
                else Direction.DOWN
            )
            optional_elevator.add_request(Request(target_floor, direction))
        else:
            print(f"Error: Elevator with ID {elevator_id} not found.", file=sys.stderr)

