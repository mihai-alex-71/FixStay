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
### 1. Host – Adding a New Property
As a rental host,
I want to add a new short-term rental property with address, photos, and basic details,
so that I can start creating maintenance requests specifically for that property and keep all my listings organized in one place.
### 2. Service Provider – Filtering & Accepting Tasks
As a verified service provider (handyman/cleaner),
I want to filter available maintenance tasks by location, category, and urgency level,
so that I can quickly accept only the jobs I’m skilled for and available to complete within my schedule.
### 3. Admin – Verifying a New Service Provider
As an administrator,
I want to review and approve a new service provider’s profile (documents, skills, location),
so that only trustworthy professionals can accept tasks and hosts can rely on the quality of the platform.