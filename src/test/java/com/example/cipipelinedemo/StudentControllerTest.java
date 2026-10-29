
package com.example.cipipelinedemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService service;

    @Test
    void testGetAllStudents() throws Exception {

        when(service.getAllStudents()).thenReturn(
            Arrays.asList(
                new Student(1, "Alice"),
                new Student(2, "Bob")
            )
        );

        mockMvc.perform(get("/students"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    void testGetStudentById() throws Exception {

        when(service.getStudentById(1))
            .thenReturn(new Student(1, "Alice"));

        mockMvc.perform(get("/students/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    void testStudentNotFound() throws Exception {

        when(service.getStudentById(99))
            .thenReturn(null);

        mockMvc.perform(get("/students/99"))
            .andExpect(status().isNotFound());
    }
}
