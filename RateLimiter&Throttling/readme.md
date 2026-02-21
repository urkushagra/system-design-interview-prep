# 🚦 Rate Limiter vs Throttling – Learning Notes

## 📌 Objective

This repository contains my structured learning notes on:

- Rate Limiting
- Throttling
- Their differences
- Real-world use cases
- System design perspective

This is part of my backend/system design preparation journey.

---

# 🟢 What is Rate Limiting?

Rate limiting controls **how many requests** a user or client can make within a specific time window.

### ✅ Example:
- 100 requests per minute
- If exceeded → Request rejected
- HTTP Status Code: `429 Too Many Requests`

### 🎯 Why It’s Used:
- Prevent API abuse
- Protect backend servers
- Ensure fair usage
- Prevent DDoS attacks

### ⚙ Common Algorithms:
- Fixed Window Counter
- Sliding Window
- Token Bucket
- Leaky Bucket

---

# 🔵 What is Throttling?

Throttling controls **how fast requests are processed**, especially when the system is under heavy load.

Instead of rejecting requests immediately:
- Requests may be delayed
- Processing speed is reduced
- Requests may be queued

### 🎯 Why It’s Used:
- Maintain system stability
- Prevent crashes during traffic spikes
- Protect infrastructure

---

# ⚡ Key Differences

| Feature | Rate Limiting | Throttling |
|----------|--------------|------------|
| Purpose | Limit number of requests | Slow down request speed |
| Behavior | Rejects excess requests | Delays or slows processing |
| Applied When | Always | During high load |
| Response | 429 Error | Delayed response |

---

# 🧠 Real-World Understanding

In production systems:

- Rate limiting ensures fairness.
- Throttling ensures stability.

Most scalable systems use **both together**.

---

# 🏗 System Design Perspective

In distributed systems:

- Rate limiting can be implemented using Redis
- Token Bucket is commonly used
- Requires synchronization across multiple servers

Design considerations:
- Horizontal scaling
- Consistency
- High availability
- Low latency

---

# 🚀 Learning Outcomes

After completing this topic, I can:

- Explain the difference clearly in interviews
- Design a distributed rate limiter
- Choose appropriate algorithm based on use case
- Understand API gateway implementation

---

# 📅 Learning Log

| Date | Topic | Notes |
|------|--------|--------|
| YYYY-MM-DD | Rate Limiter | Completed theory |
| YYYY-MM-DD | Throttling | Completed comparison |
| YYYY-MM-DD | Implementation | Practiced using Spring Boot |

---

# 📚 Next Steps

- Implement Rate Limiter in Spring Boot
- Explore API Gateway-based rate limiting
- Study distributed rate limiting at scale
- Practice system design questions

---

# 🛠 Tech Stack (Future Implementation)

- Java
- Spring Boot
- Redis
- Docker

---

# 🙌 Author

**Kushagra Upadhayay**  
Backend & System Design Enthusiast  
B.Tech CSE (Cybersecurity & Digital Forensics)

---

⭐ If this repository helped you, feel free to star it!
