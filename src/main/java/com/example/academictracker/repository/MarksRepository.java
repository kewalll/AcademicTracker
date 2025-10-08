package com.example.academictracker.repository;

import com.example.academictracker.model.Marks;
import com.example.academictracker.model.User;
import com.example.academictracker.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long> {
    List<Marks> findByStudent(User student);
    List<Marks> findByCourse(Course course);
    void deleteByStudent(User student);
    void deleteByCourse(Course course);
}
