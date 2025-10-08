# Manual Testing Guide - Academic Tracker API
## Complete Step-by-Step Testing Sequence

**Purpose**: This guide provides a complete testing sequence for manual testers using Postman. Follow each step in order to test all endpoints and roles.

---

## 🎯 Prerequisites

1. **Database**: Create database `academictracker`
2. **Application**: Start with `mvn spring-boot:run`
3. **Base URL**: `http://localhost:8080`
4. **Tool**: Postman

---

## 📝 Testing Sequence Overview

| Phase | Steps | Purpose |
|-------|-------|---------|
| Phase 1 | 1-4 | Register all user roles |
| Phase 2 | 5-7 | Admin authentication & operations |
| Phase 3 | 8-11 | Teacher authentication & course creation |
| Phase 4 | 12-13 | Student authentication & view courses |
| Phase 5 | 14-20 | Teacher adds attendance & marks |
| Phase 6 | 21-23 | Student views own data |
| Phase 7 | 24-26 | Parent authentication & view student data |
| Phase 8 | 27-32 | Access control validation (should fail) |
| Phase 9 | 33-35 | Admin cleanup operations |

**Total Steps**: 35

---

## 🚀 PHASE 1: Register All Users (Steps 1-4)

### Step 1: Register Admin User
**Purpose**: Create an admin account for system management

```
Method: POST
URL: http://localhost:8080/api/auth/register
Headers:
  Content-Type: application/json

Body:
{
    "name": "Admin User",
    "email": "admin@test.com",
    "password": "admin123",
    "role": "ADMIN"
}

Expected Response: 200 OK
{
    "message": "User registered successfully",
    "email": "admin@test.com",
    "name": "Admin User",
    "role": "ADMIN"
}
```

✅ **Verification**: Check response contains success message

---

### Step 2: Register Teacher User
**Purpose**: Create a teacher account for course and attendance management

```
Method: POST
URL: http://localhost:8080/api/auth/register
Headers:
  Content-Type: application/json

Body:
{
    "name": "John Teacher",
    "email": "teacher@test.com",
    "password": "teacher123",
    "role": "TEACHER"
}

Expected Response: 200 OK
{
    "message": "User registered successfully",
    "email": "teacher@test.com",
    "name": "John Teacher",
    "role": "TEACHER"
}
```

✅ **Verification**: Check response contains success message

---

### Step 3: Register Student User
**Purpose**: Create a student account to track attendance and marks

```
Method: POST
URL: http://localhost:8080/api/auth/register
Headers:
  Content-Type: application/json

Body:
{
    "name": "Jane Student",
    "email": "student@test.com",
    "password": "student123",
    "role": "STUDENT"
}

Expected Response: 200 OK
{
    "message": "User registered successfully",
    "email": "student@test.com",
    "name": "Jane Student",
    "role": "STUDENT"
}
```

✅ **Verification**: Check response contains success message

---

### Step 4: Register Parent User
**Purpose**: Create a parent account to monitor student progress

```
Method: POST
URL: http://localhost:8080/api/auth/register
Headers:
  Content-Type: application/json

Body:
{
    "name": "Parent User",
    "email": "parent@test.com",
    "password": "parent123",
    "role": "PARENT"
}

Expected Response: 200 OK
{
    "message": "User registered successfully",
    "email": "parent@test.com",
    "name": "Parent User",
    "role": "PARENT"
}
```

✅ **Verification**: Check response contains success message

---

## 🔐 PHASE 2: Admin Login & Operations (Steps 5-7)

### Step 5: Login as Admin
**Purpose**: Authenticate admin and get access token

```
Method: POST
URL: http://localhost:8080/api/auth/login
Headers:
  Content-Type: application/json

Body:
{
    "email": "admin@test.com",
    "password": "admin123"
}

Expected Response: 200 OK
{
    "email": "admin@test.com",
    "name": "Admin User",
    "role": "ADMIN",
    "id": 1,
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0LmNvbSIsImlhdCI6MTY5..."
}
```

✅ **Action Required**: 
- **SAVE the token** - You'll use this as `adminToken` in subsequent requests
- **SAVE the id** - This is the admin user ID

📝 **Note**: Copy the token value and save it. Format for next requests: `Authorization: Bearer <token>`

---

### Step 6: Get All Users (Admin Only)
**Purpose**: Verify admin can view all registered users

