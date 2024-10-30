package com.incudo.schema;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class UserSchema {

    public static ArrayList<String> newUserSchema(String firstName, String lastName, String dateOfBirth, String address,
            String documentId) {
        ArrayList<String> errors = new ArrayList<>();

        if (!firstName.matches("[A-Za-zÀ-ÖØ-öø-ÿ]+")) {
            errors.add("- Il nome deve contenere solo lettere e non può essere vuoto.");
        }
        if (!lastName.matches("[A-Za-zÀ-ÖØ-öø-ÿ]+")) {
            errors.add("- Il cognome deve contenere solo lettere e non può essere vuoto.");
        }
        if (!Pattern.matches("\\d{2}/\\d{2}/\\d{4}", dateOfBirth)) {
            errors.add("- La data di nascita deve essere nel formato dd/mm/yyyy.");
        }
        if (address.isEmpty()) {
            errors.add("- L'indirizzo non può essere vuoto.");
        }
        if (documentId.isEmpty()) {
            errors.add("- Il documento ID non può essere vuoto.");
        }

        return errors;
    }

    public static ArrayList<String> deleteUserSchema(String userId) {
        ArrayList<String> errors = new ArrayList<>();

        if (!userId.matches("^[0-9]+$")) {
            errors.add("- ID utente non è valido.");
        }

        return errors;
    }

    public static ArrayList<String> showUserSchema(String userId) {
        ArrayList<String> errors = new ArrayList<>();

        if (!userId.matches("^[0-9]+$")) {
            errors.add("- ID utente non è valido.");
        }

        return errors;
    }
}
