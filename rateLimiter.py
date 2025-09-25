import time
from collections import deque, defaultdict

class RateLimiter:
    def __init__(self, max_requests, time_window):
        self.max_requests = max_requests
        self.time_window = time_window
        self.user_requests = defaultdict(deque)

    def allow_request(self, user_id):
        now = time.time() * 1000  # milliseconds
        queue = self.user_requests[user_id]
        while queue and now - queue[0] > self.time_window:
            queue.popleft()
        if len(queue) < self.max_requests:
            queue.append(now)
            return True
        return False

if __name__ == "__main__":
    limiter = RateLimiter(3, 10000)
    user = "user1"
    for i in range(5):
        print(f"Request {i+1}: {limiter.allow_request(user)}")
