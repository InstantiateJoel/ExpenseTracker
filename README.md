# Expense Tracker

Fullstack Expense Tracker Web App built with Spring Boot, PostgreSQL and vanilla JavaScript.

The application allows users to manage their personal finances by creating, editing and deleting income and expense transactions.

## Features
- Create, edit and delete transactions
- Separate incomes and expenses
- Categorize transactions
- Store data persistently in PostgreSQL
- REST API backend with Spring Boot
- Responsive frontend using vanilla JavaScript

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- REST API

### Database
- PostgreSQL

### Frontend
- HTML
- CSS
- vanilla JavaScript

### DevOps
- Docker
- Docker Compose

## Run the Expense Tracker

### Requirements
- Docker
- Docker Compose

Clone the repository and start the application:
```bash
git clone https://github.com/InstantiateJoel/ExpenseTracker.git
cd ExpenseTracker
docker compose up --build -d
```

The application will be available at:

### Frontend:
http://localhost:3000

### Backend API:
http://localhost:8080

## Project Structure
```text
ExpenseTracker/
|- backend/ # Spring Boot application
|- frontend/ # HTML, CSS, JavaScript
|- docker-compose.yml
| -README.md
```

## Future improvements:
- Improve authentication flow and session handling
- Add better transaction details and advanced views
- Add monthly expense statistics and visual charts
- Deploy the application on a Raspberry Pi using Docker and Nginx
- Access the application securely through a private VPN connection
