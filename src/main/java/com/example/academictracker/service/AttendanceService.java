package com.example.academictracker.service;

import com.example.academictracker.model.Attendance;
import com.example.academictracker.model.User;
import com.example.academictracker.model.Course;
import com.example.academictracker.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceByStudent(User student) {
        return attendanceRepository.findByStudent(student);
    }

    public List<Attendance> getAttendanceByCourse(Course course) {
        return attendanceRepository.findByCourse(course);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }
}
