package com.incudo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.incudo.exception.CourseNotFoundException;
import com.incudo.exception.ReservationAlreadyExist;
import com.incudo.exception.ReservationNotFoundException;
import com.incudo.exception.UserNotFoundException;
import com.incudo.model.CourseModel;
import com.incudo.model.ReservationModel;
import com.incudo.model.UserModel;
import com.incudo.repository.CourseRepository;
import com.incudo.repository.ReservationRepository;
import com.incudo.repository.UserRepository;
import com.incudo.util.RandomNumber;

public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public ReservationController() {
        this.reservationRepository = ReservationRepository.getInstance();
        this.courseRepository = CourseRepository.getInstance();
        this.userRepository = UserRepository.getInstance();
    }

    public void getAllReservations() {
        try {
            List<ReservationModel> reservations = reservationRepository.findAll();
            reservations.forEach(System.out::println);
        } catch (ReservationNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public void getReservationById(int id) {
        try {
            ReservationModel reservation = reservationRepository.findById(id);
            System.out.println(reservation);
        } catch (ReservationNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public void createReservation(int userId, int courseId) {
        try {
            CourseModel course = courseRepository.findById(courseId);
            UserModel user = userRepository.findById(userId);
            if (course.getAvailable().equals("NO")) {
                System.err.println("Errore: Il corso selezionato non è disponibile");
                return;
            }
            int newReservationId = RandomNumber.getRandomNumber();
            int daysToAdd = ThreadLocalRandom.current().nextInt(1, 366);
            String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String endDate = LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            ReservationModel newReservation = new ReservationModel(newReservationId, course.getId(), user.getId(),
                    startDate, endDate);
            reservationRepository.insertOne(newReservation);
            course.setAvailable("NO");
            courseRepository.updateOne(course);
            System.out.println("Prenotazione creata con successo!");
        } catch (CourseNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (UserNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (ReservationAlreadyExist e) {
            System.err.println("Errore: " + e.getMessage());
        }

    }

    public void deleteReservation(int reservationId) {
        try {
            ReservationModel reservation = reservationRepository.findById(reservationId);
            CourseModel course = courseRepository.findById(reservation.getCourseId());
            reservationRepository.deleteOne(reservation.getId());
            course.setAvailable("SI");
            courseRepository.updateOne(course);
            System.out.println("Prenotazione cancellata con successo!");
        } catch (ReservationNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (CourseNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    };

    public void exportReservationsCsv() {
        try {
            reservationRepository.exportReservationsFile();

            System.out.println("File prenotazioni.csv esportato con successo nella cartella output!");
        } catch (IOException e) {
            System.err.println("Errore durante l'esportazione del file prenotazioni.csv");
        }

    }
}
