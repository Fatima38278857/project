package com.students.project.controller;

import com.students.project.model.Faculty;
import com.students.project.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {


    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void get() {
        Faculty faculty = new Faculty(1L, "Грифендор", "Золото");
        Faculty postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        Faculty actualFaculty = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class);
        assertEquals(faculty, actualFaculty);
    }

    @Test
    void post() {
        Faculty faculty = new Faculty(1L, "Грифендор", "Золото");
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class))
                .isNotNull();
    }

    @Test
    void put() {
        Faculty faculty = new Faculty(1L, "Грифендор", "Золото");
        HttpEntity<Faculty> entity = new HttpEntity<Faculty>(faculty);
        Faculty response = restTemplate.exchange("http://localhost:" + port + "/faculty", HttpMethod.PUT, entity, Faculty.class).getBody();
        assertEquals(faculty, response);

    }

    @Test
    void ydolit() {
        Faculty faculty = new Faculty(1L, "Грифендор", "Золото");
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        String fooResourceUrl = "http://localhost:" + port + "/faculty" + "?id=" + postedFaculty.getId();
        restTemplate.delete(fooResourceUrl);
    }

    @Test
    void findFaculties() {
        Faculty faculty = new Faculty(1L, "Грифендор", "Золото");
        String url = "http://localhost:" + port + "/faculty" + "?color=" + "Золото";
        Faculty[] faculty1 = restTemplate.getForObject(url + "/Золото", Faculty[].class);
        assertEquals(faculty.getColor(), faculty1[0].getColor());


    }

    @Test
    void filterByNameAndColor() {
    }

    @Test
    void getStudentByFacultyId() {
    }
}