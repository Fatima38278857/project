package com.students.project.repository;


import com.students.project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s GROUP BY id")
    List<Student> studentAll();

    List<Student> findByAgeBetween(int min, int max);

    List<Student>  findByFacultyId(Long facultyId);

    @Query(value = "SELECT COUNT(*) FROM student",
    nativeQuery = true)
    Integer getAllStudentsNumber();
    @Query(value = "SELECT AVG(age) FROM student",
            nativeQuery = true)
    Double getAvg();

    @Query(value = "SELECT * FROM student OFFSET 4",
            nativeQuery = true)
    List<Student> getLastFiveStudents();
}
