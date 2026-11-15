import threading
import time
from elevator.dto.direction import Direction
from elevator.dto.request import Request


class Elevator(threading.Thread):
    """
    Represents a single Elevator. Runs on its own thread.
    Only responsible for its own movement and processing its internal stops.
    """

    def __init__(self, elevator_id: int):
        super().__init__(name=f"Elevator-{elevator_id}", daemon=True)
        self._id = elevator_id
        self._current_floor = 0  # Ground floor
        self._current_direction = Direction.IDLE

        # Thread-safe sets to store floor requests:
        # up_requests: natural order (lowest floor first)
        # down_requests: reverse order (highest floor first)
        self._up_requests = set()
        self._down_requests = set()
        self._lock = threading.Lock()
        self._running = True

    # Called by internal buttons or the Dispatcher
    def add_request(self, request: Request) -> None:
        with self._lock:
            if request.get_direction() == Direction.UP:
                self._up_requests.add(request.requested_floor)
            else:
                self._down_requests.add(request.get_requested_floor())

            # Wake up elevator if it was idle
            if self._current_direction == Direction.IDLE:
                self._current_direction = (
                    Direction.UP
                    if request.get_requested_floor() > self._current_floor
                    else Direction.DOWN
                )

    def run(self) -> None:
        while self._running:
            try:
                self._process_requests()
                time.sleep(1.0)  # Simulate time taken to move floors
            except Exception:
                break

    def stop(self) -> None:
        self._running = False

    def _process_requests(self) -> None:
        with self._lock:
            direction = self._current_direction

        if direction == Direction.UP or direction == Direction.IDLE:
            self._process_up_requests()
            self._process_down_requests()
        else:
            self._process_down_requests()
            self._process_up_requests()

        with self._lock:
            if not self._up_requests and not self._down_requests:
                self._current_direction = Direction.IDLE

    def _process_up_requests(self) -> None:
        while True:
            next_floor = None
            with self._lock:
                if self._up_requests:
                    next_floor = min(self._up_requests)  # Get lowest floor
                    self._up_requests.remove(next_floor)

            if next_floor is None:
                break

            self._move_to_floor(next_floor)

        with self._lock:
            if self._down_requests:
                self._current_direction = Direction.DOWN

    def _process_down_requests(self) -> None:
        while True:
            next_floor = None
            with self._lock:
                if self._down_requests:
                    next_floor = max(self._down_requests)  # Get highest floor
                    self._down_requests.remove(next_floor)

            if next_floor is None:
                break

            self._move_to_floor(next_floor)

        with self._lock:
            if self._up_requests:
                self._current_direction = Direction.UP

    def _move_to_floor(self, target_floor: int) -> None:
        print(f"Elevator {self._id} moving from {self._current_floor} to {target_floor}")
        time.sleep(abs(self._current_floor - target_floor) * 0.5)  # Simulate movement delay
        self._current_floor = target_floor
        print(f"Elevator {self._id} opened doors at floor {target_floor}")

    @property
    def id(self) -> int:
        return self._id

    @property
    def current_floor(self) -> int:
        return self._current_floor

    @property
    def current_direction(self) -> Direction:
        with self._lock:
            return self._current_direction

