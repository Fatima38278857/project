package com.students.project.controller;


import com.students.project.model.Faculty;
import com.students.project.model.Student;
import com.students.project.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {



    private final FacultyService facultyService;

    public FacultyController (FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public Faculty get(@PathVariable Long id) {
        return facultyService.readFaculty(id);
    }

    @PostMapping
    public Faculty post(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public Faculty put(@RequestBody Faculty faculty) {
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public void ydolit(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping({"/color-faculty"})
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(name = "color", required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }


    @GetMapping("filter")
    public ResponseEntity<Faculty> filterByNameAndColor(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.filtrName(name));
        }
        if (color != null && !color.isBlank())
            return ResponseEntity.ok(facultyService.filtrColor(color));
          return null;
    }

    @GetMapping({"student-faculty"})
    public List<Student> getStudentByFacultyId(@RequestParam Long id){
        return facultyService.getStudentOfId(id);
    }

}


