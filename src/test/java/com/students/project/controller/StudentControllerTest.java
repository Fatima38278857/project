package com.students.project.controller;

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
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class))
                .isNotNull();
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
        Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/student/" + student1.getId(), Student.class);
        assertNull(actualStudent.getId());

    }

    @Test
    void findStudents() {
        Student student1 = new Student(1L, "Bася", 22);
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class);
        String url = "http://localhost:" + port + "/student" + "?age=" + "22";
        Student[] students = restTemplate.getForObject(url, Student[].class);
        assertEquals(student1.getAge(), students[0].getAge());
    }




       @Test
      void filterAge() {
            List<Student> age = studentController(14, 17);
            String url = "http://localhost:" + port + "/student" + "/min-And-max" + "?min=" + "14" + "&max=" + "17";
            Student[] students = restTemplate.getForObject(url, Student[].class, age);
            assertEquals(studentController.filtrAge(14, 17),  );



//
            //int[] age = {14, 15, 16, 17, 18, 11, 15, 22, 25};





//            List<Student> studentList = new ArrayList<>(Arrays.asList(
//                    new Student(1L, "Катя", 19),
//                    new Student(2L, "Дима", 17),
//                    new Student(3L, "Олег", 13),
//                    new Student(4L, "Вика", 16)));
//            List<Student> student = new ArrayList<>();
//            Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

           // "?min="
            //"&max="
//            int[] age = {14, 15, 16, 17, 18, 11, 15, 22, 25};
//            int min = 0;
//            int max = 0;

//            Student actualStudent = this.restTemplate.getForObject("http://localhost:" + port + "/student/" + student1.getId(), Student.class);

        //}
    @Test
        void getFacultyByStudentId () {


        }
}


