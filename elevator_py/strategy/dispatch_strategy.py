from abc import ABC, abstractmethod
from typing import List, TYPE_CHECKING

if TYPE_CHECKING:
    from elevator.model.elevator import Elevator
    from elevator.dto.request import Request


class DispatchStrategy(ABC):
    """
    Interface for Dispatch logic. Open for extension.
    """

    @abstractmethod
    def select_optimal_elevator(self, elevators: List["Elevator"], request: "Request") -> "Elevator":
        pass
