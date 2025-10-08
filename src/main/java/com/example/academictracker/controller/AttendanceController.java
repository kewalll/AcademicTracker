package com.example.academictracker.controller;

import com.example.academictracker.dto.AttendanceDTO;
import com.example.academictracker.model.Attendance;
import com.example.academictracker.model.User;
import com.example.academictracker.model.Course;
import com.example.academictracker.service.AttendanceService;
import com.example.academictracker.service.UserService;
import com.example.academictracker.service.CourseService;
import jakarta.validation.Valid;
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

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/mark")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> markAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO) {
        try {
            User student = userService.getUserById(attendanceDTO.getStudentId());
            Course course = courseService.getCourseById(attendanceDTO.getCourseId());

            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setCourse(course);
            attendance.setDate(attendanceDTO.getDate());
            attendance.setPresent(attendanceDTO.isPresent());
            attendance.setRemarks(attendanceDTO.getRemarks());

            return ResponseEntity.ok(attendanceService.markAttendance(attendance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/student/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('PARENT')")
    public ResponseEntity<?> getAttendanceByStudent(@PathVariable Long id) {
        try {
            List<Attendance> attendances = attendanceService.getAttendanceByStudentId(id);
            return ResponseEntity.ok(attendances);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/course/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> getAttendanceByCourse(@PathVariable Long id) {
        try {
            List<Attendance> attendances = attendanceService.getAttendanceByCourseId(id);
            return ResponseEntity.ok(attendances);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Attendance> getAttendanceByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return attendanceService.getAttendanceByDate(localDate);
    }

}
