package com.incudo.exception;

public class ReservationAlreadyExist extends BaseException {
    public ReservationAlreadyExist(String message) {
        super(message);
    }

    public ReservationAlreadyExist(int reservationId) {
        super(String.format("Prenotazione con ID " + reservationId + " è già esistente!"));
    }
}
