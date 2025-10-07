package com.example.academictracker.service;

import com.example.academictracker.model.Marks;
import com.example.academictracker.model.User;
import com.example.academictracker.model.Course;
import com.example.academictracker.repository.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    public Marks addMarks(Marks marks) {
        return marksRepository.save(marks);
    }

    public List<Marks> getMarksByStudent(User student) {
        return marksRepository.findByStudent(student);
    }

    public List<Marks> getMarksByCourse(Course course) {
        return marksRepository.findByCourse(course);
    }
}