```
Method: GET
URL: http://localhost:8080/api/users/all
Headers:
  Authorization: Bearer <paste-adminToken-here>

Expected Response: 200 OK
[
    {
        "id": 1,
        "name": "Admin User",
        "email": "admin@test.com",
        "role": "ADMIN"
    },
    {
        "id": 2,
        "name": "John Teacher",
        "email": "teacher@test.com",
        "role": "TEACHER"
    },
    {
        "id": 3,
        "name": "Jane Student",
        "email": "student@test.com",
        "role": "STUDENT"
    },
    {
        "id": 4,
        "name": "Parent User",
        "email": "parent@test.com",
        "role": "PARENT"
    }
]
```

✅ **Verification**: 
- Response contains all 4 users
- Each user has id, name, email, and role
- No password field in response (security check)

---

### Step 7: Get User by Email (Admin)
**Purpose**: Verify admin can fetch specific user details

```
Method: GET
URL: http://localhost:8080/api/users/email/student@test.com
Headers:
  Authorization: Bearer <adminToken>

Expected Response: 200 OK
{
    "id": 3,
    "name": "Jane Student",
    "email": "student@test.com",
    "role": "STUDENT"
}
```

✅ **Verification**: Response contains correct student details

---

## 👨‍🏫 PHASE 3: Teacher Login & Course Creation (Steps 8-11)

### Step 8: Login as Teacher
**Purpose**: Authenticate teacher and get access token

```
Method: POST
URL: http://localhost:8080/api/auth/login
Headers:
  Content-Type: application/json

Body:
{
    "email": "teacher@test.com",
    "password": "teacher123"
}

Expected Response: 200 OK
{
    "email": "teacher@test.com",
    "name": "John Teacher",
    "role": "TEACHER",
    "id": 2,
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFjaGVyQHRlc3QuY29tIiwiaWF0Ijo..."
}
```

