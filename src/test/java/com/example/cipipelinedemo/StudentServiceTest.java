package com.example.cipipelinedemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class StudentServiceTest {

    private final StudentService service = new StudentService();

    @Test
    void testGetAllStudents() {
        assertEquals(2, service.getAllStudents().size());
    }

    @Test
    void testGetStudentById() {
        Student student = service.getStudentById(1);

        assertNotNull(student);
        assertEquals("Alice", student.getName());
    }

    @Test
    void testStudentNotFound() {
        Student student = service.getStudentById(99);

        assertNull(student);
    }

    @Test
    void testStudentCount() {
        assertEquals(2, service.getStudentCount());
    }
}
