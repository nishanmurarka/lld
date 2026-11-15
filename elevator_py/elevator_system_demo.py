import os
import sys
import time

# Allow running this file directly from any directory
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))

from elevator.controller.elevator_controller import ElevatorController
from elevator.dto.direction import Direction
from elevator.strategy.nearest_elevator_strategy import NearestElevatorStrategy


def main():
    # Initialize system with 3 elevators and the Nearest Elevator Strategy
    controller = ElevatorController(3, NearestElevatorStrategy())

    print("--- Elevator System Booted ---")

    # Simulate Hallway requests
    controller.submit_external_request(5, Direction.UP)
    controller.submit_external_request(2, Direction.DOWN)

    time.sleep(1.0)  # Give elevators time to move

    # Simulate someone getting into Elevator 1 and pressing floor 10
    controller.submit_internal_request(1, 10)

    # Wait to allow threads to process
    time.sleep(10.0)


if __name__ == "__main__":
    main()

