package com.students.project.service;


import com.students.project.exception.StudentNotFoundException;
import com.students.project.model.Faculty;
import com.students.project.model.Student;
import com.students.project.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }


    public Student read(long id) {
        return studentRepository.findById(id).orElseThrow(() ->
                new StudentNotFoundException("Такого студента нет"));

    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }

    public void delete(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> findByAllAge(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> allStudentInformation() {
        return studentRepository.studentAll();
    }

    public Faculty getFacultyById(Long id) {
        return studentRepository.findById(id).get().getFaculty();
    }

    public List<Student> getByFacultyId(Long facultyId) {
        return studentRepository.findByFacultyId(facultyId);
    }

    public Integer getAllStudentsNumber() {
        return studentRepository.getAllStudentsNumber();
    }

    public Double getAvg() {
        return studentRepository.getAvg();
    }

    public List<Student> getFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getNamesAlphabeticalOrderUppercase() {
        return studentRepository.findAll().stream()
                .map(n -> n.getName().toUpperCase()).sorted().collect(Collectors.toList());
    }

    public double getAverageAgeOfStudents() {
        IntSummaryStatistics stats = studentRepository.findAll().stream().mapToInt(Student::getAge)
                .summaryStatistics();
        return stats.getAverage();

    }

}

