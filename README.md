💄 You Are Beautiful
You Are Beautiful is a web application built with Spring Boot that showcases beauty, hairdressing, and wellness services. Users can browse offers by category, location, and availability, and explore service providers and their portfolios.

📦 Technologies Used
Java 17

Spring Boot

Spring Data JPA

Hibernate

Thymeleaf

MySQL

Maven

🧭 Features
🔍 Browse offers by category (e.g. makeup, hairdresser, massage)

📍 Filter by location (city, district)

📅 Select date and time for appointments

👩‍🎨 View service providers and their portfolios

💬 Read and submit customer reviews

🧾 Admin panel for managing offers

🚀 Getting Started
Clone the repository:

bash
git clone https://github.com/popala1986/JestesPiekna.git
Run the application: From your IDE (e.g. IntelliJ) — run JestesPieknaApplication

Open your browser and go to:

Kod
http://localhost:8080/
🗂️ Project Structure
Kod
src/
└── main/
    ├── java/pl/jestespiekna/app/
    │   ├── controller/
    │   ├── service/
    │   ├── repository/
    │   ├── entities/
    │   ├── dto/
    │   ├── mapper/
    │   └── model/
    └── resources/
        ├── templates/
        ├── static/
        ├── schema.sql
        └── data.sql
📁 Folder Overview
controller/ – Spring MVC controllers

service/ – Business logic

repository/ – JPA repositories

entities/ – Database entities

dto/ – Data Transfer Objects

mapper/ – Entity ↔ DTO mapping

model/ – Helper objects (e.g. filters)

templates/ – Thymeleaf views

static/ – Static resources (CSS, JS, images)

schema.sql – Database schema

data.sql – Sample data

🛠️ Sample Data
The data.sql file includes sample categories, service providers, and beauty offers — for example, “Glow & Go” makeup session or “Relax Hair Spa” treatment.

📌 Project Status
✅ Core functionality implemented 🔄 User panel and booking system in development

🔜 Upcoming Features
User registration and login

Appointment booking system

Email/SMS notifications

Google Maps integration

Service provider dashboard with availability management

👨‍💻 Author
Created by Paweł Popala
