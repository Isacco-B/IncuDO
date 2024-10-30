package com.incudo.repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.incudo.exception.CourseAlreadyExist;
import com.incudo.exception.CourseNotFoundException;
import com.incudo.model.CourseModel;
import com.incudo.util.FileUtils;

public class CourseRepository {
    private static CourseRepository instance;
    private static final String COURSES_FILE = "corsi.csv";
    private final FileUtils fileUtils;
    private List<CourseModel> courses = new ArrayList<>();

    private CourseRepository() {
        this.fileUtils = new FileUtils();
        this.courses = loadCourses();
    }

    public static CourseRepository getInstance() {
        if (instance == null) {
            instance = new CourseRepository();
        }
        return instance;
    }

    public List<CourseModel> loadCourses() {
        try {
            return fileUtils.loadData(COURSES_FILE, this::parseCourse);
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Impossibile caricare il file " + COURSES_FILE);
        }
        return new ArrayList<>();

    }

    public List<CourseModel> findAll() throws CourseNotFoundException {
        if (courses.isEmpty()) {
            throw new CourseNotFoundException("Nessun corso presente!");
        }
        return courses;
    }

    public CourseModel findById(int id) throws CourseNotFoundException {
        return courses.stream()
                .filter(course -> course.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    public void insertOne(CourseModel course) throws CourseAlreadyExist {
        try {
            boolean exists = courses.stream().anyMatch(c -> c.getId() == course.getId());
            if (exists) {
                throw new CourseAlreadyExist(course.getId());
            }
            courses.add(course);
            updateCoursesFile();
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del file " + COURSES_FILE);
        }
    }

    public void updateOne(CourseModel course) throws CourseNotFoundException {
        try {
            boolean exists = courses.stream().anyMatch(c -> c.getId() == course.getId());
            if (!exists) {
                throw new CourseNotFoundException(course.getId());
            }
            courses.removeIf(c -> c.getId() == course.getId());
            courses.add(course);
            updateCoursesFile();
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del file " + COURSES_FILE);
        }
    }

    public void deleteOne(int id) throws CourseNotFoundException {
        try {
            boolean removed = courses.removeIf(course -> course.getId() == id);
            if (!removed) {
                throw new CourseNotFoundException(id);
            }
            updateCoursesFile();
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del file " + COURSES_FILE);
        }
    }

    private CourseModel parseCourse(String line) {
        String[] parts = line.split(";");
        return new CourseModel(
                Integer.parseInt(parts[0].trim()),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim(),
                parts[6].trim());
    }

    private void updateCoursesFile() throws IOException, IllegalArgumentException {
        Function<CourseModel, String> formatter = course -> String.format("%d;%s;%s;%s;%s;%s;%s",
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getDate(),
                course.getDuration(),
                course.getLocation(),
                course.getAvailable());

        fileUtils.updateFile(
                COURSES_FILE,
                "ID;Nome;Descrizione;Data;Durata (ore);Luogo;Disponibile",
                courses,
                formatter);
    }

    public void exportAvailableCoursesFile() throws IOException {
        Function<CourseModel, String> formatter = course -> String.format("%d;%s;%s;%s;%s;%s;%s",
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getDate(),
                course.getDuration(),
                course.getLocation(),
                course.getAvailable());

        List<CourseModel> availableCourses = courses.stream().filter(course -> course.getAvailable().equals("SI"))
                .toList();

        fileUtils.exportFile(
                "corsi_disponibili_" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy")) + ".csv",
                "ID;Nome;Descrizione;Data;Durata (ore);Luogo;Disponibile",
                availableCourses,
                formatter);

    }

    public void exportCoursesFile() throws IOException {
        Function<CourseModel, String> formatter = course -> String.format("%d;%s;%s;%s;%s;%s;%s",
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getDate(),
                course.getDuration(),
                course.getLocation(),
                course.getAvailable());

        fileUtils.exportFile(
                COURSES_FILE,
                "ID;Nome;Descrizione;Data;Durata (ore);Luogo;Disponibile",
                courses,
                formatter);
    }
}
