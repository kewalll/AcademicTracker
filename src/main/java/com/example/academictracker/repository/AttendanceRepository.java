package com.example.academictracker.repository;

import com.example.academictracker.model.Attendance;
import com.example.academictracker.model.User;
import com.example.academictracker.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudent(User student);
    List<Attendance> findByCourse(Course course);
    List<Attendance> findByDate(LocalDate date);
    void deleteByStudent(User student);
    void deleteByCourse(Course course);
}
