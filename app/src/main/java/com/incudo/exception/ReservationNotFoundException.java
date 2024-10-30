package com.incudo.exception;

public class ReservationNotFoundException extends BaseException{
    public ReservationNotFoundException(String message) {
        super(message);
    }

    public ReservationNotFoundException(int reservationId) {
        super(String.format("Prenotazione con ID " + reservationId + " non trovata!"));
    }
}
