package com.incudo.exception;

public class CourseAlreadyExist extends BaseException {
    public CourseAlreadyExist(String message) {
        super(message);
    }

    public CourseAlreadyExist(int courseId) {
        super(String.format("Corso con ID " + courseId + " è già esistente!"));
    }
}
