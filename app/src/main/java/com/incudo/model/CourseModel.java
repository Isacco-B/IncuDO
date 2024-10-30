package com.incudo.model;

public class CourseModel {
    private int id;
    private String title;
    private String description;
    private String date;
    private String duration;
    private String location;
    private String available;

    public CourseModel() {
    }

    public CourseModel(int id, String title, String description, String date,
            String duration, String location, String available) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.duration = duration;
        this.location = location;
        this.available = available;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvailable() {
        return this.available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d, Nome: %s, Descrizione: %s, Data (ore): %s, Durata: %s, Luogo: %s, Disponibile: %s",
                this.id, this.title, this.description, this.date, this.duration, this.location, this.available);
    }
}
