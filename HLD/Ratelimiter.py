
import asyncio
from collections import defaultdict
from collections import deque
import time
class RateLimiter:
    def __init__(self, limit, window_size: int):
        self.limit = limit
        self.window_size = window_size
        self.requests = defaultdict(deque)
    

    def allow(self, user_id):
        current_time = time.monotonic()
        user_requests = self.requests[user_id]

        while user_requests and current_time - user_requests[0] > self.window_size:
            user_requests.popleft()

        if(len(user_requests)<self.limit):
            user_requests.append(current_time)
            self.requests[user_id] = user_requests
            return True
        return False
        
async def task():
    limiter = RateLimiter(5, 7)

    for i in range(10):
        if(i>6):
            await asyncio.sleep(7)
        print(limiter.allow("user1"))

asyncio.run(task())