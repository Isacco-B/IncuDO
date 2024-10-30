package com.incudo.schema;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class CourseSchema {
    public static ArrayList<String> newCourseSchema(String title, String description, String date, String duration,
            String location) {
        ArrayList<String> errors = new ArrayList<>();

        if (!title.matches("[A-Za-zÀ-ÖØ-öø-ÿ.,!?;:'\"\\-\\s]+")) {
            errors.add("- Il titolo deve contenere solo lettere e non può essere vuoto.");
        }
        if (!description.matches("[A-Za-zÀ-ÖØ-öø-ÿ.,!?;:'\"\\-\\s]+")) {
            errors.add("- La descrizione deve contenere solo lettere e non può essere vuota.");
        }
        if (!Pattern.matches("\\d{2}/\\d{2}/\\d{4}", date)) {
            errors.add("- La data deve essere nel formato dd/mm/yyyy.");
        }
        if (!duration.matches("^(100|[1-9]?[0-9])$")) {
            errors.add("- La durata deve essere un numero intero tra 0 e 100.");
        }
        if (!location.matches("[A-Za-zÀ-ÖØ-öø-ÿ]+")) {
            errors.add("- Il luogo deve contenere solo lettere e non può essere vuoto.");
        }

        return errors;
    }

    public static ArrayList<String> deleteCourseSchema(String courseId) {
        ArrayList<String> errors = new ArrayList<>();

        if (!courseId.matches("^[0-9]+$")) {
            errors.add("- L'ID del corso non è valido.");
        }

        return errors;
    }

    public static ArrayList<String> showCourseSchema(String courseId) {
        ArrayList<String> errors = new ArrayList<>();

        if (!courseId.matches("^[0-9]+$")) {
            errors.add("- ID del corso non è valido.");
        }

        return errors;
    }
}
