import time
class CircuitBreaker:
    def __init__(self, threshold, timeWindow):
        self.threshold = threshold
        self.timeWindow = timeWindow
        self.state = "closed"
        self.failureCount = 0
        self.lastFailureTime = None
    
    def call(self, operation):
        if self.state == "open":
            if time.time() - self.lastFailureTime < self.timeWindow:
                raise Exception("Circuit is Open")
            self.state = "half-open"

        
        try: 
            response = operation()
            self.state = "closed"
            self.failureCount = 0
            return response

        except Exception as e:
            self.failureCount+=1
            if self.state == "half-open":
                self.state = "open"
            if self.failureCount >= self.threshold:
                self.state = "open"
            
            self.lastFailureTime = time.time()
            raise e

def task():
    raise Exception("operation failed")

breaker = CircuitBreaker(2, 1)

for i in range(10):
    try:
        if i==6:
            time.sleep(3)
        breaker.call(task)
    except Exception as e:
        print(e)

