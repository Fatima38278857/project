package com.students.project.service;


import com.students.project.exception.StudentNotFoundException;
import com.students.project.model.Faculty;
import com.students.project.model.Student;
import com.students.project.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private  final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student create(Student student) {
        logger.info("Был вызван create");
        return studentRepository.save(student);
    }


    public Student read(long id) {
        logger.info("Был вызван read");
        return studentRepository.findById(id).orElseThrow(() ->
                new StudentNotFoundException("Такого студента нет"));

    }

    public Student update(Student student) {
        logger.info("Был вызван update");
        return studentRepository.save(student);
    }

    public void delete(long id) {
        logger.info("Был вызван delete");
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Был вызван findByAge");
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> findByAllAge(int min, int max) {
        logger.info("Был вызван findByAllAge");
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> allStudentInformation() {
        logger.info("Был вызван allStudentInformation");
        return studentRepository.studentAll();
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Был вызван getFacultyById");
        return studentRepository.findById(id).get().getFaculty();
    }

    public List<Student> getByFacultyId(Long facultyId) {
        logger.info("Был вызван getByFacultyId");
        return studentRepository.findByFacultyId(facultyId);
    }

    public Integer getAllStudentsNumber() {
        logger.info("Был вызван getAllStudentsNumber");
        return studentRepository.getAllStudentsNumber();
    }

    public Double getAvg() {
        logger.info("Был вызван getAvg");
        return studentRepository.getAvg();
    }

    public  List<Student> getFiveStudents() {
        logger.info("Был вызван getFiveStudents");
        return studentRepository.getLastFiveStudents();
    }


}



