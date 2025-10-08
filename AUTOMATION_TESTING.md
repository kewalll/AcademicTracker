# 🚀 Complete Testing Guide - Academic Tracker API

## Quick Start (3 Steps)

### Step 1: Setup Database
```sql
CREATE DATABASE IF NOT EXISTS academictracker;
```

### Step 2: Start Application
```bash
mvn spring-boot:run
```
Wait for: `Started AcademictrackerApplication`

### Step 3: Import to Postman
1. Open Postman
2. Click **Import**
3. Select: `Academic_Tracker_Sequential.postman_collection.json`
4. Import: `environment.json`
5. Select environment from dropdown (top right)

---

## 📋 Testing Sequence (35 Requests)

### ✅ Phase 1: Register Users (1-4)
No authentication needed. Creates all user roles.

### ✅ Phase 2: Admin Login & Operations (5-7)
- Login as Admin → Token auto-saved to `{{adminToken}}`
- Get all users
- Get user by email

### ✅ Phase 3: Teacher Login & Operations (8-11)
- Login as Teacher → Token auto-saved to `{{teacherToken}}`, ID to `{{teacherId}}`
- Add courses (Mathematics, Physics)
- View all courses

### ✅ Phase 4: Student Login (12-13)
- Login as Student → Token auto-saved to `{{studentToken}}`, ID to `{{studentId}}`
- View courses

### ✅ Phase 5: Teacher Data Entry (14-20)
- Mark attendance (present, absent, another day)
- Add marks
- View attendance by course
- View marks by course
- View attendance by date

### ✅ Phase 6: Student Views Data (21-23)
- View own attendance
- View own marks
- View own profile

### ✅ Phase 7: Parent Login & View (24-26)
- Login as Parent → Token auto-saved to `{{parentToken}}`
- View student attendance
- View student marks

### ✅ Phase 8: Access Control Tests (27-32)
**These SHOULD fail with 403/401:**
- Student tries to get all users → 403
- Student tries to add course → 403
- Student tries to mark attendance → 403
- Student tries to add marks → 403
- Parent tries to add course → 403

### ✅ Phase 9: Admin Cleanup (33-35)
- Delete course
- Delete user
- Logout

---

## 🔑 Environment Variables (Auto-Set)

The collection automatically sets these variables:

| Variable | Set By | Used For |
|----------|--------|----------|
| `baseUrl` | Manual | All requests |
| `adminToken` | Request 5 | Admin operations |
| `teacherToken` | Request 8 | Teacher operations |
| `studentToken` | Request 12 | Student operations |
| `parentToken` | Request 24 | Parent operations |
| `teacherId` | Request 8 | Creating courses |
| `studentId` | Request 12 | Attendance & marks |
| `courseId` | Request 9 | Attendance & marks |

---

## 🎯 How to Run

### Option 1: Run All (Collection Runner)
1. Click collection → **Run**
2. Select environment
3. Set delay: **500ms**
4. Click **Run Academic Tracker - Sequential Testing**
5. Watch all 35 requests execute in order

### Option 2: Run Manually (Step by Step)
1. Start with Phase 1 (Register Users)
2. Continue through each phase in order
3. Check console for "✅" messages confirming tokens saved

### Option 3: Newman (CLI)
```bash
# Install Newman
npm install -g newman

# Run tests
newman run Academic_Tracker_Sequential.postman_collection.json -e environment.json --delay-request 500

# With HTML report
newman run Academic_Tracker_Sequential.postman_collection.json -e environment.json --delay-request 500 -r htmlextra
```

---

## ⚠️ Common Issues

### 403 Forbidden
**Cause**: Wrong token or not logged in with correct role  
**Solution**: 
- Ensure you ran the login request for that role
- Check Tests tab has the token-saving script
- Verify correct token variable (e.g., `{{teacherToken}}` for teacher operations)

### 401 Unauthorized
**Cause**: No token or invalid token  
**Solution**: Login first to get token

### 500 Internal Server Error - JWT Key
**Cause**: JWT secret too short  
**Solution**: Already fixed in `application.properties` (88 characters)

### Missing IDs (studentId, courseId, teacherId)
**Cause**: Login requests don't have Tests tab scripts  
**Solution**: Import the provided collection - it has all scripts

---

## 📊 Expected Results

| Phase | Success Count | Fail Count | Notes |
|-------|---------------|------------|-------|
| 1 | 4 | 0 | All registrations succeed |
| 2 | 3 | 0 | Admin operations |
| 3 | 4 | 0 | Teacher operations |
| 4 | 2 | 0 | Student login |
| 5 | 7 | 0 | Teacher data entry |
| 6 | 3 | 0 | Student views data |
| 7 | 3 | 0 | Parent views data |
| 8 | 6 | 0 | Access control (403/401 expected) |
| 9 | 3 | 0 | Admin cleanup |
| **Total** | **35** | **0** | All pass (including expected 403/401) |

---

## 🔍 Verify in Database

After running tests:

```sql
-- Check users
SELECT id, name, email, role FROM users;

-- Check courses
SELECT c.id, c.name, c.section, u.name as teacher_name 
FROM courses c 
LEFT JOIN users u ON c.teacher_id = u.id;

-- Check attendance
SELECT a.id, s.name as student, c.name as course, a.date, a.present, a.remarks
FROM attendances a
JOIN users s ON a.student_id = s.id
JOIN courses c ON a.course_id = c.id;

-- Check marks
SELECT m.id, s.name as student, c.name as course, m.score
FROM marks m
JOIN users s ON m.student_id = s.id
JOIN courses c ON m.course_id = c.id;
```

---

## 📝 Test Credentials

| Role | Email | Password |
|------|-------|----------|
| ADMIN | admin@test.com | admin123 |
| TEACHER | teacher@test.com | teacher123 |
| STUDENT | student@test.com | student123 |
| PARENT | parent@test.com | parent123 |

---

## 🎨 Files Included

1. **`Academic_Tracker_Sequential.postman_collection.json`** - Main collection (35 requests)
2. **`environment.json`** - Environment variables
3. **`POSTMAN_STEP_BY_STEP.md`** - Detailed step-by-step guide
4. **`POSTMAN_MANUAL_SETUP.md`** - Manual setup if import fails
5. **`POSTMAN_AUTOMATION.md`** - Automation with Newman
6. **`README_TESTING.md`** - This file

---

## ✨ Key Features

✅ **Proper sequence** - Login before operations  
✅ **Auto-save tokens** - Tests tab scripts save tokens automatically  
✅ **Auto-save IDs** - Teacher, Student, Course IDs saved  
✅ **Access control tests** - Validates 403/401 errors  
✅ **Role-based testing** - Each role tested separately  
✅ **Complete workflow** - Register → Login → Operations → Cleanup  

---

## 🚦 Status Indicators

- ✅ **200 OK** - Success
- ❌ **403 Forbidden** - Access denied (expected for access control tests)
- ❌ **401 Unauthorized** - Not authenticated (expected for no-token test)
- ❌ **400 Bad Request** - Invalid data
- ❌ **500 Internal Server Error** - Server error

---

## 🎯 Success Criteria

All 35 requests should complete:
- Requests 1-26: **200 OK**
- Request 27-31: **403 Forbidden** (expected)
- Request 32: **401 Unauthorized** (expected)
- Requests 33-35: **200 OK**

---

**Happy Testing! 🎉**
