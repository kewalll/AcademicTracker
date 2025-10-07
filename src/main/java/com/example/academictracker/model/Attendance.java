package com.example.academictracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDate date;
    private boolean present;

    public Attendance() {}

    public Attendance(User student, Course course, LocalDate date, boolean present) {
        this.student = student;
        this.course = course;
        this.date = date;
        this.present = present;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public boolean isPresent() { return present; }
    public void setPresent(boolean present) { this.present = present; }

    @Override
    public String toString() {
        return "Attendance{id=" + id + ", student=" + student.getName() + ", course=" + course.getName() + ", date=" + date + ", present=" + present + "}";
    }
}
