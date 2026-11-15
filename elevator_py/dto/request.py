from elevator.dto.direction import Direction


class Request:
    def __init__(self, requested_floor: int, direction: Direction):
        self._requested_floor = requested_floor
        self._direction = direction

    @property
    def requested_floor(self) -> int:
        return self._requested_floor

    @property
    def direction(self) -> Direction:
        return self._direction
