# Mega City Cab Management System

Mega City Cab, a popular cab service in Colombo City serving thousands of customers monthly, currently manages bookings manually. This system streamlines operations by digitizing customer bookings, vehicle management, and billing processes.

---

## 📋 Project Overview
This project is developed as part of the **Advanced Programming** module (CIS6003) at ICBT, affiliated with Cardiff Metropolitan University. It aims to address the following:
- Automating customer and booking management.
- Ensuring secure authentication and user-friendly interactions.
- Generating bills with applicable taxes and discounts.

---

## 🚀 Features

1. **Authentication**: Secure login system using username and password.
2. **Customer Management**:
   - Register new customers.
   - View and manage customer details.
3. **Booking Management**:
   - Add, update, view, and delete bookings.
   - Assign vehicles and drivers to bookings.
4. **Billing**:
   - Calculate bills with applicable taxes and generate printable invoices.
5. **Vehicle and Driver Management**:
   - Maintain detailed records for vehicles and drivers.
6. **Help Menu**:
   - Provide usage guidelines for new users.
7. **Exit System**:
   - Securely log out and close the application.

---

## 🛠️ Technologies Used

- **Programming Language**: Java (Spring Boot)
- **Frontend**: React
- **Database**: PostgreSQL
- **Version Control**: Git and GitHub
- **Testing**: Mockito
- **Build Tool**: Maven
- **Design Patterns**: MVC, DAO, Singleton
- **API**: RESTful web services

---

## 📂 Folder Structure

```
mega-city-cab-service-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── megacity/
│   │   │           ├── config/
│   │   │           ├── controller/
│   │   │           ├── dto/
│   │   │           ├── entity/
│   │   │           ├── exception/
│   │   │           ├── repository/
│   │   │           ├── service/
│   │   │           ├── util/
│   │   │           └── Application.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       ├── application.properties
│   │       └── messages.properties
│   ├── test/
│   │   └── java/
│   │       └── com/
│   │           └── megacitycab/
│   │               ├── controller/
│   │               ├── service/
│   │               └── repository/
├── .gitignore
├── pom.xml
└── README.md
```

---

## 🔧 Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone sample ------------------------------
   ```
2. **Navigate to the Project Directory**:
   ```bash
   cd mega-city-cab-service-backend
   ```
3. **Configure the Database**:
   - Update the `application.properties` file with your database credentials:
     ```properties
     spring.application.name=mega-city-cab-service-backend
     server.port=${PORT_NO}

      # HikariCP Settings
      spring.datasource.hikari.minimum-idle=5
      spring.datasource.hikari.maximum-pool-size=20
      spring.datasource.hikari.idle-timeout=30000
      spring.datasource.hikari.pool-name=HikariPool-1
      spring.datasource.hikari.max-lifetime=2000000
      spring.datasource.hikari.connection-timeout=30000
      spring.datasource.hikari.leak-detection-threshold=15000

      # Hibernate/JPA properties
      spring.jpa.properties.hibernate.show_sql=true
      
      # Read database configuration
      spring.datasource.read.url=${DATA_SOURCE_URL}
      spring.datasource.read.username=${DB_USER}
      spring.datasource.read.password=${DB_PASSWORD}
      spring.datasource.read.driver-class-name=org.postgresql.Driver
      
      # Write database configuration
      spring.datasource.write.url=${DATA_SOURCE_URL}
      spring.datasource.write.username=${DB_USER}
      spring.datasource.write.password=${DB_PASSWORD}
      spring.datasource.write.driver-class-name=org.postgresql.Driver
      
      # Hibernate properties
      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=true
      spring.jpa.properties.hibernate.format_sql=true
      
      # Logging properties
      logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
      logging.level.org.hibernate.type.descriptor.sql.BasicExtractor=TRACE
      logging.level.org.hibernate.SQL=TRACE
      
      # Database type and dialect
      spring.jpa.database=POSTGRESQL
      spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
      
      # JWT properties
      application.security.jwt.secret-key=${JWT_SECRET}
      application.security.jwt.expiration=${JWT_EXPIRY}
      application.security.jwt.refresh-token.expiration=${JWT_REFRESH_EXPIRY}
     ```
4. **Build the Project**:
   ```bash
   mvn clean install
   ```
5. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
6. **Access the Application**:
   - Open your browser and go to: `http://localhost:8080`

---

## 🧪 Testing

- Run all tests using Maven:
  ```bash
  mvn test
  ```
- Use tools like Postman to test API endpoints.

---

## 📈 API Endpoints

### Authentication:
- `POST /api/v1/auth/login`: User login (sample)

## Role-Based Permissions

| Feature                   | Admin      | Manager    | Driver     | Customer   | Support Staff |
|---------------------------|------------|------------|------------|------------|---------------|
| Manage users              | ✅          | ❌          | ❌          | ❌          | ❌             |
| Create/manage bookings    | ✅          | ✅          | Limited    | ✅ (Own)    | ✅             |
| Assign drivers/vehicles   | ✅          | ✅          | ❌          | ❌          | ❌             |
| Update booking status     | ✅          | ✅          | ✅ (Own)    | ❌          | ❌             |
| View system reports       | ✅          | ✅          | ❌          | ❌          | ❌             |
| View personal records     | ✅          | ✅          | ✅          | ✅          | ❌             |
| Access invoices           | ✅          | ✅          | ❌          | ✅          | ❌             |


### Customers:
- `POST /api/v1/customers`: Add a new customer (sample)
- `GET /api/v1/customers/{id}`: View customer details (sample)

### Bookings:
- `POST /api/v1/bookings`: Create a booking (sample)
- `GET /api/v1/bookings/{id}`: Retrieve booking details (sample)

### Billing:
- `GET /api/v1/bills/{bookingId}`: Generate bill for a booking (sample)

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

## 📬 Contact

For any queries or issues, feel free to reach out:
- Email: maneeshagunawardhana60@gmail.com
