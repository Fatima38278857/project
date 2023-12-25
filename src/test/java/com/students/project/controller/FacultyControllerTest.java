package com.students.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.students.project.model.Faculty;
import com.students.project.model.Student;
import com.students.project.repository.FacultyRepository;
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
class FacultyControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    FacultyRepository facultyRepository;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void initDb() {
        facultyRepository.deleteAll();
    }

    @Test
    void get() {
        Faculty faculty = new Faculty(0L, "Грифиндор", "Золото");
        facultyRepository.save(faculty);
        Faculty actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class);
        assertEquals(faculty, actualStudent);
    }

    @Test
    void post() {
        Faculty faculty = new Faculty(0L, "Грифиндор", "Золото");
        facultyRepository.save(faculty);
        assertEquals(faculty, restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class));
    }

    @Test
    void put() {
        Faculty faculty = new Faculty(0L, "Грифиндор", "Золото");
        facultyRepository.save(faculty);
        HttpEntity<Faculty> entity = new HttpEntity<Faculty>(faculty);
        Faculty response = restTemplate.exchange("http://localhost:" + port + "/faculty", HttpMethod.PUT, entity, Faculty.class).getBody();
        assertEquals(faculty, response);
    }

    @Test
    void deleteFaculty() {
        Faculty faculty = new Faculty(0L, "Грифиндор", "Золото");
        facultyRepository.save(faculty);
        String url = "http://localhost:" + port + "/faculty/" + faculty.getId();
        restTemplate.delete(url);
        Faculty actualFaculty = restTemplate.getForObject("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class);
        assertNull(actualFaculty.getId());
    }

    @Test
    void findFaculties() {

        Faculty faculty1 = new Faculty(0L, "Грифиндор", "Золото");
        Faculty faculty2 = new Faculty(0L, "Слизорин", "Зелёный");
        Faculty faculty3 = new Faculty(0L, "Когтиврам", "Синий");
        List<Faculty> expectedFaculty = new ArrayList<>(List.of(
                faculty1
        ));
        facultyRepository.save(faculty1);
        String url = "http://localhost:" + port + "/faculty/" + "faculty" + "?color=" + "Золото";
        ResponseEntity<List<Faculty>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Faculty>>() {
        });
        assertEquals(expectedFaculty, response.getBody());
    }

    @Test
    void filterByNameAndColor() {

        Faculty faculty1 = new Faculty(0L, "Грифендор", "Золото");

        facultyRepository.save(faculty1);
        String url = "http://localhost:" + port + "/faculty/" + "filter" + "?name="  + "Грифендор"  + "&color=" + "Золото";
        ResponseEntity<Faculty> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        assertEquals(faculty1, response.getBody());
    }
}