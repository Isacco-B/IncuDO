package com.incudo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.incudo.exception.ReservationAlreadyExist;
import com.incudo.exception.ReservationNotFoundException;
import com.incudo.model.ReservationModel;
import com.incudo.util.FileUtils;

public class ReservationRepository {
    private static ReservationRepository instance;
    private static final String RESERVATIONS_FILE = "prenotazioni.csv";
    private final FileUtils fileUtils;
    private List<ReservationModel> reservations = new ArrayList<>();

    private ReservationRepository() {
        this.fileUtils = new FileUtils();
        this.reservations = loadReservations();
    }

    public static ReservationRepository getInstance() {
        if (instance == null) {
            instance = new ReservationRepository();
        }
        return instance;
    }

    public List<ReservationModel> loadReservations() {
        try {
            return fileUtils.loadData(RESERVATIONS_FILE, this::parseReservation);
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Impossibile caricare il file " + RESERVATIONS_FILE);
        }
        return new ArrayList<>();
    }

    public List<ReservationModel> findAll() throws ReservationNotFoundException {
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("Nessuna prenotazione presente!");
        }
        return reservations;
    }

    public ReservationModel findById(int id) throws ReservationNotFoundException {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public void insertOne(ReservationModel reservation) throws ReservationAlreadyExist {
        try {
            boolean exists = reservations.stream().anyMatch(r -> r.getId() == reservation.getId());
            if (exists) {
                throw new ReservationAlreadyExist(reservation.getId());
            }
            reservations.add(reservation);
            updateReservationsFile();
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del file " + RESERVATIONS_FILE);
        }
    }

    public void updateOne(ReservationModel reservation) throws ReservationNotFoundException {
        try {
            boolean exists = reservations.stream().anyMatch(r -> r.getId() == reservation.getId());
            if (!exists) {
                throw new ReservationNotFoundException(reservation.getId());
            }
            reservations.removeIf(r -> r.getId() == reservation.getId());
            reservations.add(reservation);
            updateReservationsFile();
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del file " + RESERVATIONS_FILE);
        }
    }

    public void deleteOne(int id) throws ReservationNotFoundException {
        try {
            boolean removed = reservations.removeIf(reservation -> reservation.getId() == id);
            if (!removed) {
                throw new ReservationNotFoundException(id);
            }
            updateReservationsFile();
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del file " + RESERVATIONS_FILE);
        }
    }

    private ReservationModel parseReservation(String line) {
        String[] parts = line.split(";");
        return new ReservationModel(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                parts[3],
                parts[4]);
    }

    private void updateReservationsFile() throws IOException, IllegalArgumentException {
        Function<ReservationModel, String> formatter = reservation -> String.format("%d;%d;%d;%s;%s",
                reservation.getId(),
                reservation.getCourseId(),
                reservation.getUserId(),
                reservation.getStartDate(),
                reservation.getEndDate());

        fileUtils.updateFile(
                RESERVATIONS_FILE,
                "ID;ID Attività;ID Utente;Data inizio;Data fine",
                reservations,
                formatter);
    }

    public void exportReservationsFile() throws IOException {
        Function<ReservationModel, String> formatter = reservation -> String.format("%d;%d;%d;%s;%s",
                reservation.getId(),
                reservation.getCourseId(),
                reservation.getUserId(),
                reservation.getStartDate(),
                reservation.getEndDate());

        fileUtils.exportFile(
                RESERVATIONS_FILE,
                "ID;ID Attività;ID Utente;Data inizio;Data fine",
                reservations,
                formatter);

    }
}
