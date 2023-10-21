package com.students.project.controller;


import com.students.project.model.Faculty;
import com.students.project.model.Student;
import com.students.project.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public Student get(@PathVariable Long id) {
        return studentService.read(id);
    }

    @PostMapping
    public Student post(@RequestBody Student student) {
        return studentService.create(student);
    }

    @PutMapping
    public Student put(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("{id}")
    public void ydolit(@PathVariable Long id) {
        studentService.deletee(id);
    }

    @GetMapping(params = {"age"})
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(name = "age", required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping({"min-And-min"})
    public List<Student> filtrAge(@RequestParam int min, @RequestParam() int max) {
        return  studentService.findByAllAge(min, max);
    }


    @GetMapping("All- student")
    public List<Student> geyAllStudent(){
        return studentService.AllStudentInformation();
    }

    @GetMapping({"faculty-student"})
    public Faculty getFacultyByStudentId(@RequestParam Long id){
        return studentService.getFacultyById(id);
    }
}



