:

🍳 WebCooking

A modern full-stack restaurant management and online ordering system, featuring secure Google OAuth login, integrated payments, and complete admin control — all containerized with Docker and deployed on Render.

👥 Team Information

Team Size: 1 Developer

🚀 Features Overview
🔐 Authentication & Roles

Google OAuth 2.0 login with role-based access: Admin and Customer.

Secure JWT-based session management and password reset via email.

Email bill receipts automatically sent after successful order placement.

💳 Payment Integration

Integrated ZaloPay and BIDV payment gateways.

Auto table assignment for restaurant reservations after payment confirmation.

📊 Admin Dashboard

Full CRUD operations for dishes, menus, reservations, and orders.

Export detailed reports and data to Excel files.

🤖 Customer Support

Built-in AI chatbot powered by Google Dialogflow.

Instant help and support for customers directly within the platform.

🧩 Key Dependencies

Spring Web, Spring Data JPA, Spring Security, OAuth2 Client

JavaMail, Thymeleaf, Cloudinary SDK, Dialogflow SDK

Microsoft SQL Server JDBC Driver

🧱 Technologies

Backend: Java, Spring Boot
Frontend: Vue.js, Tailwind CSS
Database: Microsoft SQL Server
Other Tools: RESTful APIs, Git, Docker

🌐 Deployment

Backend: Deployed on Render (Linux Web Service) with a custom domain.

Frontend: Hosted as a Static Site via Render.

Fully containerized using Docker for consistent environment management.

🔗 Links

GitHub Repository: https://github.com/yuohta819/webcooking

Live Website: https://webcooking.onrender.com

🧾 License

This project is licensed under the MIT License.
