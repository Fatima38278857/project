package com.students.project.repository;


import com.students.project.model.Student;
import jakarta.validation.constraints.Max;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s GROUP BY id")
    List<Student> studentAll();

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findByFacultyId(Long facultyId);
}
