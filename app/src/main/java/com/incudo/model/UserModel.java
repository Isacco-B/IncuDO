package com.incudo.model;

public class UserModel {
    private int id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    private String documentId;

    public UserModel() {
    };

    public UserModel(int id, String firstName, String lastName, String dateOfBirth,
            String address, String documentId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.documentId = documentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nome: %s, Cognome: %s, Data di nascita: %s, Indirizzo: %s, Documento ID: %s",
                this.id, this.firstName, this.lastName, this.dateOfBirth, this.address, this.documentId);
    }
}
