package com.incudo.controller;

import java.io.IOException;
import java.util.List;

import com.incudo.exception.CourseNotFoundException;
import com.incudo.exception.ReservationNotFoundException;
import com.incudo.exception.UserAlreadyExist;
import com.incudo.exception.UserNotFoundException;
import com.incudo.model.CourseModel;
import com.incudo.model.ReservationModel;
import com.incudo.model.UserModel;
import com.incudo.repository.CourseRepository;
import com.incudo.repository.ReservationRepository;
import com.incudo.repository.UserRepository;
import com.incudo.util.RandomNumber;

public class UserController {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final CourseRepository courseRepository;

    public UserController() {
        this.userRepository = UserRepository.getInstance();
        this.reservationRepository = ReservationRepository.getInstance();
        this.courseRepository = CourseRepository.getInstance();
    }

    public void getAllUsers() {
        try {
            List<UserModel> users = userRepository.findAll();
            users.forEach(System.out::println);
        } catch (UserNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public void getUserById(int id) {
        try {
            UserModel user = userRepository.findById(id);
            System.out.println(user);
        } catch (UserNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public void createUser(String firstName, String lastName, String dateOfBirth,
            String address, String documentId) {
        try {
            int newUserId = RandomNumber.getRandomNumber();
            UserModel newUser = new UserModel(newUserId, firstName, lastName, dateOfBirth, address, documentId);
            userRepository.insertOne(newUser);
            System.out.println("Utente creato con successo!");
        } catch (UserAlreadyExist e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public void deleteUser(int id) {
        try {
            List<ReservationModel> reservations = reservationRepository.findAll().stream().filter(
                    reservation -> reservation.getUserId() == id).toList();
            if (reservations != null) {
                for (ReservationModel reservation : reservations) {
                    CourseModel course = courseRepository.findById(reservation.getCourseId());
                    course.setAvailable("SI");
                    courseRepository.updateOne(course);
                    reservationRepository.deleteOne(reservation.getId());
                }
            }
            userRepository.deleteOne(id);
            System.out.println("Utente eliminato con successo!");
        } catch (UserNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (ReservationNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (CourseNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public void exportUsersCsv() {
        try {
            userRepository.exportUsersFile();
            System.out.println("File utenti.csv esportato con successo nella cartella output!");
        } catch (IOException e) {
            System.err.println("Errore durante l'esportazione del file utenti.csv");
        }
    }
}
