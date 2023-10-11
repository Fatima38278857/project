package com.students.project.service;


import com.students.project.model.Faculty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class FacultyService {

  private final FacultyService facultyRepository;

    public FacultyService(FacultyService facultyRepository) {
        this.facultyRepository = facultyRepository;
    }



    // В каждом сервисе реализовать CRUD-методы для создания, чтения, изменения и удаления сущностей.

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.;
    }

    public Faculty readFaculty(long id) {
        return facultyRepository.getById(id);

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
            if (Objects.equals(faculty.getColor(), color)) {
                result.add(faculty);
            }
        }
        return result;
    }
}









