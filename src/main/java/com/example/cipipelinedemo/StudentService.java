
package com.example.cipipelinedemo;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

    public List<Student> getAllStudents() {
        return Arrays.asList(
            new Student(1, "Alice"),
            new Student(2, "Bob")
        );
    }

    public Student getStudentById(int id) {
        if (id == 1) {
            return new Student(1, "Alice");
        }

        if (id == 2) {
            return new Student(2, "Bob");
        }

        return null;
    }

    public int getStudentCount() {
        return getAllStudents().size();
    }
}
