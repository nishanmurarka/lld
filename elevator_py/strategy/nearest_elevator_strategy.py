import sys
from typing import List, TYPE_CHECKING
from dto import Direction
from dto import Request
from strategy import DispatchStrategy

if TYPE_CHECKING:
    from model import Elevator


class NearestElevatorStrategy(DispatchStrategy):
    """
    Concrete Strategy: Finds the nearest elevator moving in the same direction or idle.
    """

    def select_optimal_elevator(self, elevators: List["Elevator"], request: Request) -> "Elevator":
        best_elevator = None
        min_distance = sys.maxsize

        for elevator in elevators:
            distance = abs(elevator.get_current_floor() - request.get_requested_floor())
            e_dir = elevator.get_current_direction()

            # Check if elevator is idle, or moving towards the request in the same direction
            is_moving_towards = (
                (e_dir == Direction.UP and elevator.get_current_floor() <= request.get_requested_floor())
                or (e_dir == Direction.DOWN and elevator.get_current_floor() >= request.get_requested_floor())
            )

            if (e_dir == Direction.IDLE or is_moving_towards) and distance < min_distance:
                min_distance = distance
                best_elevator = elevator

        # Fallback: If no ideal elevator found, just pick the first one (in production, we'd queue the request)
        return best_elevator if best_elevator is not None else elevators[0]
