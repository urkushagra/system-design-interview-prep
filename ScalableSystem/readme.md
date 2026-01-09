1️⃣ What “Scalability” Actually Means

You should be able to say:

“Scalability means the system can handle increased load (users, requests, data) without degrading performance.”

And explain the two types:

🔹 Vertical Scaling

Add more CPU/RAM to one machine

Simple but limited

🔹 Horizontal Scaling

Add more machines

Needs load balancing

More scalable in real-world systems

👉 Interviewer may ask:

Which one is better and why?

Correct answer:

Horizontal scaling, because vertical has hardware limits.

2️⃣ Stateless vs Stateful Services

You must understand this.

Stateless

No user/session data stored in the server

Any request can go to any server

Easy to scale

Stateful

Server remembers user data

Harder to scale

👉 Be ready to say:

“REST APIs are usually stateless, which makes them easier to scale horizontally.”

3️⃣ Why Kafka / Queues Are Used

If you used Kafka (you did 👀), expect questions.

You should explain:

Kafka decouples producers and consumers

Handles traffic spikes

Prevents system overload

Enables async processing

Simple explanation:

“Instead of processing everything synchronously, Kafka buffers requests so the system stays responsive under high load.”

4️⃣ Database Basics for Scale

You should know:

Problems at scale:

Too many reads/writes

Lock contention

Slow queries

Common solutions:

Indexing

Read replicas

Pagination

Transactions

👉 Expect:

How do you ensure consistency when multiple requests update data?

Answer:

Use database transactions.

5️⃣ API Design Awareness

You should understand:

REST principles

Idempotency

HTTP methods

Status codes

Example interviewer question:

Why use POST for transactions and GET for balance?

Correct answer:

POST changes state

GET is read-only
