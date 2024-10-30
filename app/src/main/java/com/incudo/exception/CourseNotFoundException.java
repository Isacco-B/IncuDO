package com.incudo.exception;

public class CourseNotFoundException extends BaseException{
    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(int courseId) {
        super(String.format("Corso con ID " + courseId + " non trovato!"));
    }
}
