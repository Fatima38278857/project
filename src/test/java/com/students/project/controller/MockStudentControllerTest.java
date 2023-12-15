package com.students.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.students.project.model.Student;
import com.students.project.repository.StudentRepository;
import com.students.project.service.StudentService;
import org.hibernate.service.spi.InjectService;
import org.json.JSONException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void saveStudent() throws Exception {
        final long id = 1L;
        final String name = "Вася";
        final  int age = 23;



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
                .get("/id/" + id)
                .accept(MediaType.APPLICATION_JSON))
                //.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age));



    }


    @Test
    void post() throws Exception {
//        //Подготовка входных данных
//        Student studentForCreate = new Student(0L,"Иван", 20);
//
//        String request = objectMapper.writeValueAsString(studentForCreate);
//
//        Student studentId = new Student(0L,"Иван", 20);
//        long id = 1l;
//        studentId.setId(id);
//
//        //Подготовка ожидаемого результат
//        when(studentRepository.save(studentForCreate)).thenReturn(studentId);
//
//        //Начало теста
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/student")
//                        .content(request)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()) //receive
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentForCreate.getName()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentForCreate.getAge()));
//
//        verify(studentRepository).save(studentForCreate);
//        verifyNoMoreInteractions(studentRepository);
    }

    @Test
   void get() //throws Exception
    {
//        //Подготовка входных данных
//        Student studentForCreate = new Student(0L,"Иван", 20);
//        studentRepository.save(studentForCreate);
//        long id = 1l;
//
//        //Подготовка ожидаемого результат
//        Student studentId = new Student(0L,"Иван", 20);
//        studentId.setId(id);
//
//        when(studentRepository.findById(id)).thenReturn(Optional.of(studentId));
//
//        //Начало теста
//        mockMvc.perform(
//                        MockMvcRequestBuilders
//                                .get("/id")
//                                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentId.getName()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentId.getAge()));
//
//        verify(studentRepository).findById(id);
//        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void put() throws Exception {
//        //Подготовка входных данных
//        Student studentForCreate = new Student(0L,"Иван", 20);
//
//        String request = objectMapper.writeValueAsString(studentForCreate);
//
//        Student studentId = new Student(0L,"Саша", 22);
//        long id = 1l;
//        studentId.setId(id);
//
//        //Подготовка ожидаемого результат
//        when(studentRepository.save(studentForCreate)).thenReturn(studentId);
//
//        //Начало теста
//        mockMvc.perform(MockMvcRequestBuilders
//                .put("/student")
//                        .content(request)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()) //receive
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(studentForCreate.getName()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(studentForCreate.getAge()));
//
//        verify(studentRepository).findById(id);
//        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void findStudents() {
    }

    @Test
    void filterAge() {
    }
}