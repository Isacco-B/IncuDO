package com.incudo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.incudo.exception.UserAlreadyExist;
import com.incudo.exception.UserNotFoundException;
import com.incudo.model.UserModel;
import com.incudo.util.FileUtils;

public class UserRepository {
    private static UserRepository instance;
    private static final String USERS_FILE = "utenti.csv";
    private final FileUtils fileUtils;
    private List<UserModel> users = new ArrayList<>();

    private UserRepository() {
        this.fileUtils = new FileUtils();
        this.users = loadUsers();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public List<UserModel> loadUsers() {
        try {
            return fileUtils.loadData(USERS_FILE, this::parseUser);
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Impossibile caricare il file " + USERS_FILE);
        }
        return new ArrayList<>();
    }

    public List<UserModel> findAll() throws UserNotFoundException {
        if (users.isEmpty()) {
            throw new UserNotFoundException("Nessun utente presente!");
        }
        return users;
    }

    public UserModel findById(int id) throws UserNotFoundException {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public void insertOne(UserModel user) throws UserAlreadyExist {
        try {
            boolean exists = users.stream().anyMatch(u -> u.getId() == user.getId());
            if (exists) {
                throw new UserAlreadyExist(user.getId());
            }
            users.add(user);
            updateUsersFile();
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del file " + USERS_FILE);
        }
    }

    public void updateOne(UserModel user) throws UserNotFoundException {
        try {
            boolean exists = users.stream().anyMatch(u -> u.getId() == user.getId());
            if (!exists) {
                throw new UserNotFoundException(user.getId());
            }
            users.removeIf(u -> u.getId() == user.getId());
            users.add(user);
            updateUsersFile();
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del file " + USERS_FILE);
        }
    }

    public void deleteOne(int id) throws UserNotFoundException {
        try {
            boolean removed = users.removeIf(user -> user.getId() == id);
            if (!removed) {
                throw new UserNotFoundException(id);
            }
            updateUsersFile();
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del file " + USERS_FILE);
        }
    }

    private UserModel parseUser(String line) {
        String[] parts = line.split(";");
        return new UserModel(
                Integer.parseInt(parts[0].trim()),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim());
    }

    private void updateUsersFile() throws IOException, IllegalArgumentException {
        Function<UserModel, String> formatter = user -> String.format("%d;%s;%s;%s;%s;%s",
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getAddress(),
                user.getDocumentId());

        fileUtils.updateFile(
                USERS_FILE,
                "ID;Nome;Cognome;Data di nascita;Indirizzo;Documento ID",
                users,
                formatter);
    }

    public void exportUsersFile() throws IOException {
        Function<UserModel, String> formatter = user -> String.format("%d;%s;%s;%s;%s;%s",
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getAddress(),
                user.getDocumentId());

        fileUtils.exportFile(
                USERS_FILE,
                "ID;Nome;Cognome;Data di nascita;Indirizzo;Documento ID",
                users,
                formatter);
    }

}
