package com.students.project.service;


import com.students.project.exception.FacultyNotFoundException;
import com.students.project.model.Faculty;
import com.students.project.model.Student;
import com.students.project.repository.FacultyRepository;
import com.students.project.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class  FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    // В каждом сервисе реализовать CRUD-методы для создания, чтения, изменения и удаления сущностей.

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(() ->
                new FacultyNotFoundException("Такого факультета нет"));

    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findColor(String color) {
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : facultyRepository.findAll()) {
            if ((faculty.getColor().equals(color))) {
                result.add(faculty);
            }
        }
        return result;
    }

    public Faculty filtrName(String name) {
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Faculty filtrColor(String color) {
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }

    public List<Student> getStudentOfId(Long id) {
        return studentRepository.findByFacultyId(id);

    }

    public List<Faculty> allFaculty() {
        return facultyRepository.allFaculty();


    }
}










