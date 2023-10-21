package com.students.project.exception;

import com.students.project.model.Student;

public class StudentNotFoundException extends  RuntimeException{


    public StudentNotFoundException() {
    }


    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


}
