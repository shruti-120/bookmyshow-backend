# 🎬 BookMyShow Backend Clone

A scalable, real-time backend system inspired by BookMyShow, built using **Java**, **Spring Boot**, **MySQL**, and **Docker**. This project focuses on core backend development concepts including **JWT authentication**, **RBAC**, **concurrent seat booking**, **payment integration**, and **email notifications**.

---

## 🚀 Features

- ✅ **User Registration & Authentication** (JWT-based)
- ✅ **Role-Based Access Control (RBAC)** for admin and customers
- ✅ **Movie, Theater, Screen, Show, and Seat Management**
- ✅ **Concurrent Seat Booking** with lock & auto-expiry logic
- ✅ **Payment Integration** using Razorpay Webhooks
- ✅ **Automated Email Notifications** (Gmail SMTP)
- ✅ **Dockerized Deployment**
- ✅ **Global Exception Handling & Logging**

---

## ⚙️ Tech Stack

- **Language**: Java 17  
- **Frameworks**: Spring Boot, Spring Security  
- **Database**: MySQL  
- **Authentication**: JWT (JSON Web Tokens)  
- **Payment**: Razorpay Webhook Integration  
- **Email**: Gmail SMTP  
- **Deployment**: Docker and render
- **Build Tool**: Gradle  

---

## 📁 Project Structure
bookmyshow-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/bookmyshow/
│       │       ├── controller/
│       │       ├── model/
│       │       ├── repository/
│       │       ├── service/
│       │       ├── config/
│       │       └── exception/
│       └── resources/
│           └── application.properties
├── Dockerfile
├── build.gradle
└── README.md

