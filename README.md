# Academic Tracker - Spring Boot Application

A comprehensive academic management system built with Spring Boot 3.5.6, featuring JWT authentication, role-based access control, and RESTful APIs for managing users, courses, attendance, and marks.

## 🚀 Features

- **JWT Authentication** with secure cookie-based sessions
- **Role-Based Access Control** (ADMIN, TEACHER, STUDENT, PARENT)
- **User Management** with password encryption
- **Course Management** with teacher assignments
- **Attendance Tracking** with date-based queries
- **Marks/Grades Management** with validation
- **Comprehensive Error Handling** and validation
- **MySQL Database** with JPA/Hibernate

## 📋 Prerequisites

- Java 21
- MySQL 8.0+
- Maven 3.6+

## 🛠️ Setup Instructions

### 1. Database Configuration

Create a MySQL database:
```sql
CREATE DATABASE academictracker;
```

Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/academictracker?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 2. Build and Run

```bash
# Clean and build
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

### Authentication Endpoints

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "STUDENT"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

Response includes JWT token in cookie and user details in body.

#### Logout
```http
POST /api/auth/logout
```

### User Management Endpoints

#### Get All Users (ADMIN only)
```http
GET /api/users/all
```

#### Get User by ID
```http
GET /api/users/{id}
```

#### Get User by Email
```http
GET /api/users/email/{email}
```

#### Delete User (ADMIN only)
```http
DELETE /api/users/{id}
```

### Course Management Endpoints

#### Get All Courses
```http
GET /api/courses/all
```

#### Get Course by ID
```http
GET /api/courses/{id}
```

#### Add Course (ADMIN or TEACHER)
```http
POST /api/courses/add
Content-Type: application/json

{
  "name": "Mathematics",
  "section": "A",
  "teacher": {
    "id": 1
  }
}
```

#### Delete Course (ADMIN only)
```http
DELETE /api/courses/{id}
```

### Attendance Management Endpoints

#### Mark Attendance (TEACHER only)
```http
POST /api/attendance/mark
Content-Type: application/json

{
  "studentId": 1,
  "courseId": 1,
  "date": "2025-10-08",
  "present": true,
  "remarks": "On time"
}
```

#### Get Attendance by Student
```http
GET /api/attendance/student/{studentId}
```
Accessible by: STUDENT, TEACHER, PARENT

#### Get Attendance by Course (TEACHER only)
```http
GET /api/attendance/course/{courseId}
```

#### Get Attendance by Date (TEACHER only)
```http
GET /api/attendance/date/{date}
```
Example: `/api/attendance/date/2025-10-08`

### Marks Management Endpoints

#### Add Marks (TEACHER only)
```http
POST /api/marks/add
Content-Type: application/json

{
  "student": {
    "id": 1
  },
  "course": {
    "id": 1
  },
  "score": 85.5
}
```

#### Get Marks by Student
```http
GET /api/marks/student/{studentId}
```
Accessible by: STUDENT, TEACHER, PARENT

#### Get Marks by Course (TEACHER only)
```http
GET /api/marks/course/{courseId}
```

## 🔐 Security

### Roles and Permissions

- **ADMIN**: Full system access, user management, course deletion
- **TEACHER**: Course creation, attendance marking, marks entry, view all course data
- **STUDENT**: View own attendance and marks
- **PARENT**: View child's attendance and marks

### JWT Configuration

JWT tokens are:
- Stored in HTTP-only cookies
- Valid for 24 hours (configurable in `application.properties`)
- Signed with HS512 algorithm
- Automatically validated on each request

## 🏗️ Project Structure

```
src/main/java/com/example/academictracker/
├── controller/          # REST API endpoints
│   ├── AuthController.java
│   ├── UserController.java
│   ├── CourseController.java
│   ├── AttendanceController.java
│   └── MarksController.java
├── service/            # Business logic
│   ├── UserService.java
│   ├── CourseService.java
│   ├── AttendanceService.java
│   └── MarksService.java
├── repository/         # Data access layer
│   ├── UserRepository.java
│   ├── CourseRepository.java
│   ├── AttendanceRepository.java
│   └── MarksRepository.java
├── model/             # Entity classes
│   ├── User.java
│   ├── Course.java
│   ├── Attendance.java
│   ├── Marks.java
│   └── Role.java
├── dto/               # Data transfer objects
│   ├── LoginRequest.java
│   └── AttendanceDTO.java
├── security/          # Security configuration
│   ├── SecurityConfig.java
│   ├── JwtUtil.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
└── AcademictrackerApplication.java
```

## 🔧 Configuration

Key configuration properties in `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/academictracker
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=yourSecretKey123456789yourSecretKey123456789
jwt.expiration=86400000

# CORS (update for production)
# Configured in SecurityConfig.java
```

## ✅ Key Improvements Made

1. **Centralized Password Encoding**: Moved to `UserService` for consistency
2. **Proper Validation**: Added `@Valid` annotations and comprehensive validation
3. **Error Handling**: Try-catch blocks with meaningful error messages
4. **Entity Relationships**: Fixed lazy loading and JSON serialization issues
5. **Security**: Proper JWT configuration from properties file
6. **Service Layer**: Added ID-based query methods for better performance
7. **API Consistency**: Standardized response formats and error handling
8. **Code Organization**: Removed duplicate classes and unused code

## 🧪 Testing

### Sample Test Flow

1. **Register a user**:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com","password":"password123","role":"STUDENT"}'
```

2. **Login**:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}' \
  -c cookies.txt
```

3. **Access protected endpoint**:
```bash
curl -X GET http://localhost:8080/api/courses/all \
  -b cookies.txt
```

## 📝 Notes

- Default role for new users is `STUDENT`
- Passwords are encrypted using BCrypt
- All timestamps use system timezone
- Database schema is auto-created/updated on startup
- CORS is configured for `http://localhost:3000` (update for production)

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify MySQL is running
   - Check credentials in `application.properties`
   - Ensure database exists

2. **JWT Token Invalid**
   - Check if token has expired (24h default)
   - Verify secret key is consistent
   - Clear cookies and login again

3. **Access Denied**
   - Verify user has correct role
   - Check `@PreAuthorize` annotations
   - Ensure JWT token is being sent

## 📄 License

This project is for educational purposes.

## 👥 Contributors

Academic Tracker Development Team
