package com.students.project.repository;


import com.students.project.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty,  Long> {

       Faculty  findFacultyByNameIgnoreCase(String name);


       Faculty findFacultyByColorIgnoreCase(String color);
}
