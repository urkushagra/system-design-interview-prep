# ⚖️ Load Balancer — System Design Guide

## 📘 Overview
A **Load Balancer (LB)** is a critical component in system design that helps distribute **incoming network traffic** across multiple **servers or instances**.  
Its primary goal is to improve:
- **Availability**
- **Scalability**
- **Reliability**
- **Performance**

By preventing any single server from being overwhelmed, load balancers ensure smooth operation and better user experience in large-scale distributed systems.

---

## 🎯 Key Objectives
- Evenly distribute traffic across multiple servers  
- Avoid overloading a single server  
- Detect and reroute failed instances (health checks)  
- Enable horizontal scaling (adding more servers)  
- Improve fault tolerance and high availability  

---

## 🧱 Architecture Overview
A **load balancer** typically sits between:
1. **Clients (Users, Browsers, Mobile apps)**  
2. **Server pool / Backend servers**

```
Client → Load Balancer → Server 1
                        → Server 2
                        → Server 3
```

When a request arrives:
- The LB chooses the best backend server (based on algorithm/policy)
- Sends the request there
- Returns the response to the client (directly or via server)

---

## 🧮 Types of Load Balancing

### 1. **Based on OSI Layer**
| Type | OSI Layer | Description | Example |
|------|------------|-------------|----------|
| **Layer 4 (Transport)** | TCP/UDP | Balances based on IP and Port, unaware of application data | HAProxy (TCP mode), AWS NLB |
| **Layer 7 (Application)** | HTTP/HTTPS | Balances based on HTTP headers, cookies, URLs, etc. | Nginx, AWS ALB, Envoy |

---

### 2. **Based on Deployment**
| Type | Description | Example |
|------|--------------|----------|
| **Hardware Load Balancer** | Physical appliance, used in data centers | F5, Citrix ADC |
| **Software Load Balancer** | Application-based, flexible and cloud-native | Nginx, HAProxy, Traefik |
| **Cloud Load Balancer** | Managed service by cloud providers | AWS ELB, GCP Load Balancer, Azure Front Door |

---

## ⚙️ Load Balancing Algorithms

### **1. Round Robin**
- Each request is sent to the next server in sequence.  
✅ Simple to implement  
❌ Doesn’t consider server load

### **2. Weighted Round Robin**
- Assigns weights to servers based on capacity (CPU, memory).  
✅ Handles servers with different capabilities

### **3. Least Connections**
- Directs traffic to the server with the fewest active connections.  
✅ Ideal for long-lived connections

### **4. IP Hash**
- Uses client IP to determine which server handles the request.  
✅ Ensures session stickiness  
❌ May cause imbalance if clients are unevenly distributed

### **5. Consistent Hashing**
- Minimizes reassignments when servers are added/removed.  
✅ Used in distributed caching (e.g., CDNs, DNS routing)

### **6. Random**
- Randomly selects a backend server.  
✅ Simple and fast  
❌ Not efficient under heavy load

---

## 🔍 Health Checks
Load balancers continuously **monitor server health** to ensure traffic only goes to healthy instances.

- **Active health check:** Periodic pings or HTTP requests  
- **Passive health check:** Detects failures through connection timeouts or errors  

If a server fails:
- The LB **removes** it from rotation
- **Routes** new traffic to healthy servers

---

## 🧭 Session Persistence (Sticky Sessions)
Some applications (e.g., e-commerce, chat) need a user to stay connected to the same server.

- Implemented via:
  - Cookies
  - IP hash
  - Session IDs
- Can affect load distribution, so use carefully.

---

## 🧩 SSL/TLS Termination
Load balancers often handle **encryption/decryption** (SSL termination) to offload this expensive operation from backend servers.

### Flow:
```
Client (HTTPS) → LB (terminates SSL) → Server (HTTP)
```

✅ Improves backend performance  
❌ Requires secure LB configuration to prevent vulnerabilities  

---

## 🌍 Global vs Local Load Balancing
| Type | Description | Example |
|------|--------------|----------|
| **Local Load Balancer** | Balances traffic within a single region or data center | Nginx, HAProxy |
| **Global Load Balancer (GSLB)** | Distributes traffic across multiple geographic regions | AWS Route 53, Cloudflare Load Balancing |

---

## 📊 Load Balancer in a Real System

Example — **High-Level Architecture for a Web Application:**

```
        ┌──────────────┐
        │   Client     │
        └──────┬───────┘
               │
        ┌──────▼──────┐
        │ Load Balancer│ ← HTTPS termination, Routing
        └──────┬───────┘
          ┌─────┴─────┐
   ┌──────▼──────┐ ┌──▼──────┐
   │ App Server 1│ │App Server 2│
   └──────┬──────┘ └──┬──────┘
          │             │
        ┌─▼─────────────▼─┐
        │ Database / Cache│
        └─────────────────┘
```

---

## 🧰 Common Tools & Technologies

| Category | Examples |
|-----------|-----------|
| **Software LB** | Nginx, HAProxy, Traefik |
| **Cloud LB** | AWS ELB/ALB/NLB, GCP Load Balancer, Azure Front Door |
| **DNS-based LB** | Route 53, Cloudflare |
| **Proxy / Gateway** | Envoy, Istio (Service Mesh) |

---

## ⚡ Best Practices
✅ Use health checks and auto-scaling  
✅ Implement sticky sessions only when necessary  
✅ Monitor load metrics (CPU, latency, request count)  
✅ Use SSL termination for secure traffic  
✅ Keep load balancer highly available (use multiple instances)  
✅ Cache static assets with CDNs to reduce load  

---

## 🔐 Example: Load Balancing with Nginx

```nginx
http {
  upstream backend_servers {
    server app1.example.com weight=3;
    server app2.example.com;
    server app3.example.com;
  }

  server {
    listen 80;

    location / {
      proxy_pass http://backend_servers;
    }
  }
}
```

✅ **Round Robin** balancing with weighted servers  
✅ Simple and efficient for small-scale systems

---

## 🧭 Interview Tips
- Explain **how LB improves scalability & fault tolerance**  
- Discuss **algorithms and trade-offs**  
- Mention **Layer 4 vs Layer 7** difference  
- Use **real examples (AWS ALB, Nginx, Cloudflare)**  
- Include **health checks, sticky sessions, SSL offloading** concepts  

---

## 📚 References
- [AWS Elastic Load Balancing Documentation](https://aws.amazon.com/elasticloadbalancing/)
- [Nginx Load Balancing Guide](https://docs.nginx.com/nginx/admin-guide/load-balancer/)
- [HAProxy Configuration Basics](https://www.haproxy.com/documentation/hapee/latest/configuration/)

---

### 🏁 Summary
A **Load Balancer** ensures no single server becomes a bottleneck, enabling:
- Better **performance**
- Improved **availability**
- Simplified **scaling**
- Resilient **fault tolerance**

In modern system design, mastering load balancing concepts is essential for building **scalable and reliable** architectures.
