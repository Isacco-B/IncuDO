package com.incudo.model;

public class ReservationModel {
    private int id;
    private int userId;
    private int courseId;
    private String startDate;
    private String endDate;

    public ReservationModel() {
    }

    public ReservationModel(int id, int courseId, int userId, String startDate, String endDate) {
        this.id = id;
        this.courseId = courseId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, ID Attività: %d, ID Utente: %d, Data inizio: %s, Data fine: %s",
                this.id, this.courseId, this.userId, this.startDate, this.endDate);
    }
}
