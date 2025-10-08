package com.example.academictracker.service;

import com.example.academictracker.model.Attendance;
import com.example.academictracker.model.User;
import com.example.academictracker.model.Course;
import com.example.academictracker.repository.AttendanceRepository;
import com.example.academictracker.repository.UserRepository;
import com.example.academictracker.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceByStudent(User student) {
        return attendanceRepository.findByStudent(student);
    }

    public List<Attendance> getAttendanceByStudentId(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        return attendanceRepository.findByStudent(student);
    }

    public List<Attendance> getAttendanceByCourse(Course course) {
        return attendanceRepository.findByCourse(course);
    }

    public List<Attendance> getAttendanceByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
        return attendanceRepository.findByCourse(course);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }
}
