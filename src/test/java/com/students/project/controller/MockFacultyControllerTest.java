package com.students.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.students.project.model.Faculty;
import com.students.project.model.Student;
import com.students.project.repository.FacultyRepository;
import com.students.project.repository.StudentRepository;
import com.students.project.service.FacultyService;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
class MockFacultyControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    FacultyController facultyController;


      // Test for REST surgery
    @Test
    void restFaculty () throws Exception {
        final long id = 1L;
        final String name = "Грифендор";
        final String color = "Золото";


        JSONObject facultyObject = new JSONObject();
        facultyObject.put("Грифендор", name);
        facultyObject.put("Золото", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
           // For POST
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(color));
                 // For GET
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(color));
                // For PUT
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(color));


         // For DELETE
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id))
                .andExpect(status().isOk());
    }


    // To filter by color
    @Test
    void findFaculties() throws Exception {
        //Подготовка входных данных
        Faculty faculty = new Faculty(1L, "Грифендор", "Золото");
        Faculty faculty1 = new Faculty(2L, "Слизорин", "Зеленый");
        Faculty faculty2 = new Faculty(3L, "Пуфендуй", "Коричневый");

        //Подготовка ожидаемого результат
        when(facultyRepository.findAll()).thenReturn(List.of(faculty));


        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty" + "/filterAndColor" + "?color=" + "Золото")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].color").value("Золото"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Грифендор"));

    }
    // To filter by color and name
    @Test
    void filterByNameAndColor() throws Exception {
        //Подготовка входных данных
        Faculty faculty = new Faculty(1L, "Грифендор", "Золото");
        Faculty faculty1 = new Faculty(2L, "Слизорин", "Зеленый");
        Faculty faculty2 = new Faculty(3L, "Пуфендуй", "Коричневый");

        //Подготовка ожидаемого результат
        when(facultyRepository.findFacultyByNameAndColorIgnoreCase(any(), any())).thenReturn(faculty);


        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty" + "/filterByName-Color" + "?name=" + "Грифендор" + "&color=" + "Золото")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Грифендор"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("Золото"));

    }
}