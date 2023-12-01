package com.students.project.service;


import com.students.project.exception.FacultyNotFoundException;
import com.students.project.model.Faculty;
import com.students.project.model.Student;
import com.students.project.repository.FacultyRepository;
import com.students.project.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class  FacultyService {
    private  final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }




    public Faculty createFaculty(Faculty faculty) {
        logger.info("Был вызван createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty(long id) {
        logger.error("Был вызван readFaculty и такого студента нет", id);
        return facultyRepository.findById(id).orElseThrow(() ->
                new FacultyNotFoundException("Такого факультета нет"));

    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Был вызван updateFaculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Был вызван deleteFaculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findColor(String color) {
        logger.info("Был вызван findColor");
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : facultyRepository.findAll()) {
            if ((faculty.getColor().equals(color))) {
                result.add(faculty);
            }
        }
        return result;
    }

    public Faculty filterName(String name) {
        logger.info("Был вызван filterName");
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Faculty filterColor(String color) {
        logger.info("Был вызван filterColor");
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }

    public List<Student> getStudentOfId(Long id) {
        logger.info("Был вызван getStudentOfId");
        return studentRepository.findByFacultyId(id);

    }

    public List<Faculty> allFaculty() {
        logger.info("Был вызван findAvatar");
        return facultyRepository.allFaculty();


    }
}










