package com.students.project.controller;

import com.students.project.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {


    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;


    Student student1 = new Student(1L, "Bася", 22);
    Student student2 = new Student(2L, "vdmcm", 25);
    Student student3 = new Student(3L, "dcdcsdcs", 22);
    Student student4 = new Student(4L, "vdvdkv", 22);
    Student student5 = new Student(5L, "vdvdkcndnvv", 22);
    Student posted1 = restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class);
    Student posted2 = restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class);
    Student posted3 = restTemplate.postForObject("http://localhost:" + port + "/student", student3, Student.class);
    Student posted4 =  restTemplate.postForObject("http://localhost:" + port + "/student", student4, Student.class);
    Student posted5  = restTemplate.postForObject("http://localhost:" + port + "/student", student5, Student.class);

    @Test
    void get() {
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/student/" + posted1.getId(), Student.class);
        assertEquals(student1, actualStudent);
    }

    @Test
    void post_success() {
        assertEquals(student1, restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class));
    }

    @Test
    void put() {
        HttpEntity<Student> entity = new HttpEntity<Student>(student3);
        Student response = restTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT, entity, Student.class).getBody();

        assertEquals(student3, response);

    }


    @Test
    void deleteStudent() {
//        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class);
        String url = "http://localhost:" + port + "/student/" + posted4.getId();
        restTemplate.delete(url);
        Student actualStudent = restTemplate.getForObject("http://localhost:" + port + "/student/" + student4.getId(), Student.class);
        assertNull(actualStudent.getId());

    }

    @Test
    void findStudents()  {
//        Student student1 = new Student(1L, "Bася", 22);
//        Student student2 = new Student(2L, "Bася", 25);
//        Student student3 = new Student(3L, "Bася", 22);
        List<Student> expectedStudent = new ArrayList<>(List.of(
                student1,
                student3
        ));
//        restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class);
//        restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class);
//        restTemplate.postForObject("http://localhost:" + port + "/student", student3, Student.class);
        String url = "http://localhost:" + port + "/student" + "?age=" + "22";
        List<?> actualStudent = restTemplate.getForObject(url, List.class);
        assertEquals(expectedStudent, actualStudent);


    }

    @Test
    void filterAge() {
        List<Student> expectedStudent = new ArrayList<>(List.of(
                student1,
                student3
        ));
//        restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class);
//        restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class);
//        restTemplate.postForObject("http://localhost:" + port + "/student", student3, Student.class);
        String url = "http://localhost:" + port + "/student" + "/min-And-max" + "?min=" + "14" + "&max=" + "17";
        List<?> actualStudent = restTemplate.getForObject(url, List.class);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getFacultyByStudentId() {
    }
}