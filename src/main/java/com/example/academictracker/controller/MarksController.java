package com.example.academictracker.controller;

import com.example.academictracker.model.Marks;
import com.example.academictracker.model.User;
import com.example.academictracker.model.Course;
import com.example.academictracker.service.MarksService;
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

    @PostMapping("/add")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Marks> addMarks(@RequestBody Marks marks) {
        return ResponseEntity.ok(marksService.addMarks(marks));
    }

    @GetMapping("/student/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('PARENT')")
    public List<Marks> getMarksByStudent(@PathVariable Long id) {
        User student = new User();
        student.setId(id);
        return marksService.getMarksByStudent(student);
    }

    @GetMapping("/course/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Marks> getMarksByCourse(@PathVariable Long id) {
        Course course = new Course();
        course.setId(id);
        return marksService.getMarksByCourse(course);
    }

}
