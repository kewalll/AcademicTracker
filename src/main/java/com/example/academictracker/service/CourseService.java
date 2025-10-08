package com.example.academictracker.service;

import com.example.academictracker.model.Course;
import com.example.academictracker.repository.CourseRepository;
import com.example.academictracker.repository.AttendanceRepository;
import com.example.academictracker.repository.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MarksRepository marksRepository;

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + id));
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + id));
        
        // Delete related records first
        attendanceRepository.deleteByCourse(course);
        marksRepository.deleteByCourse(course);
        
        // Now delete the course
        courseRepository.deleteById(id);
    }
}
