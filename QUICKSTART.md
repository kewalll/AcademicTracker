# Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Step 1: Database Setup
```sql
-- Open MySQL and run:
CREATE DATABASE academictracker;
```

### Step 2: Update Configuration
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 3: Run Application
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

Application starts at: **http://localhost:8080**

---

## 🧪 Test the API

### 1. Register a Student
```bash
curl -X POST http://localhost:8080/api/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Alice Student\",\"email\":\"alice@school.com\",\"password\":\"password123\",\"role\":\"STUDENT\"}"
```

### 2. Register a Teacher
```bash
curl -X POST http://localhost:8080/api/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Bob Teacher\",\"email\":\"bob@school.com\",\"password\":\"password123\",\"role\":\"TEACHER\"}"
```

### 3. Login as Teacher
```bash
curl -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"bob@school.com\",\"password\":\"password123\"}" ^
  -c cookies.txt
```

**Response includes JWT token in cookie!**

### 4. Create a Course (as Teacher)
```bash
curl -X POST http://localhost:8080/api/courses/add ^
  -H "Content-Type: application/json" ^
  -b cookies.txt ^
  -d "{\"name\":\"Mathematics 101\",\"section\":\"A\",\"teacher\":{\"id\":2}}"
```

### 5. Mark Attendance (as Teacher)
```bash
curl -X POST http://localhost:8080/api/attendance/mark ^
  -H "Content-Type: application/json" ^
  -b cookies.txt ^
  -d "{\"studentId\":1,\"courseId\":1,\"date\":\"2025-10-08\",\"present\":true,\"remarks\":\"Present\"}"
```

### 6. Add Marks (as Teacher)
```bash
curl -X POST http://localhost:8080/api/marks/add ^
  -H "Content-Type: application/json" ^
  -b cookies.txt ^
  -d "{\"student\":{\"id\":1},\"course\":{\"id\":1},\"score\":85.5}"
```

### 7. View Student's Marks
```bash
curl -X GET http://localhost:8080/api/marks/student/1 ^
  -b cookies.txt
```

---

## 📱 Using Postman

1. **Import Collection**: Create requests for each endpoint
2. **Set Cookie**: After login, Postman automatically handles cookies
3. **Test Roles**: Try accessing TEACHER endpoints as STUDENT (should fail)

---

## 🔐 Default Roles

- **STUDENT**: View own attendance & marks
- **TEACHER**: Create courses, mark attendance, add marks
- **PARENT**: View child's attendance & marks
- **ADMIN**: Full system access

---

## ⚡ Common Commands

```bash
# Clean build
mvnw clean install

# Run tests
mvnw test

# Package as JAR
mvnw package

# Run JAR
java -jar target/academictracker-0.0.1-SNAPSHOT.jar
```

---

## 🐛 Troubleshooting

### Port 8080 already in use?
```properties
# Add to application.properties
server.port=8081
```

### Database connection failed?
- Check MySQL is running: `mysql -u root -p`
- Verify credentials in `application.properties`
- Ensure database exists: `SHOW DATABASES;`

### JWT token not working?
- Clear cookies and login again
- Check token hasn't expired (24h default)

---

## 📚 Full Documentation

See `README.md` for complete API documentation and `FIXES_APPLIED.md` for technical details.

---

**You're all set! Happy coding! 🎉**
