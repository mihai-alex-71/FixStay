# FixStay 🛠️🏠  
**Short-Term Rental Maintenance Management Platform**

## 📌 Overview
FixStay is a role-based web application designed to simplify maintenance management for short-term rental properties. It connects property hosts with verified service providers, enabling fast issue reporting, task monitoring, and transparent service execution.

---

## 🎯 Problem Statement
Short-term rental hosts often struggle to manage maintenance tasks efficiently, especially when working with multiple service providers. Communication is fragmented, progress tracking is unclear, and service quality is hard to measure.

FixStay solves this by providing a centralized, database-driven platform for reporting, monitoring, and completing maintenance tasks.

---

## 👥 User Roles
- **Host (Property Owner)**
- **Service Provider** (Cleaner, Handyman, Electrician, etc.)
- **Admin**

---

## ⚙️ Core Functionalities

### 🔐 Authentication & Roles
- User registration and login
- Role-based access control (RBAC)

### 🏠 Host Features
- Add and manage rental properties
- Create maintenance requests with description, category, priority, and photos
- Track task status in real time
- Receive notifications on task updates
- Review and rate service providers
- View maintenance history per property

### 🔧 Service Provider Features
- Create and manage service profile
- Monitor available maintenance tasks
- Filter tasks by location, service type, and urgency
- Accept or decline tasks
- Update task status (In Progress / Completed)
- Upload completion proof (photos, notes)
- Build reputation through ratings and reviews

### 🛡️ Admin Features
- Manage users and roles
- Verify service providers
- Moderate tasks and reviews
- View analytics and reports (response times, common issues, provider performance)

---

## 🔄 Task Lifecycle
1. Host creates a maintenance request  
2. Service provider monitors and accepts the task  
3. Provider updates progress and uploads proof  
4. Host confirms completion and leaves a review  

---

## 🧩 User Stories

### 🏠 Host Story – Reporting & Tracking an Issue
_As a rental host, when something breaks in my apartment, I want to quickly post the problem with photos and priority, so a service provider can take the job and I can track its progress without endless phone calls._

---

### 🔧 Service Provider Story – Finding Work
_As a service provider, I want to see nearby maintenance tasks that match my skills, accept a job I’m available for, update the status as I work, and upload proof when I’m done, so I can build trust and get more jobs._

---

### ⭐ Trust & Quality Story – Ratings
_As a host, I want to rate and review service providers after a job is completed, so I can choose reliable professionals in the future and help others avoid bad experiences._

---

## 🧠 Key Technical Concepts
- Multi-role system architecture
- RESTful API design
- Relational database modeling
- File upload and storage
- Notification system
- Secure authentication and authorization

---

## 🛠️ Possible Tech Stack
- **Frontend:** React / Vue / Angular  
- **Backend:** Node.js (Express) / Laravel / Spring Boot  
- **Database:** PostgreSQL / MySQL  
- **Auth:** JWT  
- **Hosting:** AWS / DigitalOcean  

---

## 📈 Project Value
- Improves operational efficiency for rental hosts
- Creates transparency and accountability for service providers
- Digitizes a real-world workflow used in the hospitality industry
- Strong portfolio project demonstrating full-stack and system design skills

---

## 📄 License
This project is for educational and portfolio purposes.