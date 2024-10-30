package com.incudo.controller;

import java.io.IOException;
import java.util.List;

import com.incudo.exception.CourseAlreadyExist;
import com.incudo.exception.CourseNotFoundException;
import com.incudo.exception.ReservationNotFoundException;
import com.incudo.model.CourseModel;
import com.incudo.model.ReservationModel;
import com.incudo.repository.CourseRepository;
import com.incudo.repository.ReservationRepository;
import com.incudo.util.RandomNumber;

public class CourseController {
    private final ReservationRepository reservationRepository;
    private final CourseRepository courseRepository;

    public CourseController() {
        this.reservationRepository = ReservationRepository.getInstance();
        this.courseRepository = CourseRepository.getInstance();
    }

    public void getAllCourses() {
        try {
            List<CourseModel> courses = courseRepository.findAll();
            courses.forEach(System.out::println);
        } catch (CourseNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public void getCourseById(int id) {
        try {
            CourseModel course = courseRepository.findById(id);
            System.out.println(course);
        } catch (CourseNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public void createCourse(String title, String description, String date, String duration, String location) {
        try {
            int newCourseId = RandomNumber.getRandomNumber();
            CourseModel newCourse = new CourseModel(newCourseId, title, description, date, duration, location, "SI");
            courseRepository.insertOne(newCourse);
            System.out.println("Corso creato con successo!");
        } catch (CourseAlreadyExist e) {
            System.err.println("Errore: " + e.getMessage());
        }

    };

    public void deleteCourse(int id) {
        try {
            List<ReservationModel> reservations = reservationRepository.findAll().stream().filter(
                    reservation -> reservation.getCourseId() == id).toList();
            if (reservations != null) {
                for (ReservationModel reservation : reservations) {
                    reservationRepository.deleteOne(reservation.getId());
                }
            }
            courseRepository.deleteOne(id);
            System.out.println("Corso eliminato con successo!");
        } catch (ReservationNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (CourseNotFoundException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public void exportAvailableCoursesCsv() {
        try {
            courseRepository.exportAvailableCoursesFile();
            System.out.println("File corsi_disponibili.csv esportato con successo nella cartella output!");
        } catch (IOException e) {
            System.err.println("Errore durante l'esportazione del file corsi_disponibili.csv");
        }
    }

    public void exportCoursesCsv() {
        try {
            courseRepository.exportCoursesFile();
            System.out.println("File corsi.csv esportato con successo nella cartella output!");
        } catch (IOException e) {
            System.err.println("Errore durante l'esportazione del file corsi.csv");
        }
    }
}
