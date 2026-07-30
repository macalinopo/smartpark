# 🚗 SmartPark

SmartPark is a Spring Boot–based parking management system.  
It provides REST APIs for registering parking lots, managing vehicles, and handling check-in/check-out operations.  
The project uses an H2 in-memory database for quick setup and testing, making it easy to run locally without external dependencies.

---

## 🖥 Requirements

Before running SmartPark, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version **17** or higher  
- **Maven**: Version **3.8+** (if using Maven)  
- **Gradle**: Version **7+** (if using Gradle)  
- **Git**: For cloning the repository  
- **Postman** (optional): For testing APIs with the provided collection

---

## 📦 Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/smartpark.git
   cd smartpark

2. Build the project

With Maven:

bash
mvn clean install
Or with Gradle:

bash
./gradlew build


3. Run the application

With Maven:

bash
mvn spring-boot:run


4. Access the H2 Console
URL: http://localhost:9999/h2-console
JDBC URL: jdbc:h2:mem:smartparkdb
User: sa
Password: (leave empty)

API Testing
You can test endpoints using the included Postman collection ( added in this repository).
Import the collection into Postman and run requests against:
