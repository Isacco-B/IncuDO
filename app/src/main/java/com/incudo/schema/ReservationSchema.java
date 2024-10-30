package com.incudo.schema;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ReservationSchema {

    public static ArrayList<String> newReservationSchema(String userId, String courseId) {
        ArrayList<String> errors = new ArrayList<>();
        if (!userId.matches("^[0-9]+$")) {
            errors.add("- ID utente non può essere vuoto e deve contenere solo numeri.");
        }
        if (!courseId.matches("^[0-9]+$")) {
            errors.add("- ID corso non può essere vuoto e deve contenere solo numeri.");
        }
        return errors;
    }

    public static ArrayList<String> deleteReservationSchema(String idReservation, String firstName, String lastName,
            String dateOfBirth, String address,
            String documentId) {
        ArrayList<String> errors = new ArrayList<>();

        if (!idReservation.matches("^[0-9]+$")) {
            errors.add("- ID prenotazione non può essere vuoto e deve contenere solo numeri.");
        }
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

    public static ArrayList<String> deleteReservationSchema(String reservationId) {
        ArrayList<String> errors = new ArrayList<>();

        if (!reservationId.matches("^[0-9]+$")) {
            errors.add("- ID della prenotazione non è valido.");
        }

        return errors;
    }

    public static ArrayList<String> showReservationSchema(String reservationId) {
        ArrayList<String> errors = new ArrayList<>();

        if (!reservationId.matches("^[0-9]+$")) {
            errors.add("- ID della prenotazione non è valido.");
        }

        return errors;
    }
}
