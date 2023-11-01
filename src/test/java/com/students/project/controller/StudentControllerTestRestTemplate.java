package com.students.project.controller;

import com.students.project.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {


    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void get() {
        Student student1 = new Student(1L,"Bася", 22);
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student",   student1, Student.class);
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/student" + "?id=" + postedStudent.getId(), Student.class);
        assertEquals(postedStudent, actualStudent);

    }

    @Test
    void post_success() {
        Student student1 = new Student(1L,"Bася", 22);
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student",   student1, Student.class))
                .isNotNull();
    }

    @Test
    void put() {
    }

    @Test
    void ydolit() {
    }

    @Test
    void findStudents() {
    }


    @Test
    void filtrAge() {
    }

    @Test
    void geyAllStudent() {
    }

    @Test
    void getFacultyByStudentId() {
    }
}