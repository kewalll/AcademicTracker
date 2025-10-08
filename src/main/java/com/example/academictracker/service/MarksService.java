package com.example.academictracker.service;

import com.example.academictracker.model.Marks;
import com.example.academictracker.model.User;
import com.example.academictracker.model.Course;
import com.example.academictracker.repository.MarksRepository;
import com.example.academictracker.repository.UserRepository;
import com.example.academictracker.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Marks addMarks(Marks marks) {
        return marksRepository.save(marks);
    }

    public List<Marks> getMarksByStudent(User student) {
        return marksRepository.findByStudent(student);
    }

    public List<Marks> getMarksByStudentId(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        return marksRepository.findByStudent(student);
    }

    public List<Marks> getMarksByCourse(Course course) {
        return marksRepository.findByCourse(course);
    }

    public List<Marks> getMarksByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
        return marksRepository.findByCourse(course);
    }
}
