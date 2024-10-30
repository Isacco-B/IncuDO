package com.incudo.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.incudo.controller.CourseController;
import com.incudo.controller.ReservationController;
import com.incudo.controller.UserController;
import com.incudo.schema.CourseSchema;
import com.incudo.schema.ReservationSchema;
import com.incudo.schema.UserSchema;

public class ConsoleUI {
    private final ReservationController reservationController;
    private final CourseController courseController;
    private final UserController userController;
    private final Scanner scanner;

    public ConsoleUI(ReservationController reservationController, CourseController courseController,
            UserController userController) {
        this.reservationController = reservationController;
        this.courseController = courseController;
        this.userController = userController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            showMenu();
            int choice = getUserChoice();
            processChoice(choice);
            if (choice == 0)
                break;
        }
    }

    private int getUserChoice() {
        System.out.println();
        System.out.print("Seleziona un'opzione: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Input non valido! Inserisci un numero.");
            System.out.print("Seleziona un'opzione: ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private void showAdcancedMenu() {
        System.out.println();
        System.out.println("----------------UTENTI----------------------");
        System.out.println("7. Visualizza tutti i gli utenti");
        System.out.println("8. Visualizza utente tramite ID");
        System.out.println("9. Elimina utente tramite ID");
        System.out.println("10. Esporta file utenti");
        System.out.println();
        System.out.println("----------------CORSI----------------------");
        System.out.println("11. Visualizza corso tramite ID");
        System.out.println("12. Aggiungi nuovo corso");
        System.out.println("13. Elimina corso tramite ID");
        System.out.println("14. Esporta file corsi");
        System.out.println();
        System.out.println("----------------PRENOTAZIONI----------------------");
        System.out.println("15. Visualizza tutte le prenotazioni");
        System.out.println("16. Visualizza prenotazione tramite ID");
        System.out.println("17. Elimina prenotazione tramite ID");
        System.out.println("18. Esporta file prenotazioni");
    }

    private void showMenu() {
        System.out.println();
        System.out.println("1. Visualizza tutti i corsi");
        System.out.println("2. Prenota un corso");
        System.out.println("3. Disdici prenotazione");
        System.out.println("4. Aggiungi nuovo utente");
        System.out.println("5. Esporta file corsi disponibili");
        System.out.println("6. Opzioni avanzate");
        System.out.println("0. Esci");
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 0 -> System.out.println("Arrivederci!");
            case 1 -> showAllCourses();
            case 2 -> addReservation();
            case 3 -> deleteReservation();
            case 4 -> addNewUser();
            case 5 -> exportAvailableCourses();
            case 6 -> showAdcancedMenu();
            case 7 -> showAllUsers();
            case 8 -> showUserById();
            case 9 -> deleteUserById();
            case 10 -> exportUsersCsv();
            case 11 -> showCourseById();
            case 12 -> addNewCourse();
            case 13 -> deleteCourseById();
            case 14 -> exportCoursesCsv();
            case 15 -> showAllReservations();
            case 16 -> showReservationById();
            case 17 -> deleteReservationById();
            case 18 -> exportReservationsCsv();
            default -> System.out.println("Scelta non valida!");
        }
    }

    // Course operations
    private void showAllCourses() {
        System.out.println();
        System.out.println("Elenco Corsi:");
        courseController.getAllCourses();
    }

    private void showCourseById() {
        System.out.println();
        System.out.print("Inserisci ID Corso: ");
        String courseId = scanner.nextLine();
        ArrayList<String> errors = CourseSchema.showCourseSchema(courseId);
        if (!errors.isEmpty()) {
            for (String error : errors) {
                System.err.println(error);
            }
            return;
        }
        System.out.println();
        System.out.println("Dettaglio Corso:");
        courseController.getCourseById(Integer.parseInt(courseId));
    }

    private void addNewCourse() {
        System.out.println();
        System.out.println("Inserisci i dati dell'corso: ");
        System.out.print("Titolo: ");
        String title = scanner.nextLine();
        System.out.print("Descrizione: ");
        String description = scanner.nextLine();
        System.out.print("Data (dd/mm/yyyy): ");
        String date = scanner.nextLine();
        System.out.print("Durata (ore): ");
        String duration = scanner.nextLine();
        System.out.print("Luogo: ");
        String location = scanner.nextLine();
        ArrayList<String> errors = CourseSchema.newCourseSchema(title, description, date, duration, location);
        if (!errors.isEmpty()) {
            System.out.println();
            System.err.println("Errore durante la creazione dell'corso:");
            for (String error : errors) {
                System.err.println(error);
            }
            return;
        }
        System.out.println();
        courseController.createCourse(title, description, date, duration, location);
    }

    private void deleteCourseById() {
        System.out.println();
        System.out.print("Inserisci ID Corso: ");
        String courseId = scanner.nextLine();
        ArrayList<String> errors = CourseSchema.deleteCourseSchema(courseId);
        if (!errors.isEmpty()) {
            System.out.println();
            System.err.println("Errore durante la cancellazione del corso:");
            for (String error : errors) {
                System.err.println(error);
            }
            return;
        }
        System.out.println();
        courseController.deleteCourse(Integer.parseInt(courseId));
    }

    private void exportCoursesCsv() {
        System.out.println();
        courseController.exportCoursesCsv();
    }

    private void exportAvailableCourses() {
        System.out.println();
        courseController.exportAvailableCoursesCsv();
    }

    // User operations
    private void showAllUsers() {
        System.out.println();
        System.out.println("Elenco Utenti:");
        userController.getAllUsers();
    }

    private void showUserById() {
        System.out.println();
        System.out.print("Inserisci ID Utente: ");
        String userId = scanner.nextLine();
        ArrayList<String> errors = UserSchema.showUserSchema(userId);
        if (!errors.isEmpty()) {
            System.out.println();
            System.err.println("Errore durante la visualizzazione dell'utente:");
            for (String error : errors) {
                System.err.println(error);
            }
            return;
        }
        System.out.println();
        System.out.println("Dettagli Utente:");
        userController.getUserById(Integer.parseInt(userId));
    }

    private void addNewUser() {
        System.out.println();
        System.out.println("Inserisci i dati dell'utente: ");
        System.out.print("Nome: ");
        String firstName = scanner.nextLine();
        System.out.print("Cognome: ");
        String lastName = scanner.nextLine();
        System.out.print("Data di Nascita (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Indirizzo: ");
        String address = scanner.nextLine();
        System.out.print("Documento ID: ");
        String documentId = scanner.nextLine();
        ArrayList<String> errors = UserSchema.newUserSchema(firstName, lastName, dateOfBirth, address, documentId);
        if (!errors.isEmpty()) {
            System.out.println();
            System.err.println("Errore durante la creazione dell'utente:");
            for (String error : errors) {
                System.err.println(error);
            }
            return;
        }
        System.out.println();
        userController.createUser(firstName, lastName, dateOfBirth, address, documentId);
    }

    private void deleteUserById() {
        System.out.println();
        System.out.print("Inserisci ID Utente: ");
        String userId = scanner.nextLine();
        ArrayList<String> errors = UserSchema.deleteUserSchema(userId);
        if (!errors.isEmpty()) {
            System.out.println();
            System.err.println("Errore durante la cancellazione dell'utente:");
            for (String error : errors) {
                System.err.println(error);
            }
            return;
        }
        System.out.println();
        userController.deleteUser(Integer.parseInt(userId));
    }

    private void exportUsersCsv() {
        System.out.println();
        userController.exportUsersCsv();
    }

    // Reservation operations
    private void addReservation() {
        System.out.println();
        System.out.println("Inserisci i dati per prenotare un corso: ");
        System.out.print("ID Utente: ");
        String userId = scanner.nextLine();
        System.out.print("ID Corso: ");
        String courseId = scanner.nextLine();
        ArrayList<String> errors = ReservationSchema.newReservationSchema(userId, courseId);
        if (!errors.isEmpty()) {
            System.out.println();
            System.err.println("Errore durante prenotazione del corso:");
            for (String error : errors) {
                System.err.println(error);
            }
            return;
        }
        System.out.println();
        reservationController.createReservation(Integer.parseInt(userId), Integer.parseInt(courseId));
    }

    private void deleteReservation() {
        System.out.println("Inserisci i dati della prenotazione: ");
        System.out.print("ID Prenotazione: ");
        String idReservation = scanner.nextLine();
        System.out.print("Nome: ");
        String firstName = scanner.nextLine();
        System.out.print("Cognome: ");
        String lastName = scanner.nextLine();
        System.out.print("Data di Nascita (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Indirizzo: ");
        String address = scanner.nextLine();
        ;
        System.out.print("Documento ID: ");
        String documentId = scanner.nextLine();
        ArrayList<String> errors = ReservationSchema.deleteReservationSchema(
                idReservation, firstName, lastName, dateOfBirth, address, documentId);
        if (!errors.isEmpty()) {
            System.out.println();
            System.err.println("Errore durante la cancellazione della prenotazione:");
            for (String error : errors) {
                System.err.println(error);
            }
            return;
        }
        System.out.println();
        reservationController.deleteReservation(Integer.parseInt(idReservation));
    }

    private void showAllReservations() {
        System.out.println();
        System.out.println("Elenco Prenotazioni:");
        reservationController.getAllReservations();
    }

    private void showReservationById() {
        System.out.println();
        System.out.print("Inserisci ID Prenotazione: ");
        String reservationId = scanner.nextLine();
        ArrayList<String> errors = ReservationSchema.showReservationSchema(reservationId);
        if (!errors.isEmpty()) {
            System.out.println();
            System.err.println("Errore durante la visualizzazione della prenotazione:");
            for (String error : errors) {
                System.err.println(error);
            }
            return;
        }
        System.out.println();
        System.out.println("Dettagli Prenotazione:");
        reservationController.getReservationById(Integer.parseInt(reservationId));
    }

    private void deleteReservationById() {
        System.out.println();
        System.out.print("Inserisci ID Prenotazione: ");
        String reservationId = scanner.nextLine();
        ArrayList<String> errors = ReservationSchema.showReservationSchema(reservationId);
        if (!errors.isEmpty()) {
            System.out.println();
            System.err.println("Errore durante la cancellazione della prenotazione:");
            for (String error : errors) {
                System.err.println(error);
            }
            return;
        }
        System.out.println();
        reservationController.deleteReservation(Integer.parseInt(reservationId));
    }

    private void exportReservationsCsv() {
        System.out.println();
        reservationController.exportReservationsCsv();
    }
}
