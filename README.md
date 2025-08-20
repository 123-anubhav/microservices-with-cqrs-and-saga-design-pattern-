# microservices-with-cqrs-and-saga-design-pattern-
cqrs and saga design pattern implemented microservice for data consistency. in this saga choreography used and each microservice has its won db per service , how to data async between 2 microservervice db, used cqrs for command and query responsibility segration. in every microservices data consistency is common scenario to manage this used cqrs

---
we have **two microservices** (`01-vessel-app` and `02-dashboard`), both registered in **Eureka**, and you’re using **Kafka as the Pub/Sub tool** with **topics** for communication (`vessel-app` → publish events, `dashboard` → consume events using `@KafkaListener`).
---
---

# 🚀 CQRS + Saga (Choreography) with Kafka Pub/Sub Microservices

This project demonstrates how to build **event-driven microservices** using:

* **CQRS (Command Query Responsibility Segregation)**
* **Saga Pattern (Choreography-based)**
* **Kafka Pub/Sub** for asynchronous communication
* **Eureka Server** for service discovery

We showcase this through **two microservices**:

* **01 – Vessel App Service** → Publishes vessel-related events
* **02 – Dashboard Service** → Consumes and displays vessel data

---

## 🔹 System Overview

### 1. **Microservice Isolation**

* Each service owns its **own database** (Database-per-Service).
* Services **do not call each other’s DBs directly**.
* Data sync happens **asynchronously** via **Kafka topics**.

---

### 2. **CQRS Pattern**

* **Command Side (Vessel App)** → Handles write operations (adding vessel data).
* **Query Side (Dashboard)** → Handles read operations (querying vessel data).
* This ensures **optimized writes & fast queries** without coupling.

---

### 3. **Saga Pattern (Choreography)**

* Saga is implemented using **Kafka events**.
* No central orchestrator — services **react to events** and execute local transactions.
* Compensation is handled through **failure events**.

---

### 4. **Kafka Pub/Sub**

Kafka acts as the **message broker** to connect microservices asynchronously.

* **Vessel App** → Publishes messages to Kafka Topic (e.g., `vessel-topic`).
* **Dashboard Service** → Listens to Kafka Topic with `@KafkaListener`.
* Ensures **eventual consistency** between service databases.

---

## 🔹 Data Flow Example

1. **Vessel App** receives a **command** → e.g., Add Vessel Details.
2. Vessel details are **stored in its DB** and an event is **published to Kafka (`vessel-topic`)**.
3. **Dashboard Service** consumes the event using `@KafkaListener`.
4. It **updates its own DB** with the new vessel data for querying.

👉 This way, **both services remain consistent** while being **fully decoupled**.

---

## 🛠️ Tech Stack

* **Spring Boot** – Microservices framework
* **Spring Cloud Netflix Eureka** – Service discovery
* **Spring Kafka** – Kafka client for Pub/Sub communication
* **MySQL / PostgreSQL** – Databases (per service)
* **CQRS Pattern** – Command/Query segregation
* **Saga (Choreography)** – Event-driven consistency

---

## 📂 Project Structure

```
📦 vessel-microservices
 ┣ 📂 eureka-server
 ┣ 📂 01-vessel-app
 ┃ ┣ 📂 command   # write operations (add vessel data)
 ┃ ┣ 📂 query     # read operations (local reads)
 ┃ ┗ 📂 kafka     # publishes events to vessel-topic
 ┣ 📂 02-dashboard
 ┃ ┣ 📂 query     # read operations for dashboard
 ┃ ┗ 📂 kafka     # consumes events via @KafkaListener
 ┣ 📂 common-events
 ┗ 📜 README.md
```

---

## ⚡ Kafka Setup

1. **Create Kafka Topic** for vessel data:

   ```bash
   kafka-topics.sh --create --topic vessel-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
   ```

2. **Producer Example (Vessel App)**

   ```java
   @Autowired
   private KafkaTemplate<String, VesselEvent> kafkaTemplate;

   public void publishVessel(VesselEvent event) {
       kafkaTemplate.send("vessel-topic", event);
   }
   ```

3. **Consumer Example (Dashboard Service)**

   ```java
   @KafkaListener(topics = "vessel-topic", groupId = "dashboard-group")
   public void consume(VesselEvent event) {
       log.info("Consumed event: {}", event);
       // Save into Dashboard DB
   }
   ```

---

## 🌟 Features

* ✅ **Event-driven microservices** with Kafka
* ✅ **CQRS separation** → write in Vessel App, read in Dashboard
* ✅ **Saga choreography** ensures eventual consistency
* ✅ **Eureka server registration** for dynamic discovery
* ✅ **Asynchronous DB synchronization** via Kafka

---

## 🎯 Benefits

* **Loose coupling** – services don’t depend on each other directly
* **Scalable** – Kafka handles large volumes of events
* **Resilient** – failure events trigger compensations
* **Cloud-ready** – Eureka + Kafka enable distributed deployments

---

✨ This architecture ensures **data consistency** across distributed microservices using **CQRS + Saga Choreography + Kafka** while keeping services autonomous and event-driven.

---
