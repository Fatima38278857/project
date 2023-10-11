package com.students.project.service;

import com.project.students.exception.StudentNotFoundException;
import com.project.students.model.Student;
import com.project.students.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class StudentService {

    private final StudentService studentRepository;



    // В каждом сервисе реализовать CRUD-методы для создания, чтения, изменения и удаления сущностей.

    public Student create(Student student) {
       return studentRepository.save(student);
    }


    public Student read(long id) {
        return studentRepository.findById(id).orElseThrow(() ->
            new StudentNotFoundException("Такого студента нет")
        );

    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }

    public void deletee(long id) {
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
}


