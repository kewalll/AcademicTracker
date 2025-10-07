package com.example.academictracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String section;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    public Course() {}

    public Course(String name, String section, User teacher) {
        this.name = name;
        this.section = section;
        this.teacher = teacher;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public User getTeacher() { return teacher; }
    public void setTeacher(User teacher) { this.teacher = teacher; }

    @Override
    public String toString() {
        return "Course{id=" + id + ", name='" + name + "', section='" + section + "'}";
    }
}
