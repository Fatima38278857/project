package com.students.project.service;


import com.students.project.exception.StudentNotFoundException;
import com.students.project.model.Faculty;
import com.students.project.model.Student;
import com.students.project.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    // В каждом сервисе реализовать CRUD-методы для создания, чтения, изменения и удаления сущностей.

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

       public List<Student> findByAllAge(int min, int max){
       return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> allStudentInformation(){
        return studentRepository.studentAll();
    }

       public Faculty  getFacultyById(Long id){
        return studentRepository.findById(id).get().getFaculty();
      }

      public List<Student>  getByFacultyId(Long facultyId){
        return studentRepository.findByFacultyId(facultyId);
      }

      public Integer getAllStudentsNumber(){
        return studentRepository.getAllStudentsNumber();
      }
}


