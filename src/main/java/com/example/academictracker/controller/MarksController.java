package com.example.academictracker.controller;

import com.example.academictracker.model.Marks;
import com.example.academictracker.model.User;
import com.example.academictracker.model.Course;
import com.example.academictracker.service.MarksService;
import com.example.academictracker.service.UserService;
import com.example.academictracker.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
public class MarksController {

    @Autowired
    private MarksService marksService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> addMarks(@Valid @RequestBody Marks marks) {
        try {
            if (marks.getStudent() == null || marks.getStudent().getId() == null) {
                return ResponseEntity.badRequest().body("Student ID is required");
            }
            if (marks.getCourse() == null || marks.getCourse().getId() == null) {
                return ResponseEntity.badRequest().body("Course ID is required");
            }
            return ResponseEntity.ok(marksService.addMarks(marks));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/student/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('PARENT')")
    public ResponseEntity<?> getMarksByStudent(@PathVariable Long id) {
        try {
            List<Marks> marks = marksService.getMarksByStudentId(id);
            return ResponseEntity.ok(marks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/course/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> getMarksByCourse(@PathVariable Long id) {
        try {
            List<Marks> marks = marksService.getMarksByCourseId(id);
            return ResponseEntity.ok(marks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
