package com.students.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.students.project.model.Student;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.min;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;
    private ObjectMapper objectMapper;


    @Test
    void get() {
        Student student1 = new Student(1L, "Bася", 22);
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class);
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/student/" + student1.getId(), Student.class);
        assertEquals(student1, actualStudent);
    }

    @Test
    void post_success() {
        Student student1 = new Student(1L, "Bася", 22);
        assertEquals(student1, restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class));
    }

    @Test
    void put() {
        // Принимает
        Student student1 = new Student(1L, "Bася", 22);
        HttpEntity<Student> entity = new HttpEntity<Student>(student1);
        Student response = restTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT, entity, Student.class).getBody();

        assertEquals(student1, response);

    }


    @Test
    void ydolit() {
        Student student1 = new Student(1L, "Bася", 22);
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class);
        String url = "http://localhost:" + port + "/student/" + postedStudent.getId();
        restTemplate.delete(url);
        Student actualStudent = restTemplate.getForObject("http://localhost:" + port + "/student/" + student1.getId(), Student.class);
        assertNull(actualStudent.getId());

    }

    @Test
    void findStudents()  {
        Student student1 = new Student(1L, "Bася", 22);
        Student student2 = new Student(2L, "Bася", 25);
        Student student3 = new Student(3L, "Bася", 22);
        List<Student> expectedStudent = new ArrayList<>(List.of(
                student1,
                student3
        ));
        restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class);
        restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class);
        restTemplate.postForObject("http://localhost:" + port + "/student", student3, Student.class);
        String url = "http://localhost:" + port + "/student" + "?age=" + "22";
        List<?> actualStudent = restTemplate.getForObject(url, List.class);
        assertEquals(expectedStudent, actualStudent);


    }

       @Test
      void filterAge() {
           Student student1 = new Student(1L, "Bася", 22);
           Student student2 = new Student(2L, "Bася", 25);
           Student student3 = new Student(3L, "Bася", 22);
           List<Student> expectedStudent = new ArrayList<>(List.of(
                   student1,
                   student3
           ));
           restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class);
           restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class);
           restTemplate.postForObject("http://localhost:" + port + "/student", student3, Student.class);
           String url = "http://localhost:" + port + "/student" + "/min-And-max" + "?min=" + "14" + "&max=" + "17";
           List<?> actualStudent = restTemplate.getForObject(url, List.class);
           assertEquals(expectedStudent, actualStudent);
       }

    @Test
        void getFacultyByStudentId () {


        }
}