✅ **Action Required**: 
- **SAVE the token** as `teacherToken`
- **SAVE the id** as `teacherId` (you'll need this to create courses)

---

### Step 9: Add Course - Mathematics (Teacher)
**Purpose**: Create first course for testing

```
Method: POST
URL: http://localhost:8080/api/courses/add
Headers:
  Authorization: Bearer <teacherToken>
  Content-Type: application/json

Body:
{
    "name": "Mathematics",
    "section": "A",
    "teacher": {
        "id": 2
    }
}

Expected Response: 200 OK
{
    "id": 1,
    "name": "Mathematics",
    "section": "A",
    "teacher": {
        "id": 2,
        "name": "John Teacher",
        "email": "teacher@test.com",
        "role": "TEACHER"
    }
}
```

✅ **Action Required**: 
- **SAVE the course id** as `courseId` (usually 1)
- This will be used for attendance and marks

✅ **Verification**: Response contains course with teacher details

---

### Step 10: Add Course - Physics (Teacher)
**Purpose**: Create second course to test multiple courses

```
Method: POST
URL: http://localhost:8080/api/courses/add
Headers:
  Authorization: Bearer <teacherToken>
  Content-Type: application/json

Body:
{
    "name": "Physics",
    "section": "B",
    "teacher": {
        "id": 2
    }
}

Expected Response: 200 OK
{
    "id": 2,
    "name": "Physics",
    "section": "B",
    "teacher": {
        "id": 2,
        "name": "John Teacher",
        "email": "teacher@test.com",
        "role": "TEACHER"
    }
}
```

✅ **Verification**: Response contains course id 2

---

### Step 11: Get All Courses (Teacher)
**Purpose**: Verify teacher can view all courses

```
Method: GET
URL: http://localhost:8080/api/courses/all
Headers:
  Authorization: Bearer <teacherToken>

Expected Response: 200 OK
[
    {
        "id": 1,
        "name": "Mathematics",
        "section": "A",
        "teacher": {
            "id": 2,
            "name": "John Teacher",
            "email": "teacher@test.com",
            "role": "TEACHER"
        }
    },
    {
        "id": 2,
        "name": "Physics",
        "section": "B",
        "teacher": {
            "id": 2,
            "name": "John Teacher",
            "email": "teacher@test.com",
            "role": "TEACHER"
        }
    }
]
```

✅ **Verification**: Response contains both courses created

---

## 👨‍🎓 PHASE 4: Student Login & View Courses (Steps 12-13)

### Step 12: Login as Student
**Purpose**: Authenticate student and get access token

```
Method: POST
URL: http://localhost:8080/api/auth/login
Headers:
  Content-Type: application/json

Body:
{
    "email": "student@test.com",
    "password": "student123"
}

Expected Response: 200 OK
{
    "email": "student@test.com",
    "name": "Jane Student",
    "role": "STUDENT",
    "id": 3,
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50QHRlc3QuY29tIiwiaWF0Ijo..."
}
```

✅ **Action Required**: 
- **SAVE the token** as `studentToken`
- **SAVE the id** as `studentId` (usually 3)

---

### Step 13: Get All Courses (Student)
**Purpose**: Verify student can view available courses

```
Method: GET
URL: http://localhost:8080/api/courses/all
Headers:
  Authorization: Bearer <studentToken>

Expected Response: 200 OK
[
    {
        "id": 1,
        "name": "Mathematics",
        "section": "A",
        "teacher": { ... }
    },
    {
        "id": 2,
        "name": "Physics",
        "section": "B",
        "teacher": { ... }
    }
]
```

✅ **Verification**: Student can see all available courses

---

## 📝 PHASE 5: Teacher Adds Attendance & Marks (Steps 14-20)

### Step 14: Mark Attendance - Present (Teacher)
**Purpose**: Record student present for today

```
Method: POST
URL: http://localhost:8080/api/attendance/mark
Headers:
  Authorization: Bearer <teacherToken>
  Content-Type: application/json

Body:
{
    "studentId": 3,
    "courseId": 1,
    "date": "2025-10-08",
    "present": true,
    "remarks": "On time"
}

Expected Response: 200 OK
{
    "id": 1,
    "student": {
        "id": 3,
        "name": "Jane Student",
        "email": "student@test.com",
        "role": "STUDENT"
    },
    "course": {
        "id": 1,
        "name": "Mathematics",
        "section": "A"
    },
    "date": "2025-10-08",
    "present": true,
    "remarks": "On time"
}
```

✅ **Verification**: Attendance record created with present = true

---

### Step 15: Mark Attendance - Absent (Teacher)
**Purpose**: Record student absent for previous day

```
Method: POST
URL: http://localhost:8080/api/attendance/mark
Headers:
  Authorization: Bearer <teacherToken>
  Content-Type: application/json

Body:
{
    "studentId": 3,
    "courseId": 1,
    "date": "2025-10-07",
    "present": false,
    "remarks": "Sick leave"
}

Expected Response: 200 OK
{
    "id": 2,
    "student": { ... },
    "course": { ... },
    "date": "2025-10-07",
    "present": false,
    "remarks": "Sick leave"
}
```

✅ **Verification**: Attendance record created with present = false

---

### Step 16: Mark Attendance - Another Day (Teacher)
**Purpose**: Add more attendance data for testing

```
Method: POST
URL: http://localhost:8080/api/attendance/mark
Headers:
  Authorization: Bearer <teacherToken>
  Content-Type: application/json

Body:
{
    "studentId": 3,
    "courseId": 1,
    "date": "2025-10-06",
    "present": true,
    "remarks": "Participated well"
}

Expected Response: 200 OK
{
    "id": 3,
    "student": { ... },
    "course": { ... },
    "date": "2025-10-06",
    "present": true,
    "remarks": "Participated well"
}
```

✅ **Verification**: Third attendance record created

---

### Step 17: Add Marks (Teacher)
**Purpose**: Record student marks for Mathematics

```
Method: POST
URL: http://localhost:8080/api/marks/add
Headers:
  Authorization: Bearer <teacherToken>
  Content-Type: application/json

Body:
{
    "student": {
        "id": 3
    },
    "course": {
        "id": 1
    },
    "score": 85.5
}

Expected Response: 200 OK
{
    "id": 1,
    "student": {
        "id": 3,
        "name": "Jane Student",
        "email": "student@test.com",
        "role": "STUDENT"
    },
    "course": {
        "id": 1,
        "name": "Mathematics",
        "section": "A"
    },
    "score": 85.5
}
```

✅ **Verification**: Marks record created with score 85.5

---

### Step 18: Get Attendance by Course (Teacher)
**Purpose**: Verify teacher can view all attendance for their course

```
Method: GET
URL: http://localhost:8080/api/attendance/course/1
Headers:
  Authorization: Bearer <teacherToken>

Expected Response: 200 OK
[
    {
        "id": 1,
        "student": {
            "id": 3,
            "name": "Jane Student",
            "email": "student@test.com",
            "role": "STUDENT"
        },
        "course": {
            "id": 1,
            "name": "Mathematics",
            "section": "A"
        },
        "date": "2025-10-08",
        "present": true,
        "remarks": "On time"
    },
    {
        "id": 2,
        "student": { ... },
        "course": { ... },
        "date": "2025-10-07",
        "present": false,
        "remarks": "Sick leave"
    },
    {
        "id": 3,
        "student": { ... },
        "course": { ... },
        "date": "2025-10-06",
        "present": true,
        "remarks": "Participated well"
    }
]
```

✅ **Verification**: All 3 attendance records returned for Mathematics course

---

### Step 19: Get Marks by Course (Teacher)
**Purpose**: Verify teacher can view all marks for their course

```
Method: GET
URL: http://localhost:8080/api/marks/course/1
Headers:
  Authorization: Bearer <teacherToken>

Expected Response: 200 OK
[
    {
        "id": 1,
        "student": {
            "id": 3,
            "name": "Jane Student",
            "email": "student@test.com",
            "role": "STUDENT"
        },
        "course": {
            "id": 1,
            "name": "Mathematics",
            "section": "A"
        },
        "score": 85.5
    }
]
```

✅ **Verification**: Marks record returned for Mathematics course

---

### Step 20: Get Attendance by Date (Teacher)
**Purpose**: Verify teacher can view attendance for specific date

```
Method: GET
URL: http://localhost:8080/api/attendance/date/2025-10-08
Headers:
  Authorization: Bearer <teacherToken>

Expected Response: 200 OK
[
    {
        "id": 1,
        "student": {
            "id": 3,
            "name": "Jane Student",
            "email": "student@test.com",
            "role": "STUDENT"
        },
        "course": {
            "id": 1,
            "name": "Mathematics",
            "section": "A"
        },
        "date": "2025-10-08",
        "present": true,
        "remarks": "On time"
    }
]
```

✅ **Verification**: Only attendance for 2025-10-08 is returned

---

## 📊 PHASE 6: Student Views Own Data (Steps 21-23)

### Step 21: Get Own Attendance (Student)
**Purpose**: Verify student can view their attendance records

```
Method: GET
URL: http://localhost:8080/api/attendance/student/3
Headers:
  Authorization: Bearer <studentToken>

Expected Response: 200 OK
[
    {
        "id": 1,
        "student": {
            "id": 3,
            "name": "Jane Student",
            "email": "student@test.com",
            "role": "STUDENT"
        },
        "course": {
            "id": 1,
            "name": "Mathematics",
            "section": "A"
        },
        "date": "2025-10-08",
        "present": true,
        "remarks": "On time"
    },
    {
        "id": 2,
        "student": { ... },
        "course": { ... },
        "date": "2025-10-07",
        "present": false,
        "remarks": "Sick leave"
    },
    {
        "id": 3,
        "student": { ... },
        "course": { ... },
        "date": "2025-10-06",
        "present": true,
        "remarks": "Participated well"
    }
]
```

✅ **Verification**: Student sees all their attendance records (3 records)

---

### Step 22: Get Own Marks (Student)
**Purpose**: Verify student can view their marks

```
Method: GET
URL: http://localhost:8080/api/marks/student/3
Headers:
  Authorization: Bearer <studentToken>

Expected Response: 200 OK
[
    {
        "id": 1,
        "student": {
            "id": 3,
            "name": "Jane Student",
            "email": "student@test.com",
            "role": "STUDENT"
        },
        "course": {
            "id": 1,
            "name": "Mathematics",
            "section": "A"
        },
        "score": 85.5
    }
]
```

✅ **Verification**: Student sees their marks (85.5 in Mathematics)

---

### Step 23: Get Own Profile (Student)
**Purpose**: Verify student can view their profile

```
Method: GET
URL: http://localhost:8080/api/users/3
Headers:
  Authorization: Bearer <studentToken>

Expected Response: 200 OK
{
    "id": 3,
    "name": "Jane Student",
    "email": "student@test.com",
    "role": "STUDENT"
}
```

✅ **Verification**: Student profile returned without password

---

## 👪 PHASE 7: Parent Login & View Student Data (Steps 24-26)

### Step 24: Login as Parent
**Purpose**: Authenticate parent and get access token

```
Method: POST
URL: http://localhost:8080/api/auth/login
Headers:
  Content-Type: application/json

Body:
{
    "email": "parent@test.com",
    "password": "parent123"
}

Expected Response: 200 OK
{
    "email": "parent@test.com",
    "name": "Parent User",
    "role": "PARENT",
    "id": 4,
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwYXJlbnRAdGVzdC5jb20iLCJpYXQiOj..."
}
```

✅ **Action Required**: **SAVE the token** as `parentToken`

---

### Step 25: Get Student Attendance (Parent)
**Purpose**: Verify parent can monitor student attendance

```
Method: GET
URL: http://localhost:8080/api/attendance/student/3
Headers:
  Authorization: Bearer <parentToken>

Expected Response: 200 OK
[
    {
        "id": 1,
        "student": {
            "id": 3,
            "name": "Jane Student",
            "email": "student@test.com",
            "role": "STUDENT"
        },
        "course": {
            "id": 1,
            "name": "Mathematics",
            "section": "A"
        },
        "date": "2025-10-08",
        "present": true,
        "remarks": "On time"
    },
    ... (all 3 attendance records)
]
```

✅ **Verification**: Parent can view student's attendance

---

### Step 26: Get Student Marks (Parent)
**Purpose**: Verify parent can monitor student marks

```
Method: GET
URL: http://localhost:8080/api/marks/student/3
Headers:
  Authorization: Bearer <parentToken>

Expected Response: 200 OK
[
    {
        "id": 1,
        "student": {
            "id": 3,
            "name": "Jane Student",
            "email": "student@test.com",
            "role": "STUDENT"
        },
        "course": {
            "id": 1,
            "name": "Mathematics",
            "section": "A"
        },
        "score": 85.5
    }
]
```

✅ **Verification**: Parent can view student's marks

---

## 🚫 PHASE 8: Access Control Tests (Steps 27-32)
**Purpose**: Verify role-based access control is working

### Step 27: Student Tries to Get All Users (Should Fail)
**Purpose**: Verify students cannot access admin-only endpoints

```
Method: GET
URL: http://localhost:8080/api/users/all
Headers:
  Authorization: Bearer <studentToken>

Expected Response: 403 Forbidden
{
    "timestamp": "2025-10-08T08:30:00.000+00:00",
    "status": 403,
    "error": "Forbidden",
    "message": "Access Denied",
    "path": "/api/users/all"
}
```

✅ **Verification**: Request is denied with 403 status (This is correct behavior!)

---

### Step 28: Student Tries to Add Course (Should Fail)
**Purpose**: Verify students cannot create courses

```
Method: POST
URL: http://localhost:8080/api/courses/add
Headers:
  Authorization: Bearer <studentToken>
  Content-Type: application/json

Body:
{
    "name": "Chemistry",
    "section": "C",
    "teacher": {
        "id": 2
    }
}

Expected Response: 403 Forbidden
{
    "timestamp": "2025-10-08T08:30:00.000+00:00",
    "status": 403,
    "error": "Forbidden",
    "message": "Access Denied",
    "path": "/api/courses/add"
}
```

✅ **Verification**: Request is denied with 403 status (This is correct behavior!)

---

### Step 29: Student Tries to Mark Attendance (Should Fail)
**Purpose**: Verify students cannot mark attendance

```
Method: POST
URL: http://localhost:8080/api/attendance/mark
Headers:
  Authorization: Bearer <studentToken>
  Content-Type: application/json

Body:
{
    "studentId": 3,
    "courseId": 1,
    "date": "2025-10-08",
    "present": true,
    "remarks": "Test"
}

Expected Response: 403 Forbidden
{
    "timestamp": "2025-10-08T08:30:00.000+00:00",
    "status": 403,
    "error": "Forbidden",
    "message": "Access Denied",
    "path": "/api/attendance/mark"
}
```

✅ **Verification**: Request is denied with 403 status (This is correct behavior!)

---

### Step 30: Student Tries to Add Marks (Should Fail)
**Purpose**: Verify students cannot add marks

```
Method: POST
URL: http://localhost:8080/api/marks/add
Headers:
  Authorization: Bearer <studentToken>
  Content-Type: application/json

Body:
{
    "student": {
        "id": 3
    },
    "course": {
        "id": 1
    },
    "score": 95.0
}

Expected Response: 403 Forbidden
{
    "timestamp": "2025-10-08T08:30:00.000+00:00",
    "status": 403,
    "error": "Forbidden",
    "message": "Access Denied",
    "path": "/api/marks/add"
}
```

✅ **Verification**: Request is denied with 403 status (This is correct behavior!)

---

### Step 31: Parent Tries to Add Course (Should Fail)
**Purpose**: Verify parents cannot create courses

```
Method: POST
URL: http://localhost:8080/api/courses/add
Headers:
  Authorization: Bearer <parentToken>
  Content-Type: application/json

Body:
{
    "name": "Biology",
    "section": "D",
    "teacher": {
        "id": 2
    }
}

Expected Response: 403 Forbidden
{
    "timestamp": "2025-10-08T08:30:00.000+00:00",
    "status": 403,
    "error": "Forbidden",
    "message": "Access Denied",
    "path": "/api/courses/add"
}
```

✅ **Verification**: Request is denied with 403 status (This is correct behavior!)

---

### Step 32: No Token - Get Courses (Should Fail)
**Purpose**: Verify authentication is required

```
Method: GET
URL: http://localhost:8080/api/courses/all
Headers:
  (No Authorization header)

Expected Response: 401 Unauthorized
{
    "timestamp": "2025-10-08T08:30:00.000+00:00",
    "status": 401,
    "error": "Unauthorized",
    "message": "Full authentication is required",
    "path": "/api/courses/all"
}
```

✅ **Verification**: Request is denied with 401 status (This is correct behavior!)

---

## 🗑️ PHASE 9: Admin Cleanup Operations (Steps 33-35)

### Step 33: Delete Course (Admin)
**Purpose**: Verify admin can delete courses (with cascade delete)

```
Method: DELETE
URL: http://localhost:8080/api/courses/1
Headers:
  Authorization: Bearer <adminToken>

Expected Response: 200 OK
"Course deleted successfully"
```

✅ **Verification**: 
- Course is deleted
- Related attendance records are automatically deleted
- Related marks records are automatically deleted

📝 **Check in Database**:
```sql
SELECT * FROM courses WHERE id = 1;  -- Should return 0 rows
SELECT * FROM attendances WHERE course_id = 1;  -- Should return 0 rows
SELECT * FROM marks WHERE course_id = 1;  -- Should return 0 rows
```

---

### Step 34: Delete User (Admin)
**Purpose**: Verify admin can delete users (with cascade delete)

```
Method: DELETE
URL: http://localhost:8080/api/users/3
Headers:
  Authorization: Bearer <adminToken>

Expected Response: 200 OK
"User deleted successfully"
```

✅ **Verification**: 
- User is deleted
- Related attendance records are automatically deleted
- Related marks records are automatically deleted

📝 **Check in Database**:
```sql
SELECT * FROM users WHERE id = 3;  -- Should return 0 rows
SELECT * FROM attendances WHERE student_id = 3;  -- Should return 0 rows
SELECT * FROM marks WHERE student_id = 3;  -- Should return 0 rows
```

---

### Step 35: Logout
**Purpose**: End the session and clear authentication

```
Method: POST
URL: http://localhost:8080/api/auth/logout
Headers:
  Authorization: Bearer <adminToken>

Expected Response: 200 OK
"Logged out successfully"
```

✅ **Verification**: Logout successful

---

## 📊 Testing Summary

### Total Tests: 35

| Phase | Tests | Expected Pass | Expected Fail |
|-------|-------|---------------|---------------|
| Phase 1 | 4 | 4 | 0 |
| Phase 2 | 3 | 3 | 0 |
| Phase 3 | 4 | 4 | 0 |
| Phase 4 | 2 | 2 | 0 |
| Phase 5 | 7 | 7 | 0 |
| Phase 6 | 3 | 3 | 0 |
| Phase 7 | 3 | 3 | 0 |
| Phase 8 | 6 | 0 | 6 (403/401 expected) |
| Phase 9 | 3 | 3 | 0 |
| **Total** | **35** | **29** | **6** |

---

## ✅ Test Completion Checklist

### Registration & Authentication
- [ ] Admin registered successfully
- [ ] Teacher registered successfully
- [ ] Student registered successfully
- [ ] Parent registered successfully
- [ ] Admin login successful with token
- [ ] Teacher login successful with token
- [ ] Student login successful with token
- [ ] Parent login successful with token

### Admin Operations
- [ ] Admin can view all users
- [ ] Admin can get user by email
- [ ] Admin can delete users
- [ ] Admin can delete courses

### Teacher Operations
- [ ] Teacher can create courses
- [ ] Teacher can view all courses
- [ ] Teacher can mark attendance
- [ ] Teacher can add marks
- [ ] Teacher can view course attendance
- [ ] Teacher can view course marks
- [ ] Teacher can view attendance by date

### Student Operations
- [ ] Student can view all courses
- [ ] Student can view own attendance
- [ ] Student can view own marks
- [ ] Student can view own profile

### Parent Operations
- [ ] Parent can view student attendance
- [ ] Parent can view student marks

### Access Control
- [ ] Student cannot access admin endpoints (403)
- [ ] Student cannot create courses (403)
- [ ] Student cannot mark attendance (403)
- [ ] Student cannot add marks (403)
- [ ] Parent cannot create courses (403)
- [ ] Unauthenticated requests are denied (401)

### Data Integrity
- [ ] Cascade delete works for courses
- [ ] Cascade delete works for users
- [ ] No orphaned records in database

---

## 🔍 Database Verification Queries

After completing all tests, run these queries to verify data integrity:

```sql
-- Check remaining users
SELECT id, name, email, role FROM users;

-- Check remaining courses
SELECT c.id, c.name, c.section, u.name as teacher_name 
FROM courses c 
LEFT JOIN users u ON c.teacher_id = u.id;

-- Check remaining attendance (should be 0 after deletions)
SELECT COUNT(*) as attendance_count FROM attendances;

-- Check remaining marks (should be 0 after deletions)
SELECT COUNT(*) as marks_count FROM marks;

-- Verify no orphaned records
SELECT * FROM attendances WHERE student_id NOT IN (SELECT id FROM users);
SELECT * FROM attendances WHERE course_id NOT IN (SELECT id FROM courses);
SELECT * FROM marks WHERE student_id NOT IN (SELECT id FROM users);
SELECT * FROM marks WHERE course_id NOT IN (SELECT id FROM courses);
```

---

## 🎯 Key Testing Points

### 1. Authentication
- ✅ JWT tokens are generated on login
- ✅ Tokens are required for protected endpoints
- ✅ Invalid tokens are rejected

### 2. Authorization
- ✅ Role-based access control works
- ✅ Admin has full access
- ✅ Teacher can manage courses and data
- ✅ Student can only view own data
- ✅ Parent can view student data

### 3. Data Operations
- ✅ CRUD operations work correctly
- ✅ Validation is enforced
- ✅ Cascade deletes maintain integrity

### 4. Security
- ✅ Passwords are never returned
- ✅ Unauthorized access is blocked
- ✅ Forbidden operations return 403
- ✅ Unauthenticated requests return 401

---

## 📝 Notes for Testers

1. **Token Management**: Always save tokens after login. They expire after 24 hours.

2. **ID Tracking**: Keep track of IDs (teacherId, studentId, courseId) as you'll need them for subsequent requests.

3. **Expected Failures**: Steps 27-32 are supposed to fail with 403/401. This validates security.

4. **Cascade Deletes**: When you delete a course or user, related records are automatically deleted.

5. **Date Format**: Always use YYYY-MM-DD format for dates.

6. **Score Range**: Marks must be between 0 and 100.

7. **Email Validation**: Emails must be in valid format.

8. **Password Security**: Minimum 6 characters required.

---

## 🚨 Common Issues & Solutions

### Issue 1: 401 Unauthorized
**Cause**: Token not provided or expired  
**Solution**: Login again and get a new token

### Issue 2: 403 Forbidden
**Cause**: User role doesn't have permission  
**Solution**: Use the correct role token (e.g., adminToken for admin operations)

### Issue 3: 404 Not Found
**Cause**: Resource doesn't exist  
**Solution**: Verify the ID exists in the database

### Issue 4: 400 Bad Request
**Cause**: Invalid data in request  
**Solution**: Check request body format and validation rules

### Issue 5: 500 Internal Server Error
**Cause**: Server-side error  
**Solution**: Check application logs for details

---

**Testing Duration**: Approximately 30-45 minutes for complete manual testing

**Last Updated**: October 8, 2025  
**Version**: 1.0
