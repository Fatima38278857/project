package com.students.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.students.project.model.Student;
import com.students.project.repository.StudentRepository;
import com.students.project.service.StudentService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
class MockStudentControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;

    @InjectMocks
    StudentController studentController;


    @Test
    void restStudent() throws Exception {
        final long id = 1L;
        final String name = "Вася";
        final int age = 23;


        JSONObject studentObject = new JSONObject();
        studentObject.put("Вася", name);
        studentObject.put(String.valueOf(23), age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age));


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id))
                .andExpect(status().isOk());

    }


    @Test
    void findStudents() throws Exception {
        //Подготовка входных данных
        Student student = new Student(1L, "Caving", 22);
        Student student1 = new Student(2L, "Sasha", 22);
        Student student3 = new Student(3L, "Sas", 19);

        //Подготовка ожидаемого результат
        when(studentRepository.findAll()).thenReturn(List.of(student, student1));

        //age?age=22'
        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age" + "?age=" + 22)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Caving"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Sasha"));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].name").value("Sas"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].age").value(19));


    }

    @Test
    void filterAge() throws Exception {
        //Подготовка входных данных
        Student student = new Student(1L, "Caving", 20);
        Student student1 = new Student(2L, "Sasha", 23);
        Student student3 = new Student(3L, "Sas", 25);
        Student student4 = new Student(4L, "Sasj", 14);

        //Подготовка ожидаемого результат
        when(studentRepository.findByAgeBetween(20, 25)).thenReturn(List.of(student, student1, student3));

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/min-And-max" + "?min=" + 20 + "&max=" + 25)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Caving"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].age").value(23))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Sasha"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].name").value("Sas"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].age").value(25));

    }
}