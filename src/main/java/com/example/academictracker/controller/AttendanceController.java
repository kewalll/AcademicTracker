package com.example.academictracker.controller;

import com.example.academictracker.model.Attendance;
import com.example.academictracker.model.User;
import com.example.academictracker.model.Course;
import com.example.academictracker.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/mark")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Attendance> markAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.markAttendance(attendance));
    }

    @GetMapping("/student/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('PARENT')")
    public List<Attendance> getAttendanceByStudent(@PathVariable Long id) {
        User student = new User();
        student.setId(id);
        return attendanceService.getAttendanceByStudent(student);
    }

    @GetMapping("/course/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Attendance> getAttendanceByCourse(@PathVariable Long id) {
        Course course = new Course();
        course.setId(id);
        return attendanceService.getAttendanceByCourse(course);
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Attendance> getAttendanceByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return attendanceService.getAttendanceByDate(localDate);
    }

}
