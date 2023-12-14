package com.students.project.controller;
import com.students.project.model.Student;
import com.students.project.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {
    @Autowired
    StudentRepository studentRepository;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void initDb() {
        studentRepository.deleteAll();
    }


    @Test
    void get() {
        Student student1 = new Student(0L, "Bася", 22);
        Student posted1 = restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class);
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/student/" + posted1.getId(), Student.class);
        assertEquals(student1, actualStudent);
    }

    @Test
    void post_success() {
        Student student2 = new Student(0L, "vdmcm", 25);
        Student posted2 = restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class);
        assertEquals(student2, restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class));
    }

    @Test
    void put() {
        Student student3 = new Student(0L, "dcdcsdcs", 22);
        Student posted3 = restTemplate.postForObject("http://localhost:" + port + "/student", student3, Student.class);
        HttpEntity<Student> entity = new HttpEntity<Student>(student3);
        Student response = restTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT, entity, Student.class).getBody();

        assertEquals(student3, response);

    }


    @Test
    void deleteStudent() {
        Student student4 = new Student(0L, "vdvdkv", 22);
        Student posted4 = restTemplate.postForObject("http://localhost:" + port + "/student", student4, Student.class);
        String url = "http://localhost:" + port + "/student/" + posted4.getId();
        restTemplate.delete(url);
        Student actualStudent = restTemplate.getForObject("http://localhost:" + port + "/student/" + student4.getId(), Student.class);
        assertNull(actualStudent.getId());
    }

      /*   Тест findStudents  получает на вход список студентов,
           следом идет сохронение, затем в url пишем результат
           который хотим получить. exchange - обеспечивает дополнительную гибкость, если это необходимо.
            Он принимает RequestEntity и возвращает ResponseEntity.
            Эти методы позволяют использовать ParameterizedTypeReference вместо Class для
            задания типа ответа с помощью дженериков.
                 */
    @Test
    void findStudents() {
        Student student1 = new Student(0L, "Bася", 22);
        Student student2 = new Student(0L, "Bася", 25);
        Student student3 = new Student(0L, "Bася", 22);
        List<Student> expectedStudent = new ArrayList<>(List.of(
                student1,
                student3
        ));
        studentRepository.save(student1);
        studentRepository.save(student3);
        String url = "http://localhost:" + port + "/student/age" + "?age=" + "22";
        ResponseEntity<List<Student>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
        });
        assertEquals(expectedStudent, response.getBody());

    }

    @Test
    void filterAge() {
        Student student1 = new Student(0L, "Bася", 14);
        Student student2 = new Student(0L, "Bася", 16);
        Student student3 = new Student(0L, "Bася", 17);
        Student student4 = new Student(0L, "Bася", 13);
        List<Student> expectedStudent = new ArrayList<>(List.of(
                student1,
                student2,
                student3
        ));
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        String url = "http://localhost:" + port + "/student" + "/min-And-max" + "?min=" + "14" + "&max=" + "17";
        ResponseEntity<List<Student>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
        });
        assertEquals(expectedStudent, response.getBody());
    }

}