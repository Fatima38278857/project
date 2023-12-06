package com.students.project.repository;


import com.students.project.model.Faculty;
import com.students.project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty,  Long> {

       Faculty  findFacultyByNameIgnoreCase(String name);


       Faculty findFacultyByColorIgnoreCase(String color);

       @Query("SELECT f FROM Faculty f GROUP BY id")
       List<Faculty> allFaculty();
}
