package com.students.project.controller;


import com.students.project.model.Faculty;
import com.students.project.model.Student;
import com.students.project.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/student")
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
    public void deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(name = "age", required = false) Integer age) {
        if (age != null) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(studentService.allStudentInformation());
    }

    @GetMapping({"min-And-max"})
    public List<Student> filterAge(@RequestParam int min, @RequestParam() int max) {
        return studentService.findByAllAge(min, max);
    }


    @GetMapping("All- student")
    public List<Student> geyAllStudent() {
        return studentService.allStudentInformation();
    }

    @GetMapping({"faculty-student"})
    public Faculty getFacultyByStudentId(@RequestParam Long id) {
        return studentService.getFacultyById(id);
    }

    @GetMapping({"/sum_student"})
    public Integer sumStudent() {
        return studentService.getAllStudentsNumber();
    }

    @GetMapping({"/average-value"})
    public Double getAvg() {
        return studentService.getAvg();
    }

    @GetMapping({"/five-students"})
    public List<Student> getFiveStudents() {
        return studentService.getFiveStudents();
    }

    @GetMapping({"/stream-of-name"})
    public List<String> streamName(){
        return studentService.getNamesAlphabeticalOrderUppercase();
    }

    @GetMapping({"/stream-of-age"})
    public double streamAge(){
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/print-threads")
    public void printIn() {
        studentService.printThread();
    }

    @GetMapping("/print-sync")
    public void printInSync() {
        studentService.printStudentsSync();
    }
}




