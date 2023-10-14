package com.students.project.exception;

public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException() {
    }

    public FacultyNotFoundException(String message) {
        super(message);
    }
}
